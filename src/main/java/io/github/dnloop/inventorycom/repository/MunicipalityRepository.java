package io.github.dnloop.inventorycom.repository;

import io.github.dnloop.inventorycom.model.Locality;
import io.github.dnloop.inventorycom.model.Municipality;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MunicipalityRepository extends JpaRepository<Municipality, Integer> {
    Page<Municipality> findAllByLocalitiesById(Locality id);
}
