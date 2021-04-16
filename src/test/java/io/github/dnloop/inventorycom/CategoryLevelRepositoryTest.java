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

package io.github.dnloop.inventorycom;

import io.github.dnloop.inventorycom.model.CategoryLevel;
import io.github.dnloop.inventorycom.model.Level;
import io.github.dnloop.inventorycom.model.LevelBuilder;
import io.github.dnloop.inventorycom.service.ProductService;
import io.github.dnloop.inventorycom.utils.LevelManager;
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
import java.util.concurrent.atomic.AtomicBoolean;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test {@link CategoryLevelRepositoryTest} property in  {@link ProductService}.
 * <p></p>
 * <p><b>Pending Tests</b></p>
 * <p></p>
 * <li>
 * Check the behaviour of repeated levels, the assumption is ordering will be affected but not hierarchy.
 * </li>
 * <p></p>
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
public class CategoryLevelRepositoryTest {

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

        final Condition<CategoryLevel> levelCondition = new Condition<>(
                category -> !category.getL1().equals(0),
                "[Level] - Top level"
        );
        final CompletableFuture<HashSet<CategoryLevel>> categories = productService.findAllCategoryLevel();
        final HashSet<CategoryLevel> result = categories.get();

        assertThat(result).hasSize(6);
        result.forEach(
                categoryLevel -> assertThat(categoryLevel).has(levelCondition)
        );
    }

    @Test
    void findAllDeletedCategoryLevel() throws ExecutionException, InterruptedException {
        final Condition<CategoryLevel> levelCondition = new Condition<>(
                category -> category.getL1().equals(0)
                            && category.getL2().equals(0)
                            && category.getL3().equals(0)
                            && category.getL4().equals(0),
                "[Level] - Must be 0 (unassigned)"
        );

        final CompletableFuture<HashSet<CategoryLevel>> categories =
                productService.findAllDeletedCategoryLevel();

        final HashSet<CategoryLevel> result = categories.get();

        assertThat(result).hasSize(2);
        result.forEach(categoryLevel -> assertThat(categoryLevel).has(levelCondition));

    }


    @Test
    void findCategoryLevelByCategoryId() throws ExecutionException, InterruptedException {
        final Condition<CategoryLevel> levelCondition = new Condition<>(
                category -> category.getL1().equals(1)
                            && category.getL2().equals(0)
                            && category.getL3().equals(0)
                            && category.getL4().equals(0),
                "[Level] - Not top level"
        );
        final CompletableFuture<Optional<CategoryLevel>> categoryLevel = CompletableFuture.supplyAsync(() -> {
            try {
                return productService.findCategoryLevelByCategoryId(1).get();
            } catch (InterruptedException | ExecutionException e) {
                return Optional.empty();
            }
        });

        final Optional<CategoryLevel> result = categoryLevel.get();

        assertThat(result)
                .matches(Optional::isPresent, "is empty");
        if (result.isPresent())
            assertThat(result.get()).has(levelCondition);
        else
            throw new AssertionError("Result is empty");
    }

    @Test
    void saveCategoryLevel() throws ExecutionException, InterruptedException {
        final Level level = new LevelBuilder().levelOne(1).buildLevel();
        final CategoryLevel newCategory = new CategoryLevel(7, level);
        final Condition<CategoryLevel> categoryLevelCondition = new Condition<>(
                categoryLevel -> categoryLevel.getCategoryByCategoryId().getDescription().equals("CAT-6"),
                "[Description] - Must be CAT-6"
        );
        final CompletableFuture<Optional<CategoryLevel>> category =
                productService.saveCategoryLevel(newCategory).thenApply(category1 -> {
                    try {
                        return productService.findCategoryLevelById(category1.getId()).get();
                    } catch (InterruptedException | ExecutionException e) {
                        return Optional.empty();
                    }
                });

        final Optional<CategoryLevel> result = category.get();
        assertThat(result)
                .matches(Optional::isPresent, "is empty");
        if (result.isPresent())
            assertThat(result.get()).has(categoryLevelCondition);
        else
            throw new AssertionError("result not present");
    }

    @Test
    void insertIntoHierarchy() throws ExecutionException, InterruptedException {
        final Condition<CategoryLevel> categoryDescriptionCondition = new Condition<>(
                categoryLevel -> categoryLevel.getCategoryByCategoryId().getDescription().equals("CAT-5"),
                "[Description] - Must be CAT-5"
        );

        final Condition<CategoryLevel> categoryLevelCondition = new Condition<>(
                categoryLevel -> categoryLevel.getL3().equals(1),
                "[L3] - Must be 1 after increment"
        );

        final CompletableFuture<Optional<CategoryLevel>> category =
                productService.findCategoryLevelByCategoryId(6).thenCompose(categoryLevel1 -> {
                    CategoryLevel newCategory;
                    if (categoryLevel1.isPresent()) {
                        // adjust level number to represent insertion under its hierarchy.
                        Level level = new LevelBuilder()
                                .levelOne(categoryLevel1.get().getL1())
                                .levelTwo(categoryLevel1.get().getL2())
                                .levelThree(categoryLevel1.get().getL3())
                                .levelFour(categoryLevel1.get().getL4())
                                .buildLevel();
                        LevelManager.insertLevel(level);
                        newCategory = new CategoryLevel(7, level);

                        return productService.saveCategoryLevel(newCategory).thenApply(category1 -> {
                            try {
                                return productService.findCategoryLevelById(category1.getId()).get();
                            } catch (InterruptedException | ExecutionException e) {
                                return Optional.empty();
                            }
                        });
                    } else
                        return CompletableFuture.completedFuture(Optional.empty());
                });

        final Optional<CategoryLevel> result = category.get();
        assertThat(result)
                .matches(Optional::isPresent, "is empty");
        if (result.isPresent())
            assertThat(result.get())
                    .has(categoryDescriptionCondition)
                    .has(categoryLevelCondition);
        else
            throw new AssertionError("result not present");
    }

    @Test
    void childNodesExistsInProduct() throws ExecutionException, InterruptedException {
        final Integer nodes = productService.childNodesExistsInProduct(1).get();

        assertThat(nodes).isEqualTo(3);
    }

    @Test
    void categoryLevelExistsInProduct() throws ExecutionException, InterruptedException {
        final Integer products = productService.categoryLevelExistsInProduct(2).get();

        assertThat(products).isEqualTo(2);
    }

    @Test
    void modifyCategoryLevel() throws ExecutionException, InterruptedException {
        final Timestamp ts = Timestamp.from(Instant.now());
        final CategoryLevel editCategoryLevel = productService.findCategoryLevelById(1).join().orElse(null);

        Objects.requireNonNull(editCategoryLevel).setModifiedAt(ts);

        final CompletableFuture<Optional<CategoryLevel>> modifiedCategoryLevel =
                productService.saveCategoryLevel(editCategoryLevel).thenCompose(
                        categoryLevel -> productService.findCategoryLevelById(categoryLevel.getId())
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
    void deleteCategoryLevelRoot() throws ExecutionException, InterruptedException {
        final CompletableFuture<HashSet<CategoryLevel>> categoryLevelDeleted =
                productService.findCategoryLevelByCategoryId(5).thenAccept(
                        categoryLevel -> categoryLevel.ifPresent(
                                level -> productService.deleteCategoryLevel(level)
                        )
                ).thenCompose(unused -> productService.findAllDeletedCategoryLevel());

        HashSet<CategoryLevel> result = categoryLevelDeleted
                .get();

        assertThat(result).hasSize(4);
    }

    @Test
    void deleteCategoryLevelChild() throws ExecutionException, InterruptedException {
        final CompletableFuture<HashSet<CategoryLevel>> categoryLevelDeleted =
                productService.findCategoryLevelByCategoryId(6).thenAccept(
                        categoryLevel -> categoryLevel.ifPresent(
                                level -> productService.deleteCategoryLevel(level)
                        )
                ).thenCompose(unused -> productService.findAllDeletedCategoryLevel());

        HashSet<CategoryLevel> result = categoryLevelDeleted
                .get();

        assertThat(result).hasSize(3);
    }

    @Test
    void failedDeleteCategoryLevelRoot() throws ExecutionException, InterruptedException {
        AtomicBoolean state = new AtomicBoolean(true);
        final CompletableFuture<HashSet<CategoryLevel>> categoryLevelDeleted =
                productService.findCategoryLevelByCategoryId(1).thenAccept(
                        categoryLevel -> categoryLevel.ifPresent(
                                level -> state.set(productService.deleteCategoryLevel(level))
                        )
                ).thenCompose(unused -> productService.findAllDeletedCategoryLevel());

        HashSet<CategoryLevel> result = categoryLevelDeleted
                .get();

        assertThat(state).isFalse();
        assertThat(result).hasSize(2);
    }

    @Test
    void failedDeleteCategoryLevelChild() throws ExecutionException, InterruptedException {
        AtomicBoolean state = new AtomicBoolean(true);
        final CompletableFuture<HashSet<CategoryLevel>> categoryLevelDeleted =
                productService.findCategoryLevelByCategoryId(2).thenAccept(
                        categoryLevel -> categoryLevel.ifPresent(
                                level -> state.set(productService.deleteCategoryLevel(level))
                        )
                ).thenCompose(unused -> productService.findAllDeletedCategoryLevel());

        HashSet<CategoryLevel> result = categoryLevelDeleted
                .get();

        assertThat(state).isFalse();
        assertThat(result).hasSize(2);
    }
}
