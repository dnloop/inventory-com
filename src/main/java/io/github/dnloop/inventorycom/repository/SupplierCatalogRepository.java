package io.github.dnloop.inventorycom.repository;

import io.github.dnloop.inventorycom.model.SupplierCatalog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SupplierCatalogRepository extends JpaRepository<SupplierCatalog, Integer> {

    @Query("SELECT supplierCatalog FROM SupplierCatalog supplierCatalog" +
           " WHERE supplierCatalog.deleted = 0")
    Page<SupplierCatalog> findAll(Pageable pageable);

    @Query("SELECT supplierCatalog FROM SupplierCatalog supplierCatalog" +
           " WHERE supplierCatalog.id = :id" +
           " AND supplierCatalog.deleted = 0")
    Optional<SupplierCatalog> findById(int id);

    @Query("SELECT supplierCatalog FROM SupplierCatalog supplierCatalog" +
           " WHERE supplierCatalog.id = :id" +
           " AND supplierCatalog.deleted = 0")
    Page<SupplierCatalog> findAllBySupplierCatalogId(
            int id, Pageable pageable
    );

    @Query("SELECT supplierCatalog FROM SupplierCatalog supplierCatalog" +
           " WHERE supplierCatalog.deleted = 1")
    Page<SupplierCatalog> findAllDeleted(Pageable pageable);

    @Query("SELECT supplierCatalog FROM SupplierCatalog supplierCatalog" +
           " WHERE supplierCatalog.id = :id" +
           " AND supplierCatalog.deleted = 1")
    Optional<SupplierCatalog> findDeleted(int id);
}
