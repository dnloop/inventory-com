package io.github.dnloop.inventorycom.repository;

import io.github.dnloop.inventorycom.model.SaleDetail;
import io.github.dnloop.inventorycom.model.SaleInvoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SaleInvoiceDetailsRepository extends JpaRepository<SaleDetail, Integer> {
    Page<SaleDetail> findAll(Pageable pageable);

    Page<SaleDetail> findAllBySaleInvoiceBySaleInvoiceIdOrderByProductIdAsc(
            SaleInvoice id, Pageable pageable
    );

    @Query("SELECT saleDetail FROM SaleDetail saleDetail WHERE saleDetail.deleted = 1")
    Page<SaleDetail> findAllDeleted(Pageable pageable);
}
