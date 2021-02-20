package io.github.dnloop.inventorycom.repository;

import io.github.dnloop.inventorycom.model.Supplier;
import io.github.dnloop.inventorycom.model.SupplierCatalog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierCatalogRepository extends JpaRepository<SupplierCatalog, Integer> {

    Page<SupplierCatalog> findAll(Pageable pageable);

    Page<SupplierCatalog> findSupplierCatalogBySupplierBySupplierIdOrderBySupplierBySupplierIdAsc(
            Supplier id, Pageable pageable
    );

    @Query("SELECT supplierCatalog FROM SupplierCatalog supplierCatalog WHERE supplierCatalog.deleted = 1")
    Page<SupplierCatalog> findAllDeleted(Pageable pageable);
}
