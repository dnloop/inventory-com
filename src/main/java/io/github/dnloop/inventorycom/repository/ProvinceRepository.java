package io.github.dnloop.inventorycom.repository;

import io.github.dnloop.inventorycom.model.Province;
import org.springframework.data.repository.CrudRepository;

import java.util.LinkedHashSet;


public interface ProvinceRepository extends CrudRepository<Province, Integer> {
    LinkedHashSet<Province> findAllByOrderByNameAsc();
}
