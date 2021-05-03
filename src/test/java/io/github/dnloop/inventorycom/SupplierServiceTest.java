package io.github.dnloop.inventorycom;

import io.github.dnloop.inventorycom.model.Client;
import io.github.dnloop.inventorycom.model.Supplier;
import io.github.dnloop.inventorycom.model.SupplierBuilder;
import io.github.dnloop.inventorycom.repository.SupplierRepository;
import io.github.dnloop.inventorycom.service.SupplierService;
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

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test {@link SupplierRepository} property in  {@link SupplierService}.
 */
@SpringBootTest
@EnableAsync
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Sql({
             "/db/data/insert-localities.sql",
             "/db/data/insert-suppliers.sql"
     })
public class SupplierServiceTest {

    @Autowired
    SupplierService supplierService;

    @Test
    void contextLoads() {}

    @Test
    void purchaseInvoiceNull() throws ExecutionException, InterruptedException {
        final CompletableFuture<Optional<Supplier>> supplier = supplierService.findSupplierById(6);

        assertThat(
                supplier.get()
        ).matches(Optional::isEmpty, "Must be empty");
    }

    @Test
    void findSupplierById() throws ExecutionException, InterruptedException {
        final CompletableFuture<Optional<Supplier>> supplier = CompletableFuture.supplyAsync(() -> {
            try {
                return supplierService.findSupplierById(1).get();
            } catch (InterruptedException | ExecutionException e) {
                return Optional.empty();
            }
        });

        assertThat(supplier.get())
                .matches(Optional::isPresent, "Must be present");
    }

    /**
     * Query a deleted record with a non-delete clause
     */
    @Test
    void findSupplierByIdDeleted() throws ExecutionException, InterruptedException {
        final CompletableFuture<Optional<Supplier>> supplier = CompletableFuture.supplyAsync(() -> {
            try {
                return supplierService.findSupplierById(5).get();
            } catch (InterruptedException | ExecutionException e) {
                return Optional.empty();
            }
        });

        assertThat(supplier.get())
                .matches(Optional::isPresent, "Must be present");
    }

    @Test
    void findDeletedSupplier() throws ExecutionException, InterruptedException {
        final CompletableFuture<Optional<Supplier>> supplier = CompletableFuture.supplyAsync(() -> {
            try {
                return supplierService.findSupplierById(5).get();
            } catch (InterruptedException | ExecutionException e) {
                return Optional.empty();
            }
        });

        assertThat(supplier.get())
                .matches(Optional::isPresent, "Must be present");
    }

    @Test
    void findAllSupplier() throws ExecutionException, InterruptedException {
        final Condition<Supplier> firstSupplier = new Condition<>(
                client -> client.getName().equalsIgnoreCase("SUPPLIER-1"),
                "[name] - Must be SUPPLIER-1"
        );
        final CompletableFuture<Page<Supplier>> clients = supplierService.findAllSuppliers();
        final Page<Supplier> result = clients.get();

        assertThat(result).hasSize(3);
        assertThat(
                result.getContent().get(0)
        ).has(firstSupplier);
    }

    @Test
    void findAllDeletedSupplier() throws ExecutionException, InterruptedException {
        final CompletableFuture<Page<Supplier>> suppliers = supplierService.findAllDeletedSuppliers();
        final Page<Supplier> result = suppliers.get();
        final Condition<Supplier> name = new Condition<>(
                client -> client.getName().equalsIgnoreCase("SUPPLIER-4"),
                "[Name] - Must be SUPPLIER-4"
        );

        assertThat(result).hasSize(2);
        assertThat(result.getContent().get(0)).has(name);
    }

    @Test
    void saveSupplier() throws ExecutionException, InterruptedException {
        final Condition<Supplier> supplierCondition = new Condition<>(
                supplier -> supplier.getName().equals("SUPPLIER-NEW"),
                "[Number] - Must be SUPPLIER-1"
        );

        Supplier newSupplier = new SupplierBuilder()
                .setName("SUPPLIER-NEW")
                .setCuit(22454896521L)
                .setAddress("ADDRESS-NEW")
                .setLocalityId(1)
                .createSupplier();

        final CompletableFuture<Optional<Supplier>> supplier =
                supplierService.saveSupplier(newSupplier).thenApply(spp -> {
                    try {
                        return supplierService.findSupplierById(spp.getId()).get();
                    } catch (InterruptedException | ExecutionException e) {
                        return Optional.empty();
                    }
                });

        final Optional<Supplier> result = supplier.get();

        assertThat(result)
                .matches(Optional::isPresent, "Must be present");
        if (result.isPresent())
            assertThat(result.get()).has(supplierCondition);
        else
            throw new AssertionError("Result is not present");
    }

    @Test
    void modifySupplier() throws ExecutionException, InterruptedException {
        final Timestamp ts = Timestamp.from(Instant.now());
        final Supplier editSupplier = supplierService.findSupplierById(1).join().orElse(null);

        Objects.requireNonNull(editSupplier).setModifiedAt(ts);

        final CompletableFuture<Optional<Supplier>> modifiedSupplier =
                supplierService.saveSupplier(editSupplier).thenCompose(
                        spp -> supplierService.findSupplierById(spp.getId())
                );

        final Timestamp result;

        if (modifiedSupplier.get().isPresent())
            result = modifiedSupplier.get().get().getModifiedAt();
        else
            result = Timestamp.from(Instant.ofEpochSecond(0L));

        assertThat(result)
                .as("TimeStamp should be equal to %s", result)
                .isEqualTo(ts);
    }

    @Test
    void deleteSupplier() throws ExecutionException, InterruptedException {
        final CompletableFuture<Void> supplier =
                supplierService.findSupplierById(1).thenAccept(supplier1 -> supplier1.ifPresent(
                        value -> supplierService.deleteSupplier(value)
                ));


        final CompletableFuture<Optional<Supplier>> supplierDeleted =
                supplier.thenCompose(
                        unused -> supplierService.findDeletedSupplierById(1)
                );

        assertThat(
                supplierDeleted.get()
        ).matches(Optional::isPresent, "Must be present");
    }
}
