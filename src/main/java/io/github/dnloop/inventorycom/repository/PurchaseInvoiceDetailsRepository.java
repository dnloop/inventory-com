package io.github.dnloop.inventorycom.repository;

import io.github.dnloop.inventorycom.model.PurchaseDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;

public interface PurchaseInvoiceDetailsRepository extends JpaRepository<PurchaseDetail, Integer> {
    @Query("SELECT purchaseDetail FROM PurchaseDetail purchaseDetail" +
           " WHERE purchaseDetail.purchaseInvoiceId = :invoiceId" +
           " AND purchaseDetail.deleted = 0")
    ArrayList<PurchaseDetail> findAllBySaleInvoiceId(int invoiceId);
}
