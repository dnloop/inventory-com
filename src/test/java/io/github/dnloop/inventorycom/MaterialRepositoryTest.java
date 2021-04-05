package io.github.dnloop.inventorycom;

import io.github.dnloop.inventorycom.model.Material;
import io.github.dnloop.inventorycom.model.Product;
import io.github.dnloop.inventorycom.repository.MaterialRepository;
import io.github.dnloop.inventorycom.service.ProductService;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test {@link MaterialRepository} property in  {@link ProductService}.
 * TODO adjust test values according to test data
 */
@SpringBootTest
@EnableAsync
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MaterialRepositoryTest {

    @Autowired
    ProductService productService;

    @Test
    void findMaterialById() throws ExecutionException, InterruptedException {
        final CompletableFuture<Optional<Material>> material = CompletableFuture.supplyAsync(() -> {
            try {
                return productService.findMaterialById(1).get();
            } catch (InterruptedException | ExecutionException e) {
                return Optional.empty();
            }
        });

        assertThat(material.get())
                .matches(Optional::isPresent, "is empty");
    }

    /**
     * Query a deleted record with a non-delete clause
     */
    @Test
    void findMaterialByIdDeleted() throws ExecutionException, InterruptedException {
        final CompletableFuture<Optional<Material>> material = CompletableFuture.supplyAsync(() -> {
            try {
                return productService.findMaterialById(1).get();
            } catch (InterruptedException | ExecutionException e) {
                return Optional.empty();
            }
        });

        assertThat(material.get())
                .matches(Optional::isPresent, "is empty");
    }

    @Test
    void findDeletedMaterial() throws ExecutionException, InterruptedException {
        final CompletableFuture<Optional<Material>> client = CompletableFuture.supplyAsync(() -> {
            try {
                return productService.findDeletedMaterial(1).get();
            } catch (InterruptedException | ExecutionException e) {
                return Optional.empty();
            }
        });

        assertThat(client.get())
                .matches(Optional::isEmpty, "is present");
    }

    @Test
    void findAllMaterials() throws ExecutionException, InterruptedException {
        final Condition<Material> firstMaterial = new Condition<>(
                client -> client.getType().equalsIgnoreCase("TYPE-1"),
                "[Type] - TYPE-1"
        );
        final CompletableFuture<Page<Material>> clients = productService.findAllMaterials();
        final Page<Material> result = clients.get();

        assertThat(result).hasSize(3);
        assertThat(
                result.getContent().get(0)
        ).has(firstMaterial);
    }

    @Test
    void findAllDeletedMaterials() throws ExecutionException, InterruptedException {
        final Condition<Material> firstMaterial = new Condition<>(
                client -> client.getType().equalsIgnoreCase("TYPE-1"),
                "[Type] - TYPE-1"
        );
        final CompletableFuture<Page<Material>> clients = productService.findAllDeletedMaterials();
        final Page<Material> result = clients.get();

        assertThat(result).hasSize(3);
        assertThat(
                result.getContent().get(0)
        ).has(firstMaterial);
    }

    @Test
    void saveMaterial() throws ExecutionException, InterruptedException {
        Material newMaterial = new Material("MATERIAL-TYPE");

        final CompletableFuture<Optional<Material>> product =
                productService.saveMaterial(newMaterial).thenApply(material1 -> {
                    try {
                        return productService.findMaterialById(material1.getId()).get();
                    } catch (InterruptedException | ExecutionException e) {
                        return Optional.empty();
                    }
                });

        assertThat(product.get())
                .matches(Optional::isPresent, "is empty");
    }

    @Test
    void modifyMaterial() throws ExecutionException, InterruptedException {
        final Timestamp ts = Timestamp.from(Instant.now());
        final Material editMaterial = productService.findMaterialById(1).join().orElse(null);

        Objects.requireNonNull(editMaterial).setModifiedAt(ts);

        final CompletableFuture<Optional<Material>> modifiedMaterial =
                productService.saveMaterial(editMaterial).thenCompose(
                        prod -> productService.findMaterialById(prod.getId())
                );

        final Timestamp result;

        if (modifiedMaterial.get().isPresent())
            result = modifiedMaterial.get().get().getModifiedAt();
        else
            result = Timestamp.from(Instant.ofEpochSecond(0L));

        assertThat(result)
                .as("TimeStamp should be equal to %s", result)
                .isEqualTo(ts);
    }

    @Test
    void deleteMaterial() throws ExecutionException, InterruptedException {
        final CompletableFuture<Void> material =
                productService.findMaterialById(1).thenAccept(material1 -> material1.ifPresent(
                        value -> productService.deleteMaterial(value)
                ));


        final CompletableFuture<Optional<Material>> materialDeleted =
                material.thenCompose(
                        unused -> productService.findDeletedMaterial(1)
                );

        assertThat(
                materialDeleted.get()
        ).matches(Optional::isPresent, "is empty");
    }
}
