/*
 *     Inventory-Com: Inventory and Commerce Management Application.
 *     Copyright (C) 2021. dnloop.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 */

package io.github.dnloop.inventorycom.service;

import io.github.dnloop.inventorycom.model.Product;
import io.github.dnloop.inventorycom.model.ProductBuilder;
import io.github.dnloop.inventorycom.model.ProductDetail;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
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
 * <p><b>What happens when a record references a deleted one?</b></p>
 * <p></p>
 * <p>
 * Two possible scenarios arise: one involves restoring the deleted category (not ideal due to hierarchical structure),
 * other consist in assigning a generic category until deciding where the deleted product must reside. The last option
 * is more viable, everytime a category is deleted the relationships must be updated to a generic value in order
 * to prevent orphaned values.
 * </p>
 * <p>
 * Validation must ensure that products are saved on existing categories. Everytime a category is deleted, it must
 * update its relationships. This is also the case of product detail with its related values. in order to delete one
 * of the children (presentation, measure, material) first it must be validated that no product is assigned.
 * </p>
 * <p></p>
 * <p><b>What happens to its related records when a product detail is deleted?</b></p>
 * <p></p>
 * <p>
 * It could be an issue if trying to find related records from children. Only possible query is from product detail to
 * its relationships.
 * <p/>
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
        ).matches(Optional::isEmpty, "Must be empty");
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
                .matches(Optional::isPresent, "Must be present");
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
                .matches(Optional::isEmpty, "Must be empty");
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
                .matches(Optional::isPresent, "Must be present");
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

        Product newProduct = new ProductBuilder().setDescription("PD-NEW")
                                                 .setCategoryId(2)
                                                 .setProductCode("CODE-NEW")
                                                 .createProduct();

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
                .matches(Optional::isPresent, "Must be present");
        if (result.isPresent())
            assertThat(result.get()).has(productCondition);
        else
            throw new AssertionError("Result is not present");
    }

    @Test
    void saveAllPurchaseShare() throws ExecutionException, InterruptedException {
        List<Product> listProducts = generateProducts();

        final CompletableFuture<Page<Product>> products =
                productService.saveAllProducts(listProducts).thenApply(unused -> {
                    try {
                        return productService.findAllProducts().get();
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                        return Page.empty();
                    }
                });
        final Page<Product> result = products.get();

        assertThat(result).hasSize(14);
    }

    private List<Product> generateProducts() {
        ProductBuilder pb = new ProductBuilder();
        List<Product> list = new ArrayList<>();

        for (int i = 0; i < 10; ++i) {
            Product product = pb.setDescription("DESC-" + i)
                                .setProductCode("CODE-" + i)
                                .setCategoryId(2)
                                .createProduct();
            list.add(product);
        }

        return list;
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
    void batchUpdateProduct() throws ExecutionException, InterruptedException {
        final Timestamp ts = Timestamp.from(Instant.now());
        final Page<Product> listProducts = productService.findAllProducts().join();

        listProducts.forEach(product -> product.setModifiedAt(ts));

        final CompletableFuture<Page<Product>> products =
                productService.saveAllProducts(listProducts.getContent()).thenApply(unused -> {
                    try {
                        return productService.findAllProducts().get();
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                        return Page.empty();
                    }
                });

        final Page<Product> result = products.get();

        result.forEach(product -> assertThat(product.getModifiedAt())
                .as("TimeStamp should be equal to %s", ts)
                .isEqualTo(ts));

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
        ).matches(Optional::isPresent, "Must be present");
    }

    @Test
    void productDetailNull() throws ExecutionException, InterruptedException {
        final CompletableFuture<Optional<ProductDetail>> productDetail = productService.findProductDetailById(7);

        assertThat(
                productDetail.get()
        ).matches(Optional::isEmpty, "Must be empty");
    }

    @Test
    void findProductDetailById() throws ExecutionException, InterruptedException {
        final CompletableFuture<Optional<ProductDetail>> productDetail = CompletableFuture.supplyAsync(() -> {
            try {
                return productService.findProductDetailById(1).get();
            } catch (InterruptedException | ExecutionException e) {
                return Optional.empty();
            }
        });

        assertThat(productDetail.get())
                .matches(Optional::isPresent, "Must be present");
    }

    /**
     * Query a deleted record with a non-delete clause
     */
    @Test
    void findProductDetailByIdDeleted() throws ExecutionException, InterruptedException {
        final CompletableFuture<Optional<ProductDetail>> productDetail = CompletableFuture.supplyAsync(() -> {
            try {
                return productService.findProductDetailById(6).get();
            } catch (InterruptedException | ExecutionException e) {
                return Optional.empty();
            }
        });

        assertThat(productDetail.get())
                .matches(Optional::isEmpty, "Must be present");
    }

    @Test
    void findDeletedProductDetail() throws ExecutionException, InterruptedException {
        final CompletableFuture<Optional<ProductDetail>> productDetail = CompletableFuture.supplyAsync(() -> {
            try {
                return productService.findDeletedProductDetail(6).get();
            } catch (InterruptedException | ExecutionException e) {
                return Optional.empty();
            }
        });

        assertThat(productDetail.get())
                .matches(Optional::isPresent, "Must be present");
    }

    @Test
    void findAllProductDetails() throws ExecutionException, InterruptedException {
        final Condition<ProductDetail> firstProduct = new Condition<>(
                product -> product.getBrand().equalsIgnoreCase("BRAND-1"),
                "[Description] - Must be BRAND-1"
        );
        final CompletableFuture<Page<ProductDetail>> products = productService.findAllProductDetails();
        final Page<ProductDetail> result = products.get();

        assertThat(result).hasSize(5);
        assertThat(
                result.getContent().get(1)
        ).has(firstProduct);
    }


    @Test
    void findAllDeletedProductDetails() throws ExecutionException, InterruptedException {
        final Condition<ProductDetail> firstProductDetail = new Condition<>(
                productDetail -> productDetail.getBrand().equalsIgnoreCase("BRAND-5"),
                "[Description] - Must be BRAND-5"
        );
        final CompletableFuture<Page<ProductDetail>> products = productService.findAllDeletedProductDetails();
        final Page<ProductDetail> result = products.get();

        assertThat(result).hasSize(2);
        assertThat(
                result.getContent().get(0)
        ).has(firstProductDetail);
    }

    @Test
    void saveProductDetail() throws ExecutionException, InterruptedException {
        final Condition<ProductDetail> productDetailCondition = new Condition<>(
                productDetail -> productDetail.getBrand().equalsIgnoreCase("BRAND-NEW"),
                "[Description] - BRAND-NEW"
        );

        ProductDetail newProductDetail = new ProductDetail("BRAND-NEW");

        final CompletableFuture<Optional<ProductDetail>> product =
                productService.saveProductDetail(newProductDetail).thenApply(product1 -> {
                    try {
                        return productService.findProductDetailById(product1.getId()).get();
                    } catch (InterruptedException | ExecutionException e) {
                        return Optional.empty();
                    }
                });
        final Optional<ProductDetail> result = product.get();

        assertThat(result)
                .matches(Optional::isPresent, "Must be present");
        if (result.isPresent())
            assertThat(result.get()).has(productDetailCondition);
        else
            throw new AssertionError("Result is not present");
    }

    @Test
    void modifyProductDetail() throws ExecutionException, InterruptedException {
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
    void deleteProductDetail() throws ExecutionException, InterruptedException {
        final CompletableFuture<Void> productDetail =
                productService.findProductById(1).thenAccept(productDetail1 -> productDetail1.ifPresent(
                        value -> productService.deleteProduct(value)
                ));


        final CompletableFuture<Optional<Product>> productDeleted =
                productDetail.thenCompose(
                        unused -> productService.findDeletedProduct(1)
                );

        assertThat(
                productDeleted.get()
        ).matches(Optional::isPresent, "Must be present");
    }
}
