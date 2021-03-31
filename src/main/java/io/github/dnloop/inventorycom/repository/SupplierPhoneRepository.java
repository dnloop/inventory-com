package io.github.dnloop.inventorycom.repository;

import io.github.dnloop.inventorycom.model.SupplierPhone;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.LinkedHashSet;
import java.util.Optional;

public interface SupplierPhoneRepository extends CrudRepository<SupplierPhone, Integer> {

    @Query("SELECT supplierPhone FROM SupplierPhone supplierPhone" +
           " WHERE supplierPhone.deleted = 0")
    LinkedHashSet<SupplierPhone> findAll();

    @Query("SELECT supplierPhone FROM SupplierPhone supplierPhone" +
           " WHERE supplierPhone.id = :id" +
           " AND supplierPhone.deleted = 0")
    Optional<SupplierPhone> findById(int id);

    @Query("SELECT supplierPhone FROM SupplierPhone supplierPhone" +
           " WHERE supplierPhone.deleted = 1")
    LinkedHashSet<SupplierPhone> findAllDeleted();

    @Query("SELECT supplierPhone FROM SupplierPhone supplierPhone" +
           " WHERE supplierPhone.id = :id" +
           " AND supplierPhone.deleted = 1")
    Optional<SupplierPhone> findDeleted(int id);
}
