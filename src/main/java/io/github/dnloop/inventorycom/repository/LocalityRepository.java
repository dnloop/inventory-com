package io.github.dnloop.inventorycom.repository;

import io.github.dnloop.inventorycom.model.Locality;
import io.github.dnloop.inventorycom.model.Province;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocalityRepository extends JpaRepository<Locality, Integer> {
    Slice<Locality> findLocalityByProvinceByProvinceId(Province province, Pageable pageable);
}
