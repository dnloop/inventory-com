package io.github.dnloop.inventorycom.repository;

import io.github.dnloop.inventorycom.model.SupplierPhone;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.LinkedHashSet;

public interface SupplierPhoneRepository extends CrudRepository<SupplierPhone, Integer> {
    LinkedHashSet<SupplierPhone> findAll();

    @Query("SELECT supplierPhone FROM SupplierPhone supplierPhone WHERE supplierPhone.deleted = 1")
    LinkedHashSet<SupplierPhone> findAllDeleted();
}
