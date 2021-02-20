package io.github.dnloop.inventorycom.repository;

import io.github.dnloop.inventorycom.model.SaleShare;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.LinkedHashSet;

public interface SaleShareRepository extends CrudRepository<SaleShare, Integer> {
    LinkedHashSet<SaleShare> findAll();

    @Query("SELECT saleShare FROM SaleShare saleShare WHERE saleShare.deleted = 1")
    LinkedHashSet<SaleShare> findAllDeleted();
}
