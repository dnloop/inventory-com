package io.github.dnloop.inventorycom.repository;

import io.github.dnloop.inventorycom.model.Departments;
import io.github.dnloop.inventorycom.model.Locality;
import io.github.dnloop.inventorycom.model.Municipality;
import io.github.dnloop.inventorycom.model.Province;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.function.Function;

public interface LocalityRepository extends JpaRepository<Locality, Integer> {

    Page<Locality> findAll(Pageable pageable);

    Page<Locality> findAllByMunicipalityId(int id, Pageable pageable);

    Page<Locality> findAllByDepartamentId(int id, Pageable pageable);

    Page<Locality> findAllByProvinceId(int id, Pageable pageable);
}
