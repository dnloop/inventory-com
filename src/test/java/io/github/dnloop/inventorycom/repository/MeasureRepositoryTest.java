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

import io.github.dnloop.inventorycom.model.Material;
import io.github.dnloop.inventorycom.model.Measure;
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
 * Test {@link MeasureRepository} property in  {@link ProductService}.
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
public class MeasureRepositoryTest {

    @Autowired
    ProductService productService;

    @Test
    void findMeasureById() throws ExecutionException, InterruptedException {
        final CompletableFuture<Optional<Measure>> measure = CompletableFuture.supplyAsync(() -> {
            try {
                return productService.findMeasuresById(1).get();
            } catch (InterruptedException | ExecutionException e) {
                return Optional.empty();
            }
        });

        assertThat(measure.get())
                .matches(Optional::isPresent, "Must be present");
    }

    /**
     * Query a deleted record with a non-delete clause
     */
    @Test
    void findMeasureByIdDeleted() throws ExecutionException, InterruptedException {
        final CompletableFuture<Optional<Measure>> measure = CompletableFuture.supplyAsync(() -> {
            try {
                return productService.findMeasuresById(5).get();
            } catch (InterruptedException | ExecutionException e) {
                return Optional.empty();
            }
        });

        assertThat(measure.get())
                .matches(Optional::isEmpty, "Must be empty");
    }

    @Test
    void findDeletedMeasure() throws ExecutionException, InterruptedException {
        final CompletableFuture<Optional<Measure>> measure = CompletableFuture.supplyAsync(() -> {
            try {
                return productService.findDeletedMeasures(5).get();
            } catch (InterruptedException | ExecutionException e) {
                return Optional.empty();
            }
        });

        assertThat(measure.get())
                .matches(Optional::isPresent, "Must be present");
    }

    @Test
    void findAllMeasures() throws ExecutionException, InterruptedException {
        final Condition<Measure> firstMeasure = new Condition<>(
                material -> material.getType().equalsIgnoreCase("CM"),
                "[Type] - Must be CM"
        );
        final CompletableFuture<Page<Measure>> measures = productService.findAllMeasures();
        final Page<Measure> result = measures.get();

        assertThat(result).hasSize(4);
        assertThat(
                result.getContent().get(1) // first index default value
        ).has(firstMeasure);
    }

    @Test
    void findAllDeletedMeasures() throws ExecutionException, InterruptedException {
        final Condition<Measure> firstMeasure = new Condition<>(
                material -> material.getType().equalsIgnoreCase("INCH"),
                "[Type] - Must be INCH"
        );
        final CompletableFuture<Page<Measure>> measures = productService.findAllDeletedMeasures();
        final Page<Measure> result = measures.get();

        assertThat(result).hasSize(2);
        assertThat(
                result.getContent().get(0)
        ).has(firstMeasure);
    }

    @Test
    void saveMeasures() throws ExecutionException, InterruptedException {
        Measure newMeasure = new Measure(
                "CM", 1.2, 1.1, 0.0
        );

        final CompletableFuture<Optional<Measure>> measure =
                productService.saveMeasures(newMeasure).thenApply(measure1 -> {
                    try {
                        return productService.findMeasuresById(measure1.getId()).get();
                    } catch (InterruptedException | ExecutionException e) {
                        return Optional.empty();
                    }
                });

        assertThat(measure.get())
                .matches(Optional::isPresent, "Must be present");
    }

    @Test
    void modifyMeasures() throws ExecutionException, InterruptedException {
        final Timestamp ts = Timestamp.from(Instant.now());
        final Measure editMeasures = productService.findMeasuresById(2).join().orElse(null);

        Objects.requireNonNull(editMeasures).setModifiedAt(ts);

        final CompletableFuture<Optional<Measure>> modifiedMeasures =
                productService.saveMeasures(editMeasures).thenCompose(
                        measure -> productService.findMeasuresById(measure.getId())
                );

        final Timestamp result;

        if (modifiedMeasures.get().isPresent())
            result = modifiedMeasures.get().get().getModifiedAt();
        else
            result = Timestamp.from(Instant.ofEpochSecond(0L));

        assertThat(result)
                .as("TimeStamp should be equal to %s", result)
                .isEqualTo(ts);
    }

    @Test
    void deleteMeasures() throws ExecutionException, InterruptedException {
        final CompletableFuture<Void> measures =
                productService.findMeasuresById(4).thenAccept(measures1 -> measures1.ifPresent(
                        value -> productService.deleteMeasures(value)
                ));


        final CompletableFuture<Optional<Measure>> measuresDeleted =
                measures.thenCompose(
                        unused -> productService.findDeletedMeasures(4)
                );

        assertThat(
                measuresDeleted.get()
        ).matches(Optional::isPresent, "Must be present");
    }

    /**
     * This unit should return empty due to not being able to delete the record.
     */
    @Test
    void failedDeleteMeasure() throws ExecutionException, InterruptedException {
        AtomicBoolean state = new AtomicBoolean(true);
        final CompletableFuture<Void> measure =
                productService.findMeasuresById(1).thenAccept(measure1 -> measure1.ifPresent(
                        value -> state.set(productService.deleteMeasures(value))
                ));


        final CompletableFuture<Optional<Material>> measureDeleted =
                measure.thenCompose(
                        unused -> productService.findDeletedMaterial(1)
                );

        Optional<Material> result = measureDeleted.get();

        assertThat(state).isFalse();
        assertThat(
                result
        ).matches(Optional::isEmpty, "Must be empty");
    }
}
