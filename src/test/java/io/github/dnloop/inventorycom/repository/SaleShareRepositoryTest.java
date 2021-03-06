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

import io.github.dnloop.inventorycom.model.SaleShare;
import io.github.dnloop.inventorycom.model.SaleShareBuilder;
import io.github.dnloop.inventorycom.service.PurchaseService;
import io.github.dnloop.inventorycom.service.SaleService;
import org.assertj.core.api.Condition;
import org.joda.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

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
 * Test {@link SaleShareRepository} property in {@link PurchaseService}.
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
             "/db/data/insert-clients.sql",
             "/db/data/insert-category.sql",
             "/db/data/insert-category_level.sql",
             "/db/data/insert-material.sql",
             "/db/data/insert-measure.sql",
             "/db/data/insert-presentation.sql",
             "/db/data/insert-product_detail.sql",
             "/db/data/insert-product.sql",
             "/db/data/insert-sale_invoice.sql",
             "/db/data/insert-sale_detail.sql",
             "/db/data/insert-sale_share.sql"
     })
public class SaleShareRepositoryTest {
    @Autowired
    SaleService saleService;

    @Test
    void contextLoads() {}

    @Test
    void saleShareNull() throws ExecutionException, InterruptedException {
        final CompletableFuture<Optional<SaleShare>> purchaseShare = saleService.findSaleShareById(7);

        assertThat(
                purchaseShare.get()
        ).matches(Optional::isEmpty, "Must be empty");
    }

    @Test
    void findSaleShareById() throws ExecutionException, InterruptedException {
        final CompletableFuture<Optional<SaleShare>> share = CompletableFuture.supplyAsync(() -> {
            try {
                return saleService.findSaleShareById(1).get();
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
    void findSaleShareByIdDeleted() throws ExecutionException, InterruptedException {
        final CompletableFuture<Optional<SaleShare>> share = CompletableFuture.supplyAsync(() -> {
            try {
                return saleService.findSaleShareById(4).get();
            } catch (InterruptedException | ExecutionException e) {
                return Optional.empty();
            }
        });

        assertThat(share.get())
                .matches(Optional::isEmpty, "Must be empty");
    }

    @Test
    void findDeletedSaleShare() throws ExecutionException, InterruptedException {
        final CompletableFuture<Optional<SaleShare>> share = CompletableFuture.supplyAsync(() -> {
            try {
                return saleService.findDeletedSaleShare(5).get();
            } catch (InterruptedException | ExecutionException e) {
                return Optional.empty();
            }
        });

        assertThat(share.get())
                .matches(Optional::isPresent, "Must be present");
    }

    @Test
    void findAllSaleShare() throws ExecutionException, InterruptedException {
        final CompletableFuture<LinkedHashSet<SaleShare>> shares = saleService.findAllSaleShares();
        final LinkedHashSet<SaleShare> result = shares.get();

        assertThat(result).hasSize(3);
    }

    @Test
    void findAllSaleShareByInvoiceId() throws ExecutionException, InterruptedException {
        final CompletableFuture<LinkedHashSet<SaleShare>> shares = saleService
                .findAllSaleSharesByInvoice(1);
        final LinkedHashSet<SaleShare> result = shares.get();

        assertThat(result).hasSize(2);
    }

    @Test
    void findAllDeletedSaleShare() throws ExecutionException, InterruptedException {
        final CompletableFuture<LinkedHashSet<SaleShare>> shares = saleService.findAllDeletedSaleShares();
        final LinkedHashSet<SaleShare> result = shares.get();

        assertThat(result).hasSize(2);
    }

    @Test
    void saveSaleShare() throws ExecutionException, InterruptedException {
        final Condition<SaleShare> shareCondition = new Condition<>(
                product -> product.getNumber().equals(1),
                "[Number] - Share number must be 1"
        );

        org.joda.time.LocalDate dueDate = LocalDate.now().plusMonths(1);

        SaleShare newShare = new SaleShareBuilder()
                .setNumber(1)
                .setDueDate(dueDate)
                .setSaleInvoiceId(3)
                .createSaleShare();

        final CompletableFuture<Optional<SaleShare>> share =
                saleService.saveSaleShare(newShare).thenApply(share1 -> {
                    try {
                        return saleService.findSaleShareById(share1.getId()).get();
                    } catch (InterruptedException | ExecutionException e) {
                        return Optional.empty();
                    }
                });
        final Optional<SaleShare> result = share.get();

        assertThat(result)
                .matches(Optional::isPresent, "Must be present");
        if (result.isPresent())
            assertThat(result.get()).has(shareCondition);
        else
            throw new AssertionError("Result is not present");
    }

    @Test
    void saveAllSaleShare() throws ExecutionException, InterruptedException {
        final org.joda.time.LocalDate currentDate = new org.joda.time.LocalDate("2021-01-01");
        int months = 1;

        List<SaleShare> listShares = saleService.generateShares(6, currentDate);

        final CompletableFuture<LinkedHashSet<SaleShare>> shares =
                saleService.saveAllSaleShares(listShares).thenApply(unused -> {
                    try {
                        return saleService.findAllSaleSharesByInvoice(3).get();
                    } catch (InterruptedException | ExecutionException e) {
                        return new LinkedHashSet<>();
                    }
                });
        final LinkedHashSet<SaleShare> result = shares.get();

        assertThat(result).hasSize(6);
        for (SaleShare share : result) {
            LocalDate expectedDate = saleService.createDueDate(currentDate, months);
            assertThat(share.getDueDate()).isEqualTo(expectedDate);
            ++months;
        }
    }

    @Test
    void modifySaleShare() throws ExecutionException, InterruptedException {
        final Timestamp ts = Timestamp.from(Instant.now());
        final SaleShare editShare = saleService.findSaleShareById(1).join().orElse(null);

        Objects.requireNonNull(editShare).setModifiedAt(ts);

        final CompletableFuture<Optional<SaleShare>> modifiedShare =
                saleService.saveSaleShare(editShare).thenCompose(
                        share -> saleService.findSaleShareById(share.getId())
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
    void deleteSaleShare() throws ExecutionException, InterruptedException {
        final CompletableFuture<Void> productDetail =
                saleService.findSaleShareById(1).thenAccept(productDetail1 -> productDetail1.ifPresent(
                        value -> saleService.deleteSaleShare(value)
                ));


        final CompletableFuture<Optional<SaleShare>> productDeleted =
                productDetail.thenCompose(
                        unused -> saleService.findDeletedSaleShare(1)
                );

        assertThat(
                productDeleted.get()
        ).matches(Optional::isPresent, "Must be present");

    }
}
