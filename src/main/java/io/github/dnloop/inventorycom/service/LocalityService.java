package io.github.dnloop.inventorycom.service;

import io.github.dnloop.inventorycom.model.Departments;
import io.github.dnloop.inventorycom.model.Locality;
import io.github.dnloop.inventorycom.model.Municipality;
import io.github.dnloop.inventorycom.model.Province;
import io.github.dnloop.inventorycom.repository.DepartmentRepository;
import io.github.dnloop.inventorycom.repository.LocalityRepository;
import io.github.dnloop.inventorycom.repository.ProvinceRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;


/**
 * By default province is set according the initial application set up to ease
 * data loading for clients.
 * <p>
 * Data hierarchy is as follows:
 * <p>
 * 1. Province
 * 2. Departments
 * 3. Municipality
 * 4. Locality
 */
@Service
public class LocalityService {

    private final ProvinceRepository provinceRepository;

    private final DepartmentRepository departmentRepository;

    private final LocalityRepository localityRepository;

    private final Pageable pageableFifty = PageRequest.of(0, 50);

    public LocalityService(
            ProvinceRepository provinceRepository,
            DepartmentRepository departmentRepository,
            LocalityRepository localityRepository
    ) {
        this.provinceRepository = provinceRepository;
        this.departmentRepository = departmentRepository;
        this.localityRepository = localityRepository;
    }

    @Async
    public CompletableFuture<Optional<Locality>> findLocalityById(Integer id) {
        return CompletableFuture.completedFuture(localityRepository.findById(id));
    }

    @Async
    public CompletableFuture<Page<Locality>> findLocalityByMunicipality(int id, Pageable pageable) {
        return CompletableFuture.completedFuture(
                localityRepository.findLocalitiesByMunicipalityId(id, pageable)
        );
    }

    @Async
    public CompletableFuture<Page<Locality>> findLocalitiesByDepartment(int id) {
        return CompletableFuture.completedFuture(
                localityRepository.findLocalitiesByDepartamentId(id, pageableFifty)
        );
    }

    @Async
    public CompletableFuture<Page<Locality>> findLocalitiesByProvince(int id) {
        return CompletableFuture.completedFuture(
                localityRepository.findLocalitiesByProvinceId(id, pageableFifty)
        );
    }

    @Async
    public CompletableFuture<Optional<Departments>> findDepartmentById(Integer id) {
        return CompletableFuture.completedFuture(departmentRepository.findById(id));
    }

    @Async
    public CompletableFuture<Page<Departments>> findDepartments() {
        return CompletableFuture.completedFuture(
                departmentRepository.findAll(pageableFifty)
        );
    }


    @Async
    public CompletableFuture<Optional<Province>> findProvinceById(Integer id) {
        return CompletableFuture.completedFuture(provinceRepository.findById(id));
    }

    @Async
    public CompletableFuture<LinkedHashSet<Province>> findProvinces() {
        return CompletableFuture.completedFuture(provinceRepository.findAllByOrderByName());
    }
}
