package io.github.dnloop.inventorycom;

import io.github.dnloop.inventorycom.model.Presentation;
import io.github.dnloop.inventorycom.model.Product;
import io.github.dnloop.inventorycom.repository.PresentationRepository;
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
 * Test {@link PresentationRepository} property in  {@link ProductService}.
 * TODO adjust test values according to test data
 */
@SpringBootTest
@EnableAsync
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class PresentationRepositoryTest {

    @Autowired
    ProductService productService;

    @Test
    void findPresentationById() throws ExecutionException, InterruptedException {
        final CompletableFuture<Optional<Presentation>> presentation = CompletableFuture.supplyAsync(() -> {
            try {
                return productService.findPresentationById(1).get();
            } catch (InterruptedException | ExecutionException e) {
                return Optional.empty();
            }
        });

        assertThat(presentation.get())
                .matches(Optional::isPresent, "is empty");
    }

    /**
     * Query a deleted record with a non-delete clause
     */
    @Test
    void findPresentationByIdDeleted() throws ExecutionException, InterruptedException {
        final CompletableFuture<Optional<Presentation>> presentation = CompletableFuture.supplyAsync(() -> {
            try {
                return productService.findPresentationById(1).get();
            } catch (InterruptedException | ExecutionException e) {
                return Optional.empty();
            }
        });

        assertThat(presentation.get())
                .matches(Optional::isPresent, "is empty");
    }

    @Test
    void findDeletedPresentation() throws ExecutionException, InterruptedException {
        final CompletableFuture<Optional<Presentation>> client = CompletableFuture.supplyAsync(() -> {
            try {
                return productService.findDeletedPresentation(1).get();
            } catch (InterruptedException | ExecutionException e) {
                return Optional.empty();
            }
        });

        assertThat(client.get())
                .matches(Optional::isEmpty, "is present");
    }


    @Test
    void findAllPresentations() throws ExecutionException, InterruptedException {
        final Condition<Presentation> firstPresentation = new Condition<>(
                presentation -> presentation.getDescription().equalsIgnoreCase("PD-1"),
                "[Description] - PD-1"
        );
        final CompletableFuture<Page<Presentation>> presentations = productService.findAllPresentations();
        final Page<Presentation> result = presentations.get();

        assertThat(result).hasSize(3);
        assertThat(
                result.getContent().get(0)
        ).has(firstPresentation);
    }

    @Test
    void findAllDeletedPresentations() throws ExecutionException, InterruptedException {
        final Condition<Presentation> firstPresentation = new Condition<>(
                presentation -> presentation.getDescription().equalsIgnoreCase("PD-1"),
                "[Description] - PD-1"
        );
        final CompletableFuture<Page<Presentation>> presentations = productService.findAllDeletedPresentations();
        final Page<Presentation> result = presentations.get();

        assertThat(result).hasSize(3);
        assertThat(
                result.getContent().get(0)
        ).has(firstPresentation);
    }

    @Test
    void savePresentation() throws ExecutionException, InterruptedException {
        Product newProduct = new Product(
                "PD-NEW", 1,
                new BigDecimal("200.00")
        );

        final CompletableFuture<Optional<Product>> product =
                productService.saveProduct(newProduct).thenApply(product1 -> {
                    try {
                        return productService.findProductById(product1.getId()).get();
                    } catch (InterruptedException | ExecutionException e) {
                        return Optional.empty();
                    }
                });

        assertThat(product.get())
                .matches(Optional::isPresent, "is empty");
    }

    @Test
    void modifyPresentation() throws ExecutionException, InterruptedException {
        final Timestamp ts = Timestamp.from(Instant.now());
        final Presentation editPresentation = productService.findPresentationById(1).join().orElse(null);

        Objects.requireNonNull(editPresentation).setModifiedAt(ts);

        final CompletableFuture<Optional<Presentation>> modifiedPresentation =
                productService.savePresentation(editPresentation).thenCompose(
                        prod -> productService.findPresentationById(prod.getId())
                );

        final Timestamp result;

        if (modifiedPresentation.get().isPresent())
            result = modifiedPresentation.get().get().getModifiedAt();
        else
            result = Timestamp.from(Instant.ofEpochSecond(0L));

        assertThat(result)
                .as("TimeStamp should be equal to %s", result)
                .isEqualTo(ts);
    }

    @Test
    void deletePresentation() throws ExecutionException, InterruptedException {
        final CompletableFuture<Void> presentation =
                productService.findPresentationById(1).thenAccept(presentation1 -> presentation1.ifPresent(
                        value -> productService.deletePresentation(value)
                ));


        final CompletableFuture<Optional<Presentation>> presentationDeleted =
                presentation.thenCompose(
                        unused -> productService.findDeletedPresentation(1)
                );

        assertThat(
                presentationDeleted.get()
        ).matches(Optional::isPresent, "is empty");
    }
}
