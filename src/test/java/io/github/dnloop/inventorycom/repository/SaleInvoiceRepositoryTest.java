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

import io.github.dnloop.inventorycom.model.SaleInvoice;
import io.github.dnloop.inventorycom.model.SaleInvoiceBuilder;
import io.github.dnloop.inventorycom.service.SaleService;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test {@link SaleInvoiceRepository} property in  {@link SaleService}.
 */
@SpringBootTest
@EnableAsync
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Sql({
             "/db/data/insert-localities.sql",
             "/db/data/insert-clients.sql",
             "/db/data/insert-sale_invoice.sql"
     })
public class SaleInvoiceRepositoryTest {

    @Autowired
    SaleService saleService;

    @Test
    void contextLoads() {}

    @Test
    void saleInvoiceNull() throws ExecutionException, InterruptedException {
        final CompletableFuture<Optional<SaleInvoice>> invoice = saleService.findInvoiceById(6);

        assertThat(
                invoice.get()
        ).matches(Optional::isEmpty, "Must be empty");

    }

    @Test
    void findSaleInvoiceById() throws ExecutionException, InterruptedException {
        final CompletableFuture<Optional<SaleInvoice>> invoice = CompletableFuture.supplyAsync(() -> {
            try {
                return saleService.findInvoiceById(1).get();
            } catch (InterruptedException | ExecutionException e) {
                return Optional.empty();
            }
        });

        assertThat(invoice.get())
                .matches(Optional::isPresent, "Must be present");
    }

    @Test
    void findAllSaleInvoice() throws ExecutionException, InterruptedException {
        final Condition<SaleInvoice> firstInvoice = new Condition<>(
                invoice -> invoice.getNumber().equals(1),
                "[Number] - Must be Number 1"
        );
        final CompletableFuture<Page<SaleInvoice>> invoices = saleService.findAllInvoices();
        final Page<SaleInvoice> result = invoices.get();

        assertThat(result).hasSize(3);
        assertThat(
                result.getContent().get(0)
        ).has(firstInvoice);

    }

    @Test
    void saveSaleInvoice() throws ExecutionException, InterruptedException {
        SaleInvoice newInvoice = new SaleInvoiceBuilder()
                .setNumber(6)
                .setGenerationDate(Timestamp.from(Instant.now()))
                .setPaymentType("CASH")
                .setInvoiceType("A")
                .setClientId(1)
                .setTotal(BigDecimal.valueOf(2000.00))
                .createSaleInvoice();

        final CompletableFuture<Optional<SaleInvoice>> invoice =
                saleService.saveInvoice(newInvoice).thenApply(saleInvoice -> {
                    try {
                        return saleService.findInvoiceById(saleInvoice.getId()).get();
                    } catch (InterruptedException | ExecutionException e) {
                        return Optional.empty();
                    }
                });

        final Optional<SaleInvoice> result = invoice.get();

        assertThat(result)
                .matches(Optional::isPresent, "Must be present");

    }

    @Test
    void modifySaleInvoice() throws ExecutionException, InterruptedException {
        final Timestamp ts = Timestamp.from(Instant.now());
        final SaleInvoice editInvoice = saleService.findInvoiceById(1).join().orElse(null);

        Objects.requireNonNull(editInvoice).setModifiedAt(ts);

        final CompletableFuture<Optional<SaleInvoice>> modifiedInvoice =
                saleService.saveInvoice(editInvoice).thenCompose(
                        invoice -> saleService.findInvoiceById(invoice.getId())
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
