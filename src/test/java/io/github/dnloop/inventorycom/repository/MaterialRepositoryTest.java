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
 * Test {@link MaterialRepository} property in  {@link ProductService}.
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
public class MaterialRepositoryTest {

    @Autowired
    ProductService productService;

    @Test
    void findMaterialById() throws ExecutionException, InterruptedException {
        final CompletableFuture<Optional<Material>> material = CompletableFuture.supplyAsync(() -> {
            try {
                return productService.findMaterialById(2).get();
            } catch (InterruptedException | ExecutionException e) {
                return Optional.empty();
            }
        });

        assertThat(material.get())
                .matches(Optional::isPresent, "Must be present");
    }

    /**
     * Query a deleted record with a non-delete clause
     */
    @Test
    void findMaterialByIdDeleted() throws ExecutionException, InterruptedException {
        final CompletableFuture<Optional<Material>> material = CompletableFuture.supplyAsync(() -> {
            try {
                return productService.findMaterialById(5).get();
            } catch (InterruptedException | ExecutionException e) {
                return Optional.empty();
            }
        });

        assertThat(material.get())
                .matches(Optional::isEmpty, "Must be empty");
    }

    @Test
    void findDeletedMaterial() throws ExecutionException, InterruptedException {
        final CompletableFuture<Optional<Material>> material = CompletableFuture.supplyAsync(() -> {
            try {
                return productService.findDeletedMaterial(5).get();
            } catch (InterruptedException | ExecutionException e) {
                return Optional.empty();
            }
        });

        assertThat(material.get())
                .matches(Optional::isPresent, "Must be present");
    }

    /**
     * 'Unavailable' type is shown first due to being empty, this behaviour is preferred
     * instead of having at the end of list due to possible insertions moving its
     * value to the middle.
     */
    @Test
    void findAllMaterials() throws ExecutionException, InterruptedException {
        final Condition<Material> firstMaterial = new Condition<>(
                material -> material.getType().equalsIgnoreCase("IRON"),
                "[Type] - Must be IRON"
        );
        final CompletableFuture<Page<Material>> materials = productService.findAllMaterials();
        final Page<Material> result = materials.get();

        assertThat(result).hasSize(4);
        assertThat(
                result.getContent().get(1) // list starts at index 1
        ).has(firstMaterial);
    }

    @Test
    void findAllDeletedMaterials() throws ExecutionException, InterruptedException {
        final Condition<Material> firstMaterial = new Condition<>(
                material -> material.getType().equalsIgnoreCase("PLASTIC"),
                "[Type] - must be PLASTIC"
        );
        final CompletableFuture<Page<Material>> materials = productService.findAllDeletedMaterials();
        final Page<Material> result = materials.get();

        assertThat(result).hasSize(2);
        assertThat(
                result.getContent().get(0)
        ).has(firstMaterial);
    }

    @Test
    void saveMaterial() throws ExecutionException, InterruptedException {
        Material newMaterial = new Material("COPPER");

        final CompletableFuture<Optional<Material>> material =
                productService.saveMaterial(newMaterial).thenApply(material1 -> {
                    try {
                        return productService.findMaterialById(material1.getId()).get();
                    } catch (InterruptedException | ExecutionException e) {
                        return Optional.empty();
                    }
                });

        assertThat(material.get())
                .matches(Optional::isPresent, "Must be present");
    }

    @Test
    void modifyMaterial() throws ExecutionException, InterruptedException {
        final Timestamp ts = Timestamp.from(Instant.now());
        final Material editMaterial = productService.findMaterialById(2).join().orElse(null);

        Objects.requireNonNull(editMaterial).setModifiedAt(ts);

        final CompletableFuture<Optional<Material>> modifiedMaterial =
                productService.saveMaterial(editMaterial).thenCompose(
                        material -> productService.findMaterialById(material.getId())
                );

        final Timestamp result;

        if (modifiedMaterial.get().isPresent())
            result = modifiedMaterial.get().get().getModifiedAt();
        else
            result = Timestamp.from(Instant.ofEpochSecond(0L));

        assertThat(result)
                .as("TimeStamp should be equal to %s", result)
                .isEqualTo(ts);
    }

    @Test
    void deleteMaterial() throws ExecutionException, InterruptedException {
        final CompletableFuture<Void> material =
                productService.findMaterialById(4).thenAccept(material1 -> material1.ifPresent(
                        value -> productService.deleteMaterial(value)
                ));


        final CompletableFuture<Optional<Material>> materialDeleted =
                material.thenCompose(
                        unused -> productService.findDeletedMaterial(4)
                );

        assertThat(
                materialDeleted.get()
        ).matches(Optional::isPresent, "Must be present");
    }

    /**
     * This unit should return empty due to not being able to delete the record.
     */
    @Test
    void failedDeleteMaterial() throws ExecutionException, InterruptedException {
        AtomicBoolean state = new AtomicBoolean(true);
        final CompletableFuture<Void> material =
                productService.findMaterialById(1).thenAccept(material1 -> material1.ifPresent(
                        value -> state.set(productService.deleteMaterial(value))
                ));


        final CompletableFuture<Optional<Material>> materialDeleted =
                material.thenCompose(
                        unused -> productService.findDeletedMaterial(1)
                );

        Optional<Material> result = materialDeleted.get();

        assertThat(state).isFalse();
        assertThat(
                result
        ).matches(Optional::isEmpty, "Must be empty");
    }
}
