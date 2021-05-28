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

import io.github.dnloop.inventorycom.model.Client;
import io.github.dnloop.inventorycom.model.ClientBuilder;
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
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * <p>
 * Basic client repository tests units. Some methods that are meant to be async are executed sequentially in the
 * background due to the nature of the operation such as saving a record and retrieving its results in the same
 * call.
 * </p>
 */
@SpringBootTest
@EnableAsync
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Sql({
             "/db/data/insert-localities.sql",
             "/db/data/insert-clients.sql"
     })
class ClientRepositoryTest {

    @Autowired
    private ClientService clientService;

    @Test
    void contextLoads() {}

    @Test
    void clientNull() throws ExecutionException, InterruptedException {
        final CompletableFuture<Optional<Client>> client = clientService.findClientById(6);

        assertThat(
                client.get()
        ).matches(Optional::isEmpty, "Must be empty");
    }

    @Test
    void insertClient() throws ExecutionException, InterruptedException {
        final Condition<Client> clientCondition = new Condition<>(
                client -> client.getSurname().equals("Hanigan"),
                "[Surname] - Hanigan"
        );

        Client newClient = new ClientBuilder().setName("Berengaria").setSurname("Hanigan").setAddress("ADDRESS-1")
                                              .setCuit(123456789L).setDni("12345678").setLocalityId(1)
                                              .setDeleted((byte) 0).setCreatedAt(Timestamp.from(Instant.now()))
                                              .setModifiedAt(null).setDeletedAt(null).setMail("Joe@dora.biz")
                                              .createClient();

        final CompletableFuture<Optional<Client>> client =
                clientService.saveClient(newClient).thenApply(cli -> {
                    try {
                        return clientService.findClientById(cli.getId()).get();
                    } catch (InterruptedException | ExecutionException e) {
                        return Optional.empty();
                    }
                });

        final Optional<Client> result = client.get();

        assertThat(result)
                .matches(Optional::isPresent, "Must be present");
        if (result.isPresent())
            assertThat(result.get()).has(clientCondition);
        else
            throw new AssertionError("Result is not present");
    }

    @Test
    void findById() throws ExecutionException, InterruptedException {
        final CompletableFuture<Optional<Client>> client = CompletableFuture.supplyAsync(() -> {
            try {
                return clientService.findClientById(1).get();
            } catch (InterruptedException | ExecutionException e) {
                return Optional.empty();
            }
        });

        assertThat(client.get())
                .matches(Optional::isPresent, "Must be present");
    }

    /**
     * Query a deleted record with a non-delete clause
     */
    @Test
    void findByIdDeleted() throws ExecutionException, InterruptedException {
        final CompletableFuture<Optional<Client>> client = CompletableFuture.supplyAsync(() -> {
            try {
                return clientService.findClientById(5).get();
            } catch (InterruptedException | ExecutionException e) {
                return Optional.empty();
            }
        });

        assertThat(client.get())
                .matches(Optional::isEmpty, "Must be empty");
    }

    @Test
    void findDeletedClient() throws ExecutionException, InterruptedException {
        final CompletableFuture<Optional<Client>> client = CompletableFuture.supplyAsync(() -> {
            try {
                return clientService.findDeletedClient(5).get();
            } catch (InterruptedException | ExecutionException e) {
                return Optional.empty();
            }
        });

        assertThat(client.get())
                .matches(Optional::isPresent, "Must be present");
    }

    @Test
    void modifyClient() throws ExecutionException, InterruptedException {
        final Timestamp ts = Timestamp.from(Instant.now());
        final Client editClient = clientService.findClientById(1).join().orElse(null);

        Objects.requireNonNull(editClient).setModifiedAt(ts);

        final CompletableFuture<Optional<Client>> modifiedClient =
                clientService.saveClient(editClient).thenCompose(
                        cli -> clientService.findClientById(cli.getId())
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
                client -> client.getSurname().equalsIgnoreCase("Benson"),
                "[Surname] - Benson"
        );
        final CompletableFuture<Page<Client>> clients = clientService.findAllClients();
        final Page<Client> result = clients.get();

        assertThat(result).hasSize(3);
        assertThat(
                result.getContent().get(0)
        ).has(firstClient);
    }

    @Test
    void findAllDeleted() throws ExecutionException, InterruptedException {
        final CompletableFuture<Page<Client>> clients = clientService.findAllDeletedClients();
        final Page<Client> result = clients.get();
        final Condition<Client> surname = new Condition<>(
                client -> client.getSurname().equalsIgnoreCase("Ayers"),
                "[Surname] - Ayers "
        );

        assertThat(result).hasSize(2);
        assertThat(result.getContent().get(0)).has(surname);
    }

    @Test
    void deleteClient() throws ExecutionException, InterruptedException {
        final CompletableFuture<Void> client =
                clientService.findClientById(1).thenAccept(client1 -> client1.ifPresent(
                        value -> clientService.deleteClient(value)
                ));


        final CompletableFuture<Optional<Client>> clientDeleted =
                client.thenCompose(
                        unused -> clientService.findDeletedClient(1)
                );

        assertThat(
                clientDeleted.get()
        ).matches(Optional::isPresent, "Must be present");
    }

    @Test
    void deleteClientCollection() throws ExecutionException, InterruptedException {

        final CompletableFuture<Void> clients =
                clientService.findAllClients().thenAccept(
                        clients1 -> clientService.deleteAllClients(clients1.getContent())
                );

        final CompletableFuture<Page<Client>> clientsDeleted = clients.thenApply(
                unused -> clientService.findAllDeletedClients().join()
        );

        assertThat(
                clientsDeleted.get().getContent()
        ).hasSize(5);
    }
}
