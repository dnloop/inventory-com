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

import io.github.dnloop.inventorycom.model.SaleDetail;
import io.github.dnloop.inventorycom.model.SaleDetailBuilder;
import io.github.dnloop.inventorycom.service.SaleService;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test {@link SaleInvoiceDetailsRepository} property in  {@link SaleService}.
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
             "/db/data/insert-sale_detail.sql"
     })
public class SaleDetailsRepositoryTest {
    @Autowired
    SaleService saleService;

    @Test
    void contextLoads() {}

    @Test
    void saleDetailsNull() throws ExecutionException, InterruptedException {
        final CompletableFuture<Optional<SaleDetail>> invoiceDetail = saleService.findDetailById(6);

        assertThat(
                invoiceDetail.get()
        ).matches(Optional::isEmpty, "Must be empty");

    }

    @Test
    void findSaleInvoiceDetailById() throws ExecutionException, InterruptedException {
        final CompletableFuture<Optional<SaleDetail>> invoiceDetail = CompletableFuture.supplyAsync(() -> {
            try {
                return saleService.findDetailById(1).get();
            } catch (InterruptedException | ExecutionException e) {
                return Optional.empty();
            }
        });

        assertThat(invoiceDetail.get())
                .matches(Optional::isPresent, "Must be present");
    }

    @Test
    void findAllSaleInvoiceDetails() throws ExecutionException, InterruptedException {
        final Condition<SaleDetail> detailCondition = new Condition<>(
                saleDetail -> saleDetail.getSaleInvoiceId().equals(1),
                "[Invoice Id] - Must have ID: 1"
        );
        final CompletableFuture<ArrayList<SaleDetail>> invoices = saleService.findAllDetailsById(1);
        final ArrayList<SaleDetail> result = invoices.get();

        assertThat(result).hasSize(3);
        assertThat(
                result.get(0)
        ).has(detailCondition);

    }

    @Test
    void saveSaleInvoiceDetails() throws ExecutionException, InterruptedException {
        SaleDetail newSaleDetail = new SaleDetailBuilder()
                .setSaleInvoiceId(1)
                .setAmount(1)
                .setProductId(1)
                .setUnitPrice(BigDecimal.valueOf(20.00))
                .setSubtotal(BigDecimal.valueOf(20.00))
                .createSaleDetail();

        final CompletableFuture<Optional<SaleDetail>> invoiceDetail =
                saleService.saveDetail(newSaleDetail).thenApply(saleDetail -> {
                    try {
                        return saleService.findDetailById(saleDetail.getId()).get();
                    } catch (InterruptedException | ExecutionException e) {
                        return Optional.empty();
                    }
                });

        final Optional<SaleDetail> result = invoiceDetail.get();

        assertThat(result)
                .matches(Optional::isPresent, "Must be present");

    }

    @Test
    void modifySaleInvoiceDetails() throws ExecutionException, InterruptedException {
        final Timestamp ts = Timestamp.from(Instant.now());
        final SaleDetail editDetail = saleService.findDetailById(1).join().orElse(null);

        Objects.requireNonNull(editDetail).setModifiedAt(ts);

        final CompletableFuture<Optional<SaleDetail>> modifiedInvoice =
                saleService.saveDetail(editDetail).thenCompose(
                        invoiceDetail -> saleService.findDetailById(invoiceDetail.getId())
                );

        final Timestamp result;

        if (modifiedInvoice.get().isPresent())
            result = modifiedInvoice.get().get().getModifiedAt();
        else
            result = Timestamp.from(Instant.ofEpochSecond(0L));

        assertThat(result)
                .as("TimeStamp should be equal to %s", result)
                .isEqualTo(ts);
    }
}
