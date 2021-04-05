package io.github.dnloop.inventorycom;

import io.github.dnloop.inventorycom.model.Measure;
import io.github.dnloop.inventorycom.model.Product;
import io.github.dnloop.inventorycom.repository.MeasureRepository;
import io.github.dnloop.inventorycom.service.ProductService;
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
 * Test {@link MeasureRepository} property in  {@link ProductService}.
 * TODO adjust test values according to test data
 */
@SpringBootTest
@EnableAsync
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MeasureRepositoryTest {

    @Autowired
    ProductService productService;

    @Test
    void findMeasureById() throws ExecutionException, InterruptedException {
        final CompletableFuture<Optional<Measure>> measure = CompletableFuture.supplyAsync(() -> {
            try {
                return productService.findMeasuresById(1).get();
            } catch (InterruptedException | ExecutionException e) {
                return Optional.empty();
            }
        });

        assertThat(measure.get())
                .matches(Optional::isPresent, "is empty");
    }

    /**
     * Query a deleted record with a non-delete clause
     */
    @Test
    void findMeasureByIdDeleted() throws ExecutionException, InterruptedException {
        final CompletableFuture<Optional<Measure>> measure = CompletableFuture.supplyAsync(() -> {
            try {
                return productService.findMeasuresById(1).get();
            } catch (InterruptedException | ExecutionException e) {
                return Optional.empty();
            }
        });

        assertThat(measure.get())
                .matches(Optional::isPresent, "is empty");
    }

    @Test
    void findDeletedMeasure() throws ExecutionException, InterruptedException {
        final CompletableFuture<Optional<Measure>> measure = CompletableFuture.supplyAsync(() -> {
            try {
                return productService.findDeletedMeasures(1).get();
            } catch (InterruptedException | ExecutionException e) {
                return Optional.empty();
            }
        });

        assertThat(measure.get())
                .matches(Optional::isEmpty, "is present");
    }

    @Test
    void findAllMeasures() throws ExecutionException, InterruptedException {
        final CompletableFuture<Page<Measure>> measures = productService.findAllMeasures();
        final Page<Measure> result = measures.get();

        assertThat(result).hasSize(3);
        assertThat(
                result.getContent().get(0)
        );
    }

    @Test
    void findAllDeletedMeasures() throws ExecutionException, InterruptedException {
        final CompletableFuture<Page<Measure>> measures = productService.findAllDeletedMeasures();
        final Page<Measure> result = measures.get();

        assertThat(result).hasSize(3);
        assertThat(
                result.getContent().get(0)
        );
    }

    @Test
    void saveMeasures() throws ExecutionException, InterruptedException {
        Measure newMeasure = new Measure(
               "CM" , 1.2, 1.1, 0.0
        );

        final CompletableFuture<Optional<Measure>> measure =
                productService.saveMeasures(newMeasure).thenApply(measure1 -> {
                    try {
                        return productService.findMeasuresById(measure1.getId()).get();
                    } catch (InterruptedException | ExecutionException e) {
                        return Optional.empty();
                    }
                });

        assertThat(measure.get())
                .matches(Optional::isPresent, "is empty");
    }

    @Test
    void modifyMeasures() throws ExecutionException, InterruptedException {
        final Timestamp ts = Timestamp.from(Instant.now());
        final Measure editMeasures = productService.findMeasuresById(1).join().orElse(null);

        Objects.requireNonNull(editMeasures).setModifiedAt(ts);

        final CompletableFuture<Optional<Measure>> modifiedMeasures =
                productService.saveMeasures(editMeasures).thenCompose(
                        prod -> productService.findMeasuresById(prod.getId())
                );

        final Timestamp result;

        if (modifiedMeasures.get().isPresent())
            result = modifiedMeasures.get().get().getModifiedAt();
        else
            result = Timestamp.from(Instant.ofEpochSecond(0L));

        assertThat(result)
                .as("TimeStamp should be equal to %s", result)
                .isEqualTo(ts);
    }

    @Test
    void deleteMeasures() throws ExecutionException, InterruptedException {
        final CompletableFuture<Void> measures =
                productService.findMeasuresById(1).thenAccept(measures1 -> measures1.ifPresent(
                        value -> productService.deleteMeasures(value)
                ));


        final CompletableFuture<Optional<Measure>> measuresDeleted =
                measures.thenCompose(
                        unused -> productService.findDeletedMeasures(1)
                );

        assertThat(
                measuresDeleted.get()
        ).matches(Optional::isPresent, "is empty");
    }
}
