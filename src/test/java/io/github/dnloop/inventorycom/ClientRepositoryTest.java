package io.github.dnloop.inventorycom;

import io.github.dnloop.inventorycom.model.Client;
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
        final CompletableFuture<Optional<Client>> client = clientService.findById(6);

        assertThat(
                client.get()
        ).matches(Optional::isEmpty, "is present");
    }

    @Test
    void insertClient() throws ExecutionException, InterruptedException {
        final Condition<Client> clientCondition = new Condition<>(
                product -> product.getSurname().equals("Hanigan"),
                "[Surname] - Hanigan"
        );

        Client newClient = new Client(
                "Berengaria", "Hanigan",
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

        final Optional<Client> result = client.get();

        assertThat(result)
                .matches(Optional::isPresent, "is empty");
        if (result.isPresent())
            assertThat(result.get()).has(clientCondition);
        else
            throw new AssertionError("Result is not present");
    }

    @Test
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

    /**
     * Query a deleted record with a non-delete clause
     */
    @Test
    void findByIdDeleted() throws ExecutionException, InterruptedException {
        final CompletableFuture<Optional<Client>> client = CompletableFuture.supplyAsync(() -> {
            try {
                return clientService.findById(5).get();
            } catch (InterruptedException | ExecutionException e) {
                return Optional.empty();
            }
        });

        assertThat(client.get())
                .matches(Optional::isEmpty, "is present");
    }

    @Test
    void findDeletedClient() throws ExecutionException, InterruptedException {
        final CompletableFuture<Optional<Client>> product = CompletableFuture.supplyAsync(() -> {
            try {
                return clientService.findDeleted(5).get();
            } catch (InterruptedException | ExecutionException e) {
                return Optional.empty();
            }
        });

        assertThat(product.get())
                .matches(Optional::isPresent, "Must be present");
    }

    @Test
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
    void findAll() throws ExecutionException, InterruptedException {
        final Condition<Client> firstClient = new Condition<>(
                client -> client.getSurname().equalsIgnoreCase("Benson"),
                "[Surname] - Benson"
        );
        final CompletableFuture<Page<Client>> clients = clientService.findAll();
        final Page<Client> result = clients.get();

        assertThat(result).hasSize(3);
        assertThat(
                result.getContent().get(0)
        ).has(firstClient);
    }

    @Test
    void findAllDeleted() throws ExecutionException, InterruptedException {
        final CompletableFuture<Page<Client>> clients = clientService.findAllDeleted();
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
