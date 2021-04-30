package io.github.dnloop.inventorycom;

import io.github.dnloop.inventorycom.model.Departments;
import io.github.dnloop.inventorycom.model.Locality;
import io.github.dnloop.inventorycom.model.Province;
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

import java.util.LinkedHashSet;
import java.util.Objects;
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
@Sql({"/db/data/insert-localities_relation.sql"})
public class LocalityServiceTest {

    @Autowired
    private LocalityService localityService;

    @Test
    void contextLoads() {}

    @Test
    void findById() throws ExecutionException, InterruptedException {
        final CompletableFuture<Optional<Locality>> locality = CompletableFuture.supplyAsync(() -> {
            try {
                return localityService.findLocalityById(1).get();
            } catch (InterruptedException | ExecutionException e) {
                return Optional.empty();
            }
        });

        assertThat(locality.get())
                .matches(Optional::isPresent, "Must be present");
    }

    @Test
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
    void findLocalityByMunicipality() throws ExecutionException, InterruptedException {
        final Condition<Locality> name = new Condition<>(
                locality -> locality.getName().equalsIgnoreCase("LOCALITY-3"),
                "[Name] - LOCALITY-3"
        );
        final CompletableFuture<Page<Locality>> locality = localityService.findLocalityByMunicipality(2);
        final Page<Locality> result = locality.get();

        assertThat(result).hasSize(2);
        assertThat(
                result.getContent().get(0)
        ).has(name);
    }

    @Test
    void findLocalitiesByDepartment() throws ExecutionException, InterruptedException {
        final CompletableFuture<Page<Locality>> locality = localityService.findLocalitiesByDepartment(2);
        final Page<Locality> result = locality.get();

        assertThat(result).hasSize(2);
    }

    @Test
    void findLocalitiesByProvince() throws ExecutionException, InterruptedException {
        final CompletableFuture<Page<Locality>> locality = localityService.findLocalitiesByDepartment(2);
        final Page<Locality> result = locality.get();

        assertThat(result).hasSize(2);
    }

    @Test
    void findProvinceById() throws ExecutionException, InterruptedException {
        final CompletableFuture<Optional<Province>> province = CompletableFuture.supplyAsync(() -> {
            try {
                return localityService.findProvinceById(1).get();
            } catch (InterruptedException | ExecutionException e) {
                return Optional.empty();
            }
        });

        assertThat(province.get())
                .matches(Optional::isPresent, "is empty");
    }

    @Test
    void findProvinces() throws ExecutionException, InterruptedException {
        final Condition<Province> firstProvince = new Condition<>(
                province -> province.getName().equalsIgnoreCase("PROVINCE-1"),
                "[Name] - PROVINCE-1"
        );
        final CompletableFuture<LinkedHashSet<Province>> province = localityService.findAllProvinces();
        final LinkedHashSet<Province> result = province.get();

        assertThat(result).hasSize(3);
        assertThat(
                Objects.requireNonNull(
                        result.stream().findFirst().orElse(null)
                )
        ).has(firstProvince);
    }

    @Test
    void findDepartmentById() throws ExecutionException, InterruptedException {
        final CompletableFuture<Optional<Departments>> province = CompletableFuture.supplyAsync(() -> {
            try {
                return localityService.findDepartmentById(1).get();
            } catch (InterruptedException | ExecutionException e) {
                return Optional.empty();
            }
        });

        assertThat(province.get())
                .matches(Optional::isPresent, "Must be present");
    }

    @Test
    void findDepartments() throws ExecutionException, InterruptedException {
        final Condition<Departments> firstDepartment = new Condition<>(
                department -> department.getName().equalsIgnoreCase("DEPARTMENT-1"),
                "[Name] - DEPARTMENT-1"
        );
        final CompletableFuture<Page<Departments>> department = localityService.findAllDepartments();
        final Page<Departments> result = department.get();

        assertThat(result).hasSize(2);
        assertThat(
                result.getContent().get(0)
        ).has(firstDepartment);
    }
}
