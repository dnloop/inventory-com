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

import io.github.dnloop.inventorycom.model.PurchaseShare;
import io.github.dnloop.inventorycom.model.PurchaseShareBuilder;
import io.github.dnloop.inventorycom.service.PurchaseService;
import org.assertj.core.api.Condition;
import org.joda.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test {@link PurchaseShareRepository} property in {@link PurchaseService}.
 * <p>
 * Basic purchase share service tests units. Perform CRUD operations and test relationships with dependencies.
 *
 * <p>
 * Current test units only performs basic CRUD operations. A central use case in the business model
 * requires that given an amount of shares it must create the appropriate due dates of shares on a monthly basis.
 * </p>
 * <p>
 */
@SpringBootTest
@EnableAsync
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Sql({
             "/db/data/insert-localities.sql",
             "/db/data/insert-suppliers.sql",
             "/db/data/insert-category.sql",
             "/db/data/insert-category_level.sql",
             "/db/data/insert-material.sql",
             "/db/data/insert-measure.sql",
             "/db/data/insert-presentation.sql",
             "/db/data/insert-product_detail.sql",
             "/db/data/insert-product.sql",
             "/db/data/insert-purchase_invoice.sql",
             "/db/data/insert-purchase_detail.sql",
             "/db/data/insert-purchase_share.sql"
     })
public class PurchaseShareRepositoryTest {
    @Autowired
    PurchaseService purchaseService;

    @Test
    void contextLoads() {}

    @Test
    void purchaseShareNull() throws ExecutionException, InterruptedException {
        final CompletableFuture<Optional<PurchaseShare>> purchaseShare = purchaseService.findPurchaseShareById(7);

        assertThat(
                purchaseShare.get()
        ).matches(Optional::isEmpty, "Must be empty");
    }

    @Test
    void findPurchaseShareById() throws ExecutionException, InterruptedException {
        final CompletableFuture<Optional<PurchaseShare>> share = CompletableFuture.supplyAsync(() -> {
            try {
                return purchaseService.findPurchaseShareById(1).get();
            } catch (InterruptedException | ExecutionException e) {
                return Optional.empty();
            }
        });

        assertThat(share.get())
                .matches(Optional::isPresent, "Must be present");
    }

    /**
     * Query a deleted record with a non-delete clause
     */
    @Test
    void findPurchaseShareByIdDeleted() throws ExecutionException, InterruptedException {
        final CompletableFuture<Optional<PurchaseShare>> share = CompletableFuture.supplyAsync(() -> {
            try {
                return purchaseService.findPurchaseShareById(4).get();
            } catch (InterruptedException | ExecutionException e) {
                return Optional.empty();
            }
        });

        assertThat(share.get())
                .matches(Optional::isEmpty, "Must be empty");
    }

    @Test
    void findDeletedPurchaseShare() throws ExecutionException, InterruptedException {
        final CompletableFuture<Optional<PurchaseShare>> share = CompletableFuture.supplyAsync(() -> {
            try {
                return purchaseService.findDeletedPurchaseShare(5).get();
            } catch (InterruptedException | ExecutionException e) {
                return Optional.empty();
            }
        });

        assertThat(share.get())
                .matches(Optional::isPresent, "Must be present");
    }

    @Test
    void findAllPurchaseShare() throws ExecutionException, InterruptedException {
        final CompletableFuture<LinkedHashSet<PurchaseShare>> shares = purchaseService.findAllPurchaseShares();
        final LinkedHashSet<PurchaseShare> result = shares.get();

        assertThat(result).hasSize(3);
    }

    @Test
    void findAllPurchaseShareByInvoiceId() throws ExecutionException, InterruptedException {
        final CompletableFuture<LinkedHashSet<PurchaseShare>> shares = purchaseService
                .findAllPurchaseSharesByInvoice(1);
        final LinkedHashSet<PurchaseShare> result = shares.get();

        assertThat(result).hasSize(2);
    }

    @Test
    void findAllDeletedPurchaseShare() throws ExecutionException, InterruptedException {
        final CompletableFuture<LinkedHashSet<PurchaseShare>> shares = purchaseService
                .findAllDeletedPurchaseShares();
        final LinkedHashSet<PurchaseShare> result = shares.get();

        assertThat(result).hasSize(2);
    }

    @Test
    void savePurchaseShare() throws ExecutionException, InterruptedException {
        final LocalDate currentDate = new LocalDate("2021-01-01");
        final Date expectedDate = Date.valueOf("2021-02-01");
        final Condition<PurchaseShare> shareCondition = new Condition<>(
                product -> product.getDueDate().equals(expectedDate),
                "[DueDate] - Due date must be 2021-02-01"
        );

        LocalDate dueDate = purchaseService.createDueDate(currentDate);

        PurchaseShare newShare = new PurchaseShareBuilder()
                .setNumber(1)
                .setDueDate(dueDate)
                .setPurchaseInvoiceId(3)
                .createPurchaseShare();

        final CompletableFuture<Optional<PurchaseShare>> share =
                purchaseService.savePurchaseShare(newShare).thenApply(share1 -> {
                    try {
                        return purchaseService.findPurchaseShareById(share1.getId()).get();
                    } catch (InterruptedException | ExecutionException e) {
                        return Optional.empty();
                    }
                });
        final Optional<PurchaseShare> result = share.get();

        assertThat(result)
                .matches(Optional::isPresent, "Must be present");
        if (result.isPresent())
            assertThat(result.get()).has(shareCondition);
        else
            throw new AssertionError("Result is not present");

    }

    @Test
    void saveAllPurchaseShare() throws ExecutionException, InterruptedException {
        final LocalDate currentDate = new LocalDate("2021-01-01");
        int months = 1;

        List<PurchaseShare> listShares = purchaseService.generateShares(6, currentDate);

        final CompletableFuture<LinkedHashSet<PurchaseShare>> shares =
                purchaseService.saveAllPurchaseShares(listShares).thenApply(unused -> {
                    try {
                        return purchaseService.findAllPurchaseSharesByInvoice(3).get();
                    } catch (InterruptedException | ExecutionException e) {
                        return new LinkedHashSet<>();
                    }
                });
        final LinkedHashSet<PurchaseShare> result = shares.get();

        assertThat(result).hasSize(6);
        for (PurchaseShare share : result) {
            LocalDate expectedDate = purchaseService.createDueDate(currentDate, months);
            assertThat(share.getDueDate()).isEqualTo(expectedDate);
            ++months;
        }
    }

    @Test
    void modifyPurchaseShare() throws ExecutionException, InterruptedException {
        final Timestamp ts = Timestamp.from(Instant.now());
        final PurchaseShare editShare = purchaseService.findPurchaseShareById(1).join().orElse(null);

        Objects.requireNonNull(editShare).setModifiedAt(ts);

        final CompletableFuture<Optional<PurchaseShare>> modifiedShare =
                purchaseService.savePurchaseShare(editShare).thenCompose(
                        share -> purchaseService.findPurchaseShareById(share.getId())
                );

        final Timestamp result;

        if (modifiedShare.get().isPresent())
            result = modifiedShare.get().get().getModifiedAt();
        else
            result = Timestamp.from(Instant.ofEpochSecond(0L));

        assertThat(result)
                .as("TimeStamp should be equal to %s", result)
                .isEqualTo(ts);

    }

    @Test
    void deletePurchaseShare() throws ExecutionException, InterruptedException {
        final CompletableFuture<Void> productDetail =
                purchaseService.findPurchaseShareById(1).thenAccept(productDetail1 -> productDetail1.ifPresent(
                        value -> purchaseService.deletePurchaseShare(value)
                ));


        final CompletableFuture<Optional<PurchaseShare>> productDeleted =
                productDetail.thenCompose(
                        unused -> purchaseService.findDeletedPurchaseShare(1)
                );

        assertThat(
                productDeleted.get()
        ).matches(Optional::isPresent, "Must be present");

    }
}
