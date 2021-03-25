package io.github.dnloop.inventorycom;

import io.github.dnloop.inventorycom.model.Client;
import io.github.dnloop.inventorycom.service.ClientService;
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
 * Basic client service tests units. Some methods that are meant to be async are executed sequentially in the
 * background due to the nature of the operation such as saving a record and retrieving its results in the same
 * call.
 */
@SpringBootTest
@EnableAsync
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ClientServiceTests {

    @Autowired
    private ClientService clientService;

    @Test
    void contextLoads() {}

    @Test
    void clientNull() throws ExecutionException, InterruptedException {
        final CompletableFuture<Optional<Client>> client = clientService.findById(6);

        assertThat(
                client.get()
        ).matches(Optional::isEmpty, "is present");
    }


    @Test
    @Sql({"/db/data/localities.sql"})
    void insertClient() throws ExecutionException, InterruptedException {
        Client newClient = new Client(
                6, "Berengaria", "Hanigan",
                "ADDRESS-1", 123456789L, "12345678",
                1, (byte) 0, Timestamp.from(Instant.now()),
                null, null,
                "Joe@dora.biz"
        );

        final CompletableFuture<Optional<Client>> client =
                clientService.save(newClient).thenApply(cli -> {
                    try {
                        return clientService.findById(cli.getId()).get();
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
    void modifyClient() throws ExecutionException, InterruptedException {
        final Timestamp ts = Timestamp.from(Instant.now());
        final Client editClient = clientService.findById(1).join().orElse(null);

        Objects.requireNonNull(editClient).setModifiedAt(ts);

        final CompletableFuture<Optional<Client>> modifiedClient =
                clientService.save(editClient).thenCompose(
                        cli -> clientService.findById(cli.getId())
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
    @Sql({"/db/data/localities.sql", "/db/data/clients.sql"})
    void findAll() throws ExecutionException, InterruptedException {
        final CompletableFuture<Page<Client>> clients = clientService.findAll();

        assertThat(clients.get()).hasSize(3);
    }

    @Test
    @Sql({"/db/data/localities.sql", "/db/data/clients.sql"})
    void findAllDeleted() throws ExecutionException, InterruptedException {
        final CompletableFuture<Page<Client>> clients = clientService.findAllDeleted();

        assertThat(clients.get()).hasSize(2);
    }

    @Test
    @Sql({"/db/data/localities.sql", "/db/data/clients.sql"})
    void deleteClient() throws ExecutionException, InterruptedException {
        final CompletableFuture<Void> client =
                clientService.findById(1).thenAccept(client1 -> client1.ifPresent(
                        value -> clientService.delete(value)
                ));


        final CompletableFuture<Optional<Client>> clientDeleted =
                client.thenCompose(
                        unused -> clientService.findDeleted(1)
                );

        assertThat(
                clientDeleted.get()
        ).matches(Optional::isPresent, "is empty");
    }

    @Test
    @Sql({"/db/data/localities.sql", "/db/data/clients.sql"})
    void deleteClientCollection() throws ExecutionException, InterruptedException {

        final CompletableFuture<Void> clients =
                clientService.findAll().thenAccept(
                        clients1 -> clientService.deleteAll(clients1.getContent())
                );

        final CompletableFuture<Page<Client>> clientsDeleted = clients.thenApply(
                unused -> clientService.findAllDeleted().join()
        );

        assertThat(
                clientsDeleted.get().getContent()
        ).hasSize(5);
    }
}
