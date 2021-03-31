package io.github.dnloop.inventorycom.repository;

import io.github.dnloop.inventorycom.model.SaleInvoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SaleInvoiceRepository extends JpaRepository<SaleInvoice, Integer> {

    @Query("SELECT saleInvoice FROM SaleInvoice saleInvoice" +
           " WHERE saleInvoice.deleted = 0")
    Page<SaleInvoice> findAll(Pageable pageable);

    @Query("SELECT saleInvoice FROM SaleInvoice saleInvoice" +
           " WHERE saleInvoice.id = :id" +
           " AND saleInvoice.deleted = 0")
    Optional<SaleInvoice> findById(int id);

    @Query("SELECT saleInvoice FROM SaleInvoice saleInvoice" +
           " WHERE saleInvoice.deleted = 1")
    Page<SaleInvoice> findAllDeleted(Pageable pageable);

    @Query("SELECT saleInvoice FROM SaleInvoice saleInvoice" +
           " WHERE saleInvoice.id = :id" +
           " AND saleInvoice.deleted = 1")
    Optional<SaleInvoice> findDeleted(int id);
}
