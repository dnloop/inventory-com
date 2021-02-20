package io.github.dnloop.inventorycom.repository;

import io.github.dnloop.inventorycom.model.SaleInvoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.TreeSet;

public interface SaleInvoiceRepository extends JpaRepository<SaleInvoice, Integer> {
    Page<SaleInvoice> findAll(Pageable pageable);

    @Query("SELECT saleInvoice FROM SaleInvoice saleInvoice WHERE saleInvoice.deleted = 1")
    Page<SaleInvoice> findAllDeleted(Pageable pageable);
}
