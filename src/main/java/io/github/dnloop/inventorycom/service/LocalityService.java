package io.github.dnloop.inventorycom.service;

import io.github.dnloop.inventorycom.model.Departments;
import io.github.dnloop.inventorycom.model.Locality;
import io.github.dnloop.inventorycom.model.Province;
import io.github.dnloop.inventorycom.repository.DepartmentRepository;
import io.github.dnloop.inventorycom.repository.LocalityRepository;
import io.github.dnloop.inventorycom.repository.ProvinceRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

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
    public CompletableFuture<Slice<Locality>> findLocalityByProvinceId(Province id) {
        return CompletableFuture.completedFuture(
                localityRepository.findLocalityByProvinceByProvinceId(id, pageableFifty)
        );
    }

    @Async
    public CompletableFuture<Slice<Locality>> findLocalityByProvinceId(Province id, Pageable pageable) {
        return CompletableFuture.completedFuture(
                localityRepository.findLocalityByProvinceByProvinceId(id, pageable)
        );
    }

    @Async
    public CompletableFuture<Page<Locality>> findAllLocalitites() {
        return CompletableFuture.completedFuture(localityRepository.findAll(pageableFifty));
    }

    @Async
    public CompletableFuture<Page<Locality>> findAllLocalitites(Pageable pageable) {
        return CompletableFuture.completedFuture(localityRepository.findAll(pageable));
    }

    @Async
    public CompletableFuture<Optional<Departments>> findDepartmentById(Integer id) {
        return CompletableFuture.completedFuture(departmentRepository.findById(id));
    }

    @Async
    public CompletableFuture<Slice<Departments>> findDepartmentByLocality(Locality id) {
        return CompletableFuture.completedFuture(
                departmentRepository.findAllByLocalitiesById(id, pageableFifty)
        );
    }

    @Async
    public CompletableFuture<Slice<Departments>> findDepartmentByLocality(Locality id, Pageable pageable) {
        return CompletableFuture.completedFuture(
                departmentRepository.findAllByLocalitiesById(id, pageable)
        );
    }

    @Async
    public CompletableFuture<Optional<Province>> findProvinceById(Integer id) {
        return CompletableFuture.completedFuture(provinceRepository.findById(id));
    }

    @Async
    public CompletableFuture<LinkedHashSet<Province>> findAllProvinceByLocality() {
        return CompletableFuture.completedFuture(provinceRepository.findAllByOrderByName());
    }
}
