package io.github.dnloop.inventorycom;

import io.github.dnloop.inventorycom.model.Locality;
import io.github.dnloop.inventorycom.service.LocalityService;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Basic locality service tests units. The main purpose is to access related fields
 * by optimizing the search of large elements.
 */
@SpringBootTest
@EnableAsync
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class LocalityServiceTest {

    @Autowired
    private LocalityService localityService;

    @Test
    void contextLoads() {}

    @Test
    @Sql({"/db/data/localities-relation.sql"})
    void findById() throws ExecutionException, InterruptedException {
        final CompletableFuture<Optional<Locality>> client = CompletableFuture.supplyAsync(() -> {
            try {
                return localityService.findLocalityById(1).get();
            } catch (InterruptedException | ExecutionException e) {
                return Optional.empty();
            }
        });

        assertThat(client.get())
                .matches(Optional::isPresent, "is empty");
    }

    @Test
    @Sql({"/db/data/localities-relation.sql"})
    void findAll() throws ExecutionException, InterruptedException {
        final Condition<Locality> firstLocality = new Condition<>(
                locality -> locality.getName().equalsIgnoreCase("LOCALITY-1"),
                "[Name] - LOCALITY-1"
        );
        final CompletableFuture<Page<Locality>> locality = localityService.findAllLocalities();
        final Page<Locality> result = locality.get();

        assertThat(result).hasSize(5);
        assertThat(
                result.getContent().get(0)
        ).has(firstLocality);
    }

    @Test
    @Sql({"/db/data/localities-relation.sql"})
    void findLocalityByMunicipality() {

    }

    @Test
    @Sql({"/db/data/localities-relation.sql"})
    void findLocalitiesByDepartment() {}

    @Test
    @Sql({"/db/data/localities-relation.sql"})
    void findLocalitiesByProvince() {}

    @Test
    @Sql({"/db/data/localities-relation.sql"})
    void findProvinceById() {}

    @Test
    @Sql({"/db/data/localities-relation.sql"})
    void findProvinces() {}

    @Test
    @Sql({"/db/data/localities-relation.sql"})
    void findDepartmentById() {}

    @Test
    @Sql({"/db/data/localities-relation.sql"})
    void findDepartments() {}
}
