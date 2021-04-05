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
 * TODO load test data
 * TODO adjust test values according to test data
 */
@SpringBootTest
@EnableAsync
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ProductServiceTest {

    @Autowired
    ProductService productService;

    @Test
    void contextLoads() {}

    @Test
    void productNull() throws ExecutionException, InterruptedException {
        final CompletableFuture<Optional<Product>> product = productService.findProductById(6);

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
                return productService.findProductById(1).get();
            } catch (InterruptedException | ExecutionException e) {
                return Optional.empty();
            }
        });

        assertThat(product.get())
                .matches(Optional::isPresent, "is empty");
    }

    @Test
    void findProductDeleted() throws ExecutionException, InterruptedException {
        final CompletableFuture<Optional<Product>> product = CompletableFuture.supplyAsync(() -> {
            try {
                return productService.findDeletedProduct(1).get();
            } catch (InterruptedException | ExecutionException e) {
                return Optional.empty();
            }
        });

        assertThat(product.get())
                .matches(Optional::isEmpty, "is present");
    }

    @Test
    void findAllProducts() throws ExecutionException, InterruptedException {
        final Condition<Product> firstProduct = new Condition<>(
                product -> product.getDescription().equalsIgnoreCase("PD-1"),
                "[Description] - PD-1"
        );
        final CompletableFuture<Page<Product>> products = productService.findAllProducts();
        final Page<Product> result = products.get();

        assertThat(result).hasSize(3);
        assertThat(
                result.getContent().get(0)
        ).has(firstProduct);
    }


    @Test
    void findAllProductsDeleted() throws ExecutionException, InterruptedException {
        final Condition<Product> firstProduct = new Condition<>(
                product -> product.getDescription().equalsIgnoreCase("PD-1"),
                "[Description] - PD-1"
        );
        final CompletableFuture<Page<Product>> products = productService.findAllDeletedProducts();
        final Page<Product> result = products.get();

        assertThat(result).hasSize(3);
        assertThat(
                result.getContent().get(0)
        ).has(firstProduct);
    }

    @Test
    void saveProduct() throws ExecutionException, InterruptedException {
        Product newProduct = new Product(
                "PD-NEW", 1,
                new BigDecimal("200.00")
        );

        final CompletableFuture<Optional<Product>> product =
                productService.saveProduct(newProduct).thenApply(product1 -> {
                    try {
                        return productService.findProductById(product1.getId()).get();
                    } catch (InterruptedException | ExecutionException e) {
                        return Optional.empty();
                    }
                });

        assertThat(product.get())
                .matches(Optional::isPresent, "is empty");
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
