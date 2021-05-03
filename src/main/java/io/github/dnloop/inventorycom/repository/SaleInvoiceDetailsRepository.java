package io.github.dnloop.inventorycom.repository;

import io.github.dnloop.inventorycom.model.SaleDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;

public interface SaleInvoiceDetailsRepository extends JpaRepository<SaleDetail, Integer> {
    @Query("SELECT saleDetail FROM SaleDetail saleDetail" +
           " WHERE saleDetail.saleInvoiceId = :invoiceId" +
           " AND saleDetail.deleted = 0")
    ArrayList<SaleDetail> findAllBySaleInvoiceId(int invoiceId);
}
