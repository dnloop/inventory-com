package io.github.dnloop.inventorycom.repository;

import io.github.dnloop.inventorycom.model.Departments;
import io.github.dnloop.inventorycom.model.Locality;
import io.github.dnloop.inventorycom.model.Municipality;
import io.github.dnloop.inventorycom.model.Province;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocalityRepository extends JpaRepository<Locality, Integer> {
    Page<Locality> findLocalitiesByMunicipalityId(int id, Pageable pageable);

    Page<Locality> findLocalitiesByDepartamentId(int id, Pageable pageable);

    Page<Locality> findLocalitiesByProvinceId(int id, Pageable pageable);
}
