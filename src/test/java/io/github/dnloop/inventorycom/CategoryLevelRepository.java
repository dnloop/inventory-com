package io.github.dnloop.inventorycom;

import io.github.dnloop.inventorycom.model.Category;
import io.github.dnloop.inventorycom.model.CategoryLevel;
import io.github.dnloop.inventorycom.service.ProductService;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.test.annotation.DirtiesContext;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test {@link CategoryLevelRepository} property in  {@link ProductService}.
 */
@SpringBootTest
@EnableAsync
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class CategoryLevelRepository {

    @Autowired
    ProductService productService;

    @Test
    void findCategoryLevelById() throws ExecutionException, InterruptedException {
        final CompletableFuture<Optional<CategoryLevel>> categoryLevel = CompletableFuture.supplyAsync(() -> {
            try {
                return productService.findCategoryLevelById(1).get();
            } catch (InterruptedException | ExecutionException e) {
                return Optional.empty();
            }
        });

        assertThat(categoryLevel.get())
                .matches(Optional::isPresent, "is empty");
    }

    /**
     * Query a deleted record with a non-delete clause
     */
    @Test
    void findCategoryLevelByIdDeleted() throws ExecutionException, InterruptedException {
        final CompletableFuture<Optional<CategoryLevel>> categoryLevel = CompletableFuture.supplyAsync(() -> {
            try {
                return productService.findCategoryLevelById(1).get();
            } catch (InterruptedException | ExecutionException e) {
                return Optional.empty();
            }
        });

        assertThat(categoryLevel.get())
                .matches(Optional::isPresent, "is empty");
    }

    @Test
    void findDeletedCategoryLevel() throws ExecutionException, InterruptedException {
        final CompletableFuture<Optional<CategoryLevel>> categoryLevel = CompletableFuture.supplyAsync(() -> {
            try {
                return productService.findDeletedCategoryLevel(1).get();
            } catch (InterruptedException | ExecutionException e) {
                return Optional.empty();
            }
        });

        assertThat(categoryLevel.get())
                .matches(Optional::isEmpty, "is present");
    }

    @Test
    void findAllCategoryLevel() throws ExecutionException, InterruptedException {
        final Condition<Optional<CategoryLevel>> firstCategoryLevel = new Condition<>(
                category -> category.isPresent()
                            && category.get().getL1()
                                       .equals(1),
                "[L1] - Level-1"
        );
        final CompletableFuture<HashSet<CategoryLevel>> categories = productService.findAllCategoryLevel();
        final HashSet<CategoryLevel> result = categories.get();

        assertThat(result).hasSize(3);
        assertThat(
                result.stream().findFirst()
        ).has(firstCategoryLevel);
    }

    @Test
    void findAllDeletedCategoryLevel() throws ExecutionException, InterruptedException {
        final Condition<Optional<CategoryLevel>> firstCategoryLevel = new Condition<>(
                category -> category.isPresent()
                            && category.get().getL1()
                                       .equals(1),
                "[L1] - Level-1"
        );
        final CompletableFuture<HashSet<CategoryLevel>> categories = productService.findAllDeletedCategoryLevel();
        final HashSet<CategoryLevel> result = categories.get();

        assertThat(result).hasSize(3);
        assertThat(
                result.stream().findFirst()
        ).has(firstCategoryLevel);
    }

    @Test
    void saveCategoryLevel() throws ExecutionException, InterruptedException {
        Category newCategory = new Category("CAT-DESC");

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
    void modifyCategoryLevel() throws ExecutionException, InterruptedException {
        final Timestamp ts = Timestamp.from(Instant.now());
        final CategoryLevel editCategoryLevel = productService.findCategoryLevelById(1).join().orElse(null);

        Objects.requireNonNull(editCategoryLevel).setModifiedAt(ts);

        final CompletableFuture<Optional<CategoryLevel>> modifiedCategoryLevel =
                productService.saveCategoryLevel(editCategoryLevel).thenCompose(
                        prod -> productService.findCategoryLevelById(prod.getId())
                );

        final Timestamp result;

        if (modifiedCategoryLevel.get().isPresent())
            result = modifiedCategoryLevel.get().get().getModifiedAt();
        else
            result = Timestamp.from(Instant.ofEpochSecond(0L));

        assertThat(result)
                .as("TimeStamp should be equal to %s", result)
                .isEqualTo(ts);
    }

    @Test
    void deleteCategoryLevel() throws ExecutionException, InterruptedException {
        final CompletableFuture<Void> categoryLevel =
                productService.findCategoryLevelById(1).thenAccept(categoryLevel1 -> categoryLevel1.ifPresent(
                        value -> productService.deleteCategoryLevel(value)
                ));


        final CompletableFuture<Optional<CategoryLevel>> categoryLevelDeleted =
                categoryLevel.thenCompose(
                        unused -> productService.findDeletedCategoryLevel(1)
                );

        assertThat(
                categoryLevelDeleted.get()
        ).matches(Optional::isPresent, "is empty");
    }
}
