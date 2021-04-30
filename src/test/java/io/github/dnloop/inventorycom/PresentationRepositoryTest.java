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

import io.github.dnloop.inventorycom.model.Presentation;
import io.github.dnloop.inventorycom.repository.PresentationRepository;
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
 * Test {@link PresentationRepository} property in  {@link ProductService}.
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
public class PresentationRepositoryTest {

    @Autowired
    ProductService productService;

    @Test
    void findPresentationById() throws ExecutionException, InterruptedException {
        final CompletableFuture<Optional<Presentation>> presentation = CompletableFuture.supplyAsync(() -> {
            try {
                return productService.findPresentationById(1).get();
            } catch (InterruptedException | ExecutionException e) {
                return Optional.empty();
            }
        });

        assertThat(presentation.get())
                .matches(Optional::isPresent, "Must be present");
    }

    /**
     * Query a deleted record with a non-delete clause
     */
    @Test
    void findPresentationByIdDeleted() throws ExecutionException, InterruptedException {
        final CompletableFuture<Optional<Presentation>> presentation = CompletableFuture.supplyAsync(() -> {
            try {
                return productService.findPresentationById(5).get();
            } catch (InterruptedException | ExecutionException e) {
                return Optional.empty();
            }
        });

        assertThat(presentation.get())
                .matches(Optional::isEmpty, "Must be empty");
    }

    @Test
    void findDeletedPresentation() throws ExecutionException, InterruptedException {
        final CompletableFuture<Optional<Presentation>> presentation = CompletableFuture.supplyAsync(() -> {
            try {
                return productService.findDeletedPresentation(5).get();
            } catch (InterruptedException | ExecutionException e) {
                return Optional.empty();
            }
        });

        assertThat(presentation.get())
                .matches(Optional::isPresent, "Must be present");
    }


    @Test
    void findAllPresentations() throws ExecutionException, InterruptedException {
        final Condition<Presentation> firstPresentation = new Condition<>(
                presentation -> presentation.getDescription().equalsIgnoreCase("Bag"),
                "[Description] - Must be Bag"
        );
        final CompletableFuture<Page<Presentation>> presentations = productService.findAllPresentations();
        final Page<Presentation> result = presentations.get();

        assertThat(result).hasSize(4);
        assertThat(
                result.getContent().get(1) // first index default value
        ).has(firstPresentation);
    }

    @Test
    void findAllDeletedPresentations() throws ExecutionException, InterruptedException {
        final Condition<Presentation> firstPresentation = new Condition<>(
                presentation -> presentation.getDescription().equalsIgnoreCase("Bag"),
                "[Description] - Bag"
        );
        final CompletableFuture<Page<Presentation>> presentations = productService.findAllDeletedPresentations();
        final Page<Presentation> result = presentations.get();

        assertThat(result).hasSize(2);
        assertThat(
                result.getContent().get(0)
        ).has(firstPresentation);
    }

    @Test
    void savePresentation() throws ExecutionException, InterruptedException {
        Presentation newPresentation = new Presentation("PD-NEW");

        final CompletableFuture<Optional<Presentation>> presentation =
                productService.savePresentation(newPresentation).thenApply(presentation1 -> {
                    try {
                        return productService.findPresentationById(presentation1.getId()).get();
                    } catch (InterruptedException | ExecutionException e) {
                        return Optional.empty();
                    }
                });

        assertThat(presentation.get())
                .matches(Optional::isPresent, "Must be present");
    }

    @Test
    void modifyPresentation() throws ExecutionException, InterruptedException {
        final Timestamp ts = Timestamp.from(Instant.now());
        final Presentation editPresentation = productService.findPresentationById(2).join().orElse(null);

        Objects.requireNonNull(editPresentation).setModifiedAt(ts);

        final CompletableFuture<Optional<Presentation>> modifiedPresentation =
                productService.savePresentation(editPresentation).thenCompose(
                        presentation -> productService.findPresentationById(presentation.getId())
                );

        final Timestamp result;

        if (modifiedPresentation.get().isPresent())
            result = modifiedPresentation.get().get().getModifiedAt();
        else
            result = Timestamp.from(Instant.ofEpochSecond(0L));

        assertThat(result)
                .as("TimeStamp should be equal to %s", result)
                .isEqualTo(ts);
    }

    @Test
    void deletePresentation() throws ExecutionException, InterruptedException {
        final CompletableFuture<Void> presentation =
                productService.findPresentationById(4).thenAccept(presentation1 -> presentation1.ifPresent(
                        value -> productService.deletePresentation(value)
                ));


        final CompletableFuture<Optional<Presentation>> presentationDeleted =
                presentation.thenCompose(
                        unused -> productService.findDeletedPresentation(4)
                );

        assertThat(
                presentationDeleted.get()
        ).matches(Optional::isPresent, "Must be present");
    }

    /**
     * This unit should return empty due to not being able to delete the record.
     */
    @Test
    void failedDeletePresentation() throws ExecutionException, InterruptedException {
        AtomicBoolean state = new AtomicBoolean(true);
        final CompletableFuture<Void> presentation =
                productService.findPresentationById(1).thenAccept(presentation1 -> presentation1.ifPresent(
                        value -> state.set(productService.deletePresentation(value))
                ));


        final CompletableFuture<Optional<Presentation>> measureDeleted =
                presentation.thenCompose(
                        unused -> productService.findDeletedPresentation(1)
                );

        Optional<Presentation> result = measureDeleted.get();

        assertThat(state).isFalse();
        assertThat(
                result
        ).matches(Optional::isEmpty, "Must be empty");
    }
}
