package io.github.dnloop.inventorycom;

import io.github.dnloop.inventorycom.model.Client;
import io.github.dnloop.inventorycom.service.ClientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.test.context.jdbc.Sql;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@EnableAsync
public class ClientServiceTests {

    @Autowired
    private ClientService clientService;

    @Test
    void contextLoads() {}

    @Test
    void clientNull() {
        final CompletableFuture<Optional<Client>> client = clientService.findById(1);

        assertThat(client)
                .isCompleted()
                .isCompletedWithValueMatching(Optional::isEmpty);
    }


    @Test
    @Sql({"/db/data/localities.sql"})
    void insertClient() throws ExecutionException, InterruptedException {
        Client newClient = new Client(
                1, "Berengaria", "Hanigan",
                "ADDRESS-1", 123456789L, "12345678",
                1, (byte) 0, Timestamp.from(Instant.now()),
                null, null,
                "Joe@dora.biz"
        );

        final CompletableFuture<Optional<Client>> client =
                clientService.save(newClient).thenApply(unused -> {
                    try {
                        return clientService.findById(1).get();
                    } catch (InterruptedException | ExecutionException e) {
                        return Optional.empty();
                    }
                });

        assertThat(client.get())
                .matches(Optional::isPresent, "is empty");
    }

    @Test
    @Sql({"/db/data/localities.sql", "/db/data/clients.sql"})
    void findById() throws ExecutionException, InterruptedException {
        final CompletableFuture<Optional<Client>> client = CompletableFuture.supplyAsync(() -> {
            try {
                return clientService.findById(1).get();
            } catch (InterruptedException | ExecutionException e) {
                return Optional.empty();
            }
        });

        assertThat(client.get())
                .matches(Optional::isPresent, "is empty");
    }

    @Test
    @Sql({"/db/data/localities.sql", "/db/data/clients.sql"})
    void modifyClient() {
        final CompletableFuture<Optional<Client>> client = clientService.findById(1);

        Client editClient = client.join().orElse(null);
        final Timestamp ts = Timestamp.from(Instant.now());
        Objects.requireNonNull(editClient).setModifiedAt(ts);

        CompletableFuture.runAsync(
                () -> clientService.save(editClient)
        );

        final CompletableFuture<Optional<Client>> modifiedClient = clientService.findById(1);

        assertThat(modifiedClient)
                .isCompleted()
                .isCompletedWithValueMatching(
                        result -> result.map(
                                value -> value.getModifiedAt().equals(ts)
                        ).orElse(false)
                );
    }

    @Test
    @Sql({"/db/data/localities.sql", "/db/data/clients.sql"})
    void findAll() {
        final CompletableFuture<Page<Client>> clients = clientService.findAll();

        assertThat(clients)
                .isCompleted()
                .isCompletedWithValueMatching(
                        result -> result.getTotalElements() == 5L
                );
    }

    @Test
    @Sql({"/db/data/localities.sql", "/db/data/clients.sql"})
    void findAllDeleted() {
        final CompletableFuture<Page<Client>> clients = clientService.findAllDeleted();

        assertThat(clients)
                .isCompleted()
                .isCompletedWithValueMatching(
                        result -> result.getTotalElements() == 2L
                );
    }

    @Test
    @Sql({"/db/data/localities.sql", "/db/data/clients.sql"})
    void deleteClient() {
        Client client = new Client(
                1, "Berengaria", "Hanigan",
                "ADDRESS-1", 123456789L, "12345678",
                1, (byte) 0, Timestamp.from(Instant.now()),
                null, null,
                "Joe@dora.biz"
        );

        clientService.delete(client);

        final CompletableFuture<Optional<Client>> clientDeleted = clientService.findDeleted(1);

        assertThat(clientDeleted)
                .isCompleted()
                .isCompletedWithValueMatching(
                        result -> result.map(
                                value -> value.getDeleted() == 0
                        ).orElse(false)
                );
    }

    @Test
    @Sql({"/db/data/localities.sql", "/db/data/clients.sql"})
    void deleteClientCollection() {
        CompletableFuture<Page<Client>> collection = clientService.findAll();

        clientService.deleteAll(collection.join().getContent());

        final CompletableFuture<Page<Client>> clientDeleted = clientService.findAllDeleted();

        assertThat(clientDeleted)
                .isCompleted()
                .isCompletedWithValueMatching(
                        result -> result.getTotalElements() == 5L
                );
    }
}
