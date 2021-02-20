package io.github.dnloop.inventorycom.repository;

import io.github.dnloop.inventorycom.model.PurchaseDetail;
import io.github.dnloop.inventorycom.model.PurchaseInvoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PurchaseInvoiceDetailsRepository extends JpaRepository<PurchaseDetail, Integer> {
    Page<PurchaseDetail> findAll(Pageable pageable);

    Page<PurchaseDetail> findAllByPurchaseInvoiceByPurchaseInvoiceIdOrderByProductIdAsc(
            PurchaseInvoice id, Pageable pageable
    );

    @Query("SELECT purchaseDetail FROM PurchaseDetail purchaseDetail WHERE purchaseDetail.deleted = 1")
    Page<PurchaseDetail> findAllDeleted(Pageable pageable);
}
