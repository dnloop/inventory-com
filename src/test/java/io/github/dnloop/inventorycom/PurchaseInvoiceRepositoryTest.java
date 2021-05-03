package io.github.dnloop.inventorycom;

import io.github.dnloop.inventorycom.model.PurchaseInvoice;
import io.github.dnloop.inventorycom.model.PurchaseInvoiceBuilder;
import io.github.dnloop.inventorycom.repository.PurchaseInvoiceRepository;
import io.github.dnloop.inventorycom.service.PurchaseService;
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
 * Test {@link PurchaseInvoiceRepository} property in  {@link PurchaseService}.
 */
@SpringBootTest
@EnableAsync
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Sql({
             "/db/data/insert-localities.sql",
             "/db/data/insert-suppliers.sql",
             "/db/data/insert-purchase_invoice.sql",
     })
public class PurchaseInvoiceRepositoryTest {

    @Autowired
    PurchaseService purchaseService;

    @Test
    void contextLoads() {}

    @Test
    void purchaseInvoiceNull() throws ExecutionException, InterruptedException {
        final CompletableFuture<Optional<PurchaseInvoice>> invoice = purchaseService.findInvoiceById(6);

        assertThat(
                invoice.get()
        ).matches(Optional::isEmpty, "Must be empty");
    }

    @Test
    void findPurchaseInvoiceById() throws ExecutionException, InterruptedException {
        final CompletableFuture<Optional<PurchaseInvoice>> invoice = CompletableFuture.supplyAsync(() -> {
            try {
                return purchaseService.findInvoiceById(1).get();
            } catch (InterruptedException | ExecutionException e) {
                return Optional.empty();
            }
        });

        assertThat(invoice.get())
                .matches(Optional::isPresent, "Must be present");
    }

    @Test
    void findAllPurchaseInvoice() throws ExecutionException, InterruptedException {
        final Condition<PurchaseInvoice> firstInvoice = new Condition<>(
                invoice -> invoice.getNumber().equals(1),
                "[Number] - Must be Number 1"
        );
        final CompletableFuture<Page<PurchaseInvoice>> invoices = purchaseService.findAllInvoices();
        final Page<PurchaseInvoice> result = invoices.get();

        assertThat(result).hasSize(5);
        assertThat(
                result.getContent().get(0)
        ).has(firstInvoice);
    }

    @Test
    void savePurchaseInvoice() throws ExecutionException, InterruptedException {
        PurchaseInvoice newInvoice = new PurchaseInvoiceBuilder()
                .setNumber(6)
                .setGenerationDate(Timestamp.from(Instant.now()))
                .setPaymentType("CASH")
                .setInvoiceType("A")
                .setSupplierId(1)
                .setTotal(BigDecimal.valueOf(20000.00))
                .createPurchaseInvoice();

        final CompletableFuture<Optional<PurchaseInvoice>> invoice =
                purchaseService.saveInvoice(newInvoice).thenApply(spp -> {
                    try {
                        return purchaseService.findInvoiceById(spp.getId()).get();
                    } catch (InterruptedException | ExecutionException e) {
                        return Optional.empty();
                    }
                });

        final Optional<PurchaseInvoice> result = invoice.get();

        assertThat(result)
                .matches(Optional::isPresent, "Must be present");
    }

    @Test
    void modifyPurchaseInvoice() throws ExecutionException, InterruptedException {
        final Timestamp ts = Timestamp.from(Instant.now());
        final PurchaseInvoice editInvoice = purchaseService.findInvoiceById(1).join().orElse(null);

        Objects.requireNonNull(editInvoice).setModifiedAt(ts);

        final CompletableFuture<Optional<PurchaseInvoice>> modifiedInvoice =
                purchaseService.saveInvoice(editInvoice).thenCompose(
                        spp -> purchaseService.findInvoiceById(spp.getId())
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
