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

package io.github.dnloop.inventorycom.repository;

import io.github.dnloop.inventorycom.model.Category;
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

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Test {@link CategoryRepository} property in  {@link ProductService}.
 * <p>
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
                .matches(Optional::isPresent, "Must be present");
    }

    /**
     * Query a deleted record with a non-delete clause
     */
    @Test
    void findCategoryByIdDeleted() throws ExecutionException, InterruptedException {
        final CompletableFuture<Optional<Category>> category = CompletableFuture.supplyAsync(() -> {
            try {
                return productService.findCategoryById(5).get();
            } catch (InterruptedException | ExecutionException e) {
                return Optional.empty();
            }
        });

        assertThat(category.get())
                .matches(Optional::isEmpty, "Must be empty");
    }

    @Test
    void findDeletedCategory() throws ExecutionException, InterruptedException {
        final CompletableFuture<Optional<Category>> category = CompletableFuture.supplyAsync(() -> {
            try {
                return productService.findDeletedCategory(5).get();
            } catch (InterruptedException | ExecutionException e) {
                return Optional.empty();
            }
        });

        assertThat(category.get())
                .matches(Optional::isPresent, "Must be present");
    }

    @Test
    void findAllCategory() throws ExecutionException, InterruptedException {
        final Condition<Category> firstCategory = new Condition<>(
                category -> category.getDescription()
                                    .equalsIgnoreCase("CAT-1"),
                "[Description] - CAT-1"
        );
        final CompletableFuture<Page<Category>> categories = productService.findAllCategory();
        final Page<Category> result = categories.get();

        assertThat(result).hasSize(5);
        assertThat(
                result.getContent().get(0)
        ).has(firstCategory);
    }

    @Test
    void findAllDeletedCategory() throws ExecutionException, InterruptedException {
        final Condition<Category> firstCategory = new Condition<>(
                category -> category.getDescription()
                                    .equalsIgnoreCase("CAT-1"),
                "[Description] - CAT-4"
        );
        final CompletableFuture<Page<Category>> categories = productService.findAllDeletedCategory();
        final Page<Category> result = categories.get();

        assertThat(result).hasSize(4);
        assertThat(
                result.getContent().get(0)
        ).has(firstCategory);
    }

    @Test
    void saveCategory() throws ExecutionException, InterruptedException {
        Category newCategory = new Category("CAT-NEW");

        final CompletableFuture<Optional<Category>> category =
                productService.saveCategory(newCategory).thenApply(category1 -> {
                    try {
                        return productService.findCategoryById(category1.getId()).get();
                    } catch (InterruptedException | ExecutionException e) {
                        return Optional.empty();
                    }
                });

        assertThat(category.get())
                .matches(Optional::isPresent, "Must be present");
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
                productService.findCategoryById(9).thenAccept(category1 -> category1.ifPresent(
                        value -> productService.deleteCategory(value)
                ));


        final CompletableFuture<Optional<Category>> categoryDeleted =
                category.thenCompose(
                        unused -> productService.findDeletedCategory(9)
                );

        assertThat(
                categoryDeleted.get()
        ).matches(Optional::isPresent, "Must be present");
    }

    /**
     * This unit should return empty due to not being able to delete the record.
     */
    @Test
    void failedDeleteCategory() throws ExecutionException, InterruptedException {
        AtomicBoolean state = new AtomicBoolean(true);
        final CompletableFuture<Void> category =
                productService.findCategoryById(1).thenAccept(category1 -> category1.ifPresent(
                        value -> state.set(productService.deleteCategory(value))
                ));


        final CompletableFuture<Optional<Category>> categoryDeleted =
                category.thenCompose(
                        unused -> productService.findDeletedCategory(1)
                );

        Optional<Category> result = categoryDeleted.get();

        assertThat(state).isFalse();
        assertThat(
                result
        ).matches(Optional::isEmpty, "Must be empty");
    }
}
