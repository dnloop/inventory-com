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

import io.github.dnloop.inventorycom.model.SupplierPhone;
import io.github.dnloop.inventorycom.model.Supplier;
import io.github.dnloop.inventorycom.model.SupplierPhone;
import io.github.dnloop.inventorycom.repository.SupplierPhoneRepository;
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
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test {@link SupplierPhoneRepository} property in  {@link SupplierService}.
 */
@SpringBootTest
@EnableAsync
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Sql({
             "/db/data/insert-localities.sql",
             "/db/data/insert-suppliers.sql",
             "/db/data/insert-suppliers_phones.sql"
     })
public class SupplierPhoneRepositoryTest {
    @Autowired
    private SupplierService supplierService;

    @Test
    void contextLoads() {}

    @Test
    void supplierPhoneNull() throws ExecutionException, InterruptedException {
        final CompletableFuture<Optional<SupplierPhone>> supplierPhone = supplierService.findSupplierPhoneById(6);

        assertThat(
                supplierPhone.get()
        ).matches(Optional::isEmpty, "Must be empty");
    }

    @Test
    void insertSupplierPhone() throws ExecutionException, InterruptedException {
        final Condition<SupplierPhone> supplierPhoneCondition = new Condition<>(
                supplierPhone -> supplierPhone.getNumber().equals("123456789012"),
                "[Number] - Must be 12345678"
        );

        SupplierPhone newSupplierPhone = new SupplierPhone(
                "123456789012",
                1
        );

        final CompletableFuture<Optional<SupplierPhone>> supplierPhone =
                supplierService.saveSupplierPhone(newSupplierPhone).thenApply(spp -> {
                    try {
                        return supplierService.findSupplierPhoneById(spp.getId()).get();
                    } catch (InterruptedException | ExecutionException e) {
                        return Optional.empty();
                    }
                });

        final Optional<SupplierPhone> result = supplierPhone.get();

        assertThat(result)
                .matches(Optional::isPresent, "Must not be empty");
        if (result.isPresent())
            assertThat(result.get()).has(supplierPhoneCondition);
        else
            throw new AssertionError("Result is not present");
    }

    @Test
    void findById() throws ExecutionException, InterruptedException {
        final CompletableFuture<Optional<SupplierPhone>> supplierPhone = CompletableFuture.supplyAsync(() -> {
            try {
                return supplierService.findSupplierPhoneById(1).get();
            } catch (InterruptedException | ExecutionException e) {
                return Optional.empty();
            }
        });

        assertThat(supplierPhone.get())
                .matches(Optional::isPresent, "Must be present");
    }

    /**
     * Query a deleted record with a non-delete clause
     */
    @Test
    void findByIdDeleted() throws ExecutionException, InterruptedException {
        final CompletableFuture<Optional<SupplierPhone>> supplierPhone = CompletableFuture.supplyAsync(() -> {
            try {
                return supplierService.findSupplierPhoneById(5).get();
            } catch (InterruptedException | ExecutionException e) {
                return Optional.empty();
            }
        });

        assertThat(supplierPhone.get())
                .matches(Optional::isEmpty, "Must be Empty");
    }

    @Test
    void findDeletedSupplierPhone() throws ExecutionException, InterruptedException {
        final CompletableFuture<Optional<SupplierPhone>> supplierPhone = CompletableFuture.supplyAsync(() -> {
            try {
                return supplierService.findDeletedSupplierPhoneById(5).get();
            } catch (InterruptedException | ExecutionException e) {
                return Optional.empty();
            }
        });

        assertThat(supplierPhone.get())
                .matches(Optional::isPresent, "Must be present");
    }

    @Test
    void modifySupplierPhone() throws ExecutionException, InterruptedException {
        final Timestamp ts = Timestamp.from(Instant.now());
        final SupplierPhone editSupplierPhone = supplierService.findSupplierPhoneById(1).join().orElse(null);

        Objects.requireNonNull(editSupplierPhone).setModifiedAt(ts);

        final CompletableFuture<Optional<SupplierPhone>> modifiedSupplierPhone =
                supplierService.saveSupplierPhone(editSupplierPhone).thenCompose(
                        spp -> supplierService.findSupplierPhoneById(spp.getId())
                );

        final Timestamp result;

        if (modifiedSupplierPhone.get().isPresent())
            result = modifiedSupplierPhone.get().get().getModifiedAt();
        else
            result = Timestamp.from(Instant.ofEpochSecond(0L));

        assertThat(result)
                .as("TimeStamp should be equal to %s", result)
                .isEqualTo(ts);
    }

    @Test
    void findAll() throws ExecutionException, InterruptedException {
        final CompletableFuture<LinkedHashSet<SupplierPhone>> supplierPhones = supplierService.findAllSupplierPhones();
        final LinkedHashSet<SupplierPhone> result = supplierPhones.get();

        assertThat(result).hasSize(3);
    }

    @Test
    void findAllDeleted() throws ExecutionException, InterruptedException {
        final CompletableFuture<LinkedHashSet<SupplierPhone>> clients = supplierService.findAllDeletedSupplierPhones();
        final LinkedHashSet<SupplierPhone> result = clients.get();

        assertThat(result).hasSize(2);
    }

    @Test
    void deleteSupplierPhone() throws ExecutionException, InterruptedException {
        final CompletableFuture<Void> supplierPhone =
                supplierService.findSupplierPhoneById(1).thenAccept(supplierPhone1 -> supplierPhone1.ifPresent(
                        value -> supplierService.deleteSupplierPhone(value)
                ));


        final CompletableFuture<Optional<SupplierPhone>> clientDeleted =
                supplierPhone.thenCompose(
                        unused -> supplierService.findDeletedSupplierPhoneById(1)
                );

        assertThat(
                clientDeleted.get()
        ).matches(Optional::isPresent, "Must be present");
    }
}
