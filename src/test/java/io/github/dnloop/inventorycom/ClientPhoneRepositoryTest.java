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

import io.github.dnloop.inventorycom.model.Client;
import io.github.dnloop.inventorycom.model.ClientPhone;
import io.github.dnloop.inventorycom.repository.ClientPhoneRepository;
import io.github.dnloop.inventorycom.service.ClientService;
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
 * Test {@link ClientPhoneRepository} property in  {@link ClientService}.
 */
@SpringBootTest
@EnableAsync
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Sql({
             "/db/data/insert-localities.sql",
             "/db/data/insert-clients.sql",
             "/db/data/insert-client_phones.sql"
     })
public class ClientPhoneRepositoryTest {
    @Autowired
    private ClientService clientPhoneService;

    @Test
    void contextLoads() {}

    @Test
    void clientPhonePhonesNull() throws ExecutionException, InterruptedException {
        final CompletableFuture<Optional<ClientPhone>> clientPhone = clientPhoneService.findClientPhoneById(6);

        assertThat(
                clientPhone.get()
        ).matches(Optional::isEmpty, "Must be empty");
    }

    @Test
    void insertClientPhones() throws ExecutionException, InterruptedException {
        final Condition<ClientPhone> clientPhoneCondition = new Condition<>(
                clientPhone -> clientPhone.getNumber().equals(""),
                "[Number] - Must be 12345678"
        );

        ClientPhone newClientPhone = new ClientPhone("12345678", 1);

        final CompletableFuture<Optional<ClientPhone>> clientPhone =
                clientPhoneService.saveClientPhone(newClientPhone).thenApply(cli -> {
                    try {
                        return clientPhoneService.findClientPhoneById(cli.getId()).get();
                    } catch (InterruptedException | ExecutionException e) {
                        return Optional.empty();
                    }
                });

        final Optional<ClientPhone> result = clientPhone.get();

        assertThat(result)
                .matches(Optional::isPresent, "Must be present");
        if (result.isPresent())
            assertThat(result.get()).has(clientPhoneCondition);
        else
            throw new AssertionError("Result is not present");
    }

    @Test
    void findById() throws ExecutionException, InterruptedException {
        final CompletableFuture<Optional<ClientPhone>> clientPhone = CompletableFuture.supplyAsync(() -> {
            try {
                return clientPhoneService.findClientPhoneById(1).get();
            } catch (InterruptedException | ExecutionException e) {
                return Optional.empty();
            }
        });

        assertThat(clientPhone.get())
                .matches(Optional::isPresent, "Must be present");
    }

    /**
     * Query a deleted record with a non-delete clause
     */
    @Test
    void findByIdDeleted() throws ExecutionException, InterruptedException {
        final CompletableFuture<Optional<ClientPhone>> clientPhone = CompletableFuture.supplyAsync(() -> {
            try {
                return clientPhoneService.findClientPhoneById(5).get();
            } catch (InterruptedException | ExecutionException e) {
                return Optional.empty();
            }
        });

        assertThat(clientPhone.get())
                .matches(Optional::isEmpty, "Must be empty");
    }

    @Test
    void findDeletedClientPhone() throws ExecutionException, InterruptedException {
        final CompletableFuture<Optional<ClientPhone>> clientPhone = CompletableFuture.supplyAsync(() -> {
            try {
                return clientPhoneService.findDeletedClientPhoneById(5).get();
            } catch (InterruptedException | ExecutionException e) {
                return Optional.empty();
            }
        });

        assertThat(clientPhone.get())
                .matches(Optional::isPresent, "Must be present");
    }

    @Test
    void modifyClientPhone() throws ExecutionException, InterruptedException {
        final Timestamp ts = Timestamp.from(Instant.now());
        final Client editClient = clientPhoneService.findClientById(1).join().orElse(null);

        Objects.requireNonNull(editClient).setModifiedAt(ts);

        final CompletableFuture<Optional<Client>> modifiedClient =
                clientPhoneService.saveClient(editClient).thenCompose(
                        cli -> clientPhoneService.findClientById(cli.getId())
                );

        final Timestamp result;

        if (modifiedClient.get().isPresent())
            result = modifiedClient.get().get().getModifiedAt();
        else
            result = Timestamp.from(Instant.ofEpochSecond(0L));

        assertThat(result)
                .as("TimeStamp should be equal to %s", result)
                .isEqualTo(ts);
    }

    @Test
    void findAll() throws ExecutionException, InterruptedException {
        final Condition<Client> firstClient = new Condition<>(
                clientPhone -> clientPhone.getSurname().equalsIgnoreCase("Benson"),
                "[Surname] - Benson"
        );
        final CompletableFuture<Page<Client>> clientPhones = clientPhoneService.findAllClients();
        final Page<Client> result = clientPhones.get();

        assertThat(result).hasSize(3);
        assertThat(
                result.getContent().get(0)
        ).has(firstClient);
    }

    @Test
    void findAllDeleted() throws ExecutionException, InterruptedException {
        final CompletableFuture<LinkedHashSet<ClientPhone>> clientPhones = clientPhoneService
                .findAllDeletedClientPhones();
        final LinkedHashSet<ClientPhone> result = clientPhones.get();

        assertThat(result).hasSize(2);
    }

    @Test
    void deleteClientPhones() throws ExecutionException, InterruptedException {
        final CompletableFuture<Void> clientPhone =
                clientPhoneService.findClientPhoneById(1).thenAccept(clientPhone1 -> clientPhone1.ifPresent(
                        value -> clientPhoneService.deleteClientPhone(value)
                ));


        final CompletableFuture<Optional<ClientPhone>> clientPhoneDeleted =
                clientPhone.thenCompose(
                        unused -> clientPhoneService.findDeletedClientPhoneById(1)
                );

        assertThat(
                clientPhoneDeleted.get()
        ).matches(Optional::isPresent, "Must be present");
    }
}
