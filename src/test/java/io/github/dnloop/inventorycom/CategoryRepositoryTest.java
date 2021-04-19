package io.github.dnloop.inventorycom;

import io.github.dnloop.inventorycom.model.Category;
import io.github.dnloop.inventorycom.repository.CategoryRepository;
import io.github.dnloop.inventorycom.service.ProductService;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Test {@link CategoryRepository} property in  {@link ProductService}.
 * <p>
 * TODO adjust test values according to test data
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
public class CategoryRepositoryTest {

    @Autowired
    ProductService productService;

    @Test
    void contextLoads() {}

    @Test
    void findCategoryById() throws ExecutionException, InterruptedException {
        final CompletableFuture<Optional<Category>> category = CompletableFuture.supplyAsync(() -> {
            try {
                return productService.findCategoryById(1).get();
            } catch (InterruptedException | ExecutionException e) {
                return Optional.empty();
            }
        });

        assertThat(category.get())
                .matches(Optional::isPresent, "is empty");
    }

    /**
     * Query a deleted record with a non-delete clause
     */
    @Test
    void findCategoryByIdDeleted() throws ExecutionException, InterruptedException {
        final CompletableFuture<Optional<Category>> category = CompletableFuture.supplyAsync(() -> {
            try {
                return productService.findCategoryById(1).get();
            } catch (InterruptedException | ExecutionException e) {
                return Optional.empty();
            }
        });

        assertThat(category.get())
                .matches(Optional::isPresent, "is empty");
    }

    @Test
    void findDeletedCategory() throws ExecutionException, InterruptedException {
        final CompletableFuture<Optional<Category>> category = CompletableFuture.supplyAsync(() -> {
            try {
                return productService.findDeletedCategory(1).get();
            } catch (InterruptedException | ExecutionException e) {
                return Optional.empty();
            }
        });

        assertThat(category.get())
                .matches(Optional::isEmpty, "is present");
    }

    @Test
    void findAllCategory() throws ExecutionException, InterruptedException {
        final Condition<Optional<Category>> firstCategory = new Condition<>(
                category -> category.isPresent()
                            && category.get().getDescription()
                                       .equalsIgnoreCase("CATEGORY-1"),
                "[Description] - CATEGORY-1"
        );
        final CompletableFuture<HashSet<Category>> categories = productService.findAllCategory();
        final HashSet<Category> result = categories.get();

        assertThat(result).hasSize(3);
        assertThat(
                result.stream().findFirst()
        ).has(firstCategory);
    }

    @Test
    void findAllDeletedCategory() throws ExecutionException, InterruptedException {
        final Condition<Optional<Category>> firstCategory = new Condition<>(
                category -> category.isPresent()
                            && category.get().getDescription()
                                       .equalsIgnoreCase("CATEGORY-1"),
                "[Description] - CATEGORY-1"
        );
        final CompletableFuture<HashSet<Category>> categories = productService.findAllDeletedCategory();
        final HashSet<Category> result = categories.get();

        assertThat(result).hasSize(3);
        assertThat(
                result.stream().findFirst()
        ).has(firstCategory);
    }

    @Test
    void saveCategory() throws ExecutionException, InterruptedException {
        Category newCategory = new Category();

        final CompletableFuture<Optional<Category>> category =
                productService.saveCategory(newCategory).thenApply(category1 -> {
                    try {
                        return productService.findCategoryById(category1.getId()).get();
                    } catch (InterruptedException | ExecutionException e) {
                        return Optional.empty();
                    }
                });

        assertThat(category.get())
                .matches(Optional::isPresent, "is empty");
    }

    @Test
    void modifyCategory() throws ExecutionException, InterruptedException {
        final Timestamp ts = Timestamp.from(Instant.now());
        final Category editCategory = productService.findCategoryById(1).join().orElse(null);

        Objects.requireNonNull(editCategory).setModifiedAt(ts);

        final CompletableFuture<Optional<Category>> modifiedCategory =
                productService.saveCategory(editCategory).thenCompose(
                        prod -> productService.findCategoryById(prod.getId())
                );

        final Timestamp result;

        if (modifiedCategory.get().isPresent())
            result = modifiedCategory.get().get().getModifiedAt();
        else
            result = Timestamp.from(Instant.ofEpochSecond(0L));

        assertThat(result)
                .as("TimeStamp should be equal to %s", result)
                .isEqualTo(ts);
    }

    @Test
    void deleteCategory() throws ExecutionException, InterruptedException {
        final CompletableFuture<Void> category =
                productService.findCategoryById(1).thenAccept(category1 -> category1.ifPresent(
                        value -> productService.deleteCategory(value)
                ));


        final CompletableFuture<Optional<Category>> categoryDeleted =
                category.thenCompose(
                        unused -> productService.findDeletedCategory(1)
                );

        assertThat(
                categoryDeleted.get()
        ).matches(Optional::isPresent, "is empty");
    }
}
