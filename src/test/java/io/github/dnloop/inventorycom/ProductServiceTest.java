package io.github.dnloop.inventorycom;

import io.github.dnloop.inventorycom.model.Product;
import io.github.dnloop.inventorycom.service.ProductService;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Basic product service tests units. Perform CRUD operations and test relationships with dependencies.
 *
 * <p></p>
 * <p>Further test cases must answer the following topics:</p>
 * <p></p>
 * <p><b>What happens when a restored record references a deleted category?</b></p>
 * <p></p>
 * <p>
 * Two possible scenarios arise: one involves restoring the deleted category (not ideal due to hierarchical structure),
 * other consist in assigning a generic category until deciding where the deleted product must reside. The last option
 * is more viable, everytime a category is deleted the relationships must be updated to a generic value in order
 * to prevent orphaned values.
 * </p>
 * <p>
 * Validation must ensure that products are saved on existing categories. Everytime a category is deleted, it must
 * update its relationships.
 * </p>
 */
@SpringBootTest
@EnableAsync
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Sql({
             "/db/data/insert-category.sql",
             "/db/data/insert-category_level.sql",
             "/db/data/insert-material.sql",
             "/db/data/insert-measure.sql",
             "/db/data/insert-presentation.sql",
             "/db/data/insert-product_detail.sql",
             "/db/data/insert-product.sql"
     })
public class ProductServiceTest {

    @Autowired
    ProductService productService;

    @Test
    void contextLoads() {}

    @Test
    void productNull() throws ExecutionException, InterruptedException {
        final CompletableFuture<Optional<Product>> product = productService.findProductById(7);

        assertThat(
                product.get()
        ).matches(Optional::isEmpty, "is present");
    }

    @Test
    void findProductById() throws ExecutionException, InterruptedException {
        final CompletableFuture<Optional<Product>> product = CompletableFuture.supplyAsync(() -> {
            try {
                return productService.findProductById(1).get();
            } catch (InterruptedException | ExecutionException e) {
                return Optional.empty();
            }
        });

        assertThat(product.get())
                .matches(Optional::isPresent, "is empty");
    }

    /**
     * Query a deleted record with a non-delete clause
     */
    @Test
    void findProductByIdDeleted() throws ExecutionException, InterruptedException {
        final CompletableFuture<Optional<Product>> product = CompletableFuture.supplyAsync(() -> {
            try {
                return productService.findProductById(5).get();
            } catch (InterruptedException | ExecutionException e) {
                return Optional.empty();
            }
        });

        assertThat(product.get())
                .matches(Optional::isEmpty, "is present");
    }

    @Test
    void findDeletedProduct() throws ExecutionException, InterruptedException {
        final CompletableFuture<Optional<Product>> product = CompletableFuture.supplyAsync(() -> {
            try {
                return productService.findDeletedProduct(5).get();
            } catch (InterruptedException | ExecutionException e) {
                return Optional.empty();
            }
        });

        assertThat(product.get())
                .matches(Optional::isPresent, "is empty");
    }

    @Test
    void findAllProducts() throws ExecutionException, InterruptedException {
        final Condition<Product> firstProduct = new Condition<>(
                product -> product.getDescription().equalsIgnoreCase("PD-1"),
                "[Description] - PD-1"
        );
        final CompletableFuture<Page<Product>> products = productService.findAllProducts();
        final Page<Product> result = products.get();

        assertThat(result).hasSize(4);
        assertThat(
                result.getContent().get(0)
        ).has(firstProduct);
    }


    @Test
    void findAllDeletedProducts() throws ExecutionException, InterruptedException {
        final Condition<Product> firstProduct = new Condition<>(
                product -> product.getDescription().equalsIgnoreCase("PD-5"),
                "[Description] - PD-5"
        );
        final CompletableFuture<Page<Product>> products = productService.findAllDeletedProducts();
        final Page<Product> result = products.get();

        assertThat(result).hasSize(2);
        assertThat(
                result.getContent().get(0)
        ).has(firstProduct);
    }

    @Test
    void saveProduct() throws ExecutionException, InterruptedException {
        final Condition<Product> productCondition = new Condition<>(
                product -> product.getDescription().equalsIgnoreCase("PD-NEW"),
                "[Description] - PD-NEW"
        );

        Product newProduct = new Product(
                "PD-NEW", 1,
                new BigDecimal("200.00"),
                2,
                1
        );

        final CompletableFuture<Optional<Product>> product =
                productService.saveProduct(newProduct).thenApply(product1 -> {
                    try {
                        return productService.findProductById(product1.getId()).get();
                    } catch (InterruptedException | ExecutionException e) {
                        return Optional.empty();
                    }
                });
        final Optional<Product> result = product.get();

        assertThat(result)
                .matches(Optional::isPresent, "is empty");
        if (result.isPresent())
            assertThat(result.get()).has(productCondition);
        else
            throw new AssertionError("Result is not present");
    }

    @Test
    void modifyProduct() throws ExecutionException, InterruptedException {
        final Timestamp ts = Timestamp.from(Instant.now());
        final Product editProduct = productService.findProductById(1).join().orElse(null);

        Objects.requireNonNull(editProduct).setModifiedAt(ts);

        final CompletableFuture<Optional<Product>> modifiedProduct =
                productService.saveProduct(editProduct).thenCompose(
                        prod -> productService.findProductById(prod.getId())
                );

        final Timestamp result;

        if (modifiedProduct.get().isPresent())
            result = modifiedProduct.get().get().getModifiedAt();
        else
            result = Timestamp.from(Instant.ofEpochSecond(0L));

        assertThat(result)
                .as("TimeStamp should be equal to %s", result)
                .isEqualTo(ts);
    }


    @Test
    void deleteProduct() throws ExecutionException, InterruptedException {
        final CompletableFuture<Void> product =
                productService.findProductById(1).thenAccept(product1 -> product1.ifPresent(
                        value -> productService.deleteProduct(value)
                ));


        final CompletableFuture<Optional<Product>> productDeleted =
                product.thenCompose(
                        unused -> productService.findDeletedProduct(1)
                );

        assertThat(
                productDeleted.get()
        ).matches(Optional::isPresent, "is empty");
    }
}
