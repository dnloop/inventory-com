package io.github.dnloop.inventorycom.repository;

import io.github.dnloop.inventorycom.model.Departments;
import io.github.dnloop.inventorycom.model.Locality;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Departments, Integer> {
    Page<Departments> findAllByLocalitiesById(Locality id);
}