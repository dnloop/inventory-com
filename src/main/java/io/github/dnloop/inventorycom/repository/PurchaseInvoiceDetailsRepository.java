package io.github.dnloop.inventorycom.repository;

import io.github.dnloop.inventorycom.model.PurchaseDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PurchaseInvoiceDetailsRepository extends JpaRepository<PurchaseDetail, Integer> {

    @Query("SELECT purchaseDetail FROM PurchaseDetail purchaseDetail" +
           " WHERE purchaseDetail.deleted = 0")
    Page<PurchaseDetail> findAll(Pageable pageable);

    @Query("SELECT purchaseDetail FROM PurchaseDetail purchaseDetail" +
           " WHERE purchaseDetail.id = :id" +
           " AND purchaseDetail.deleted = 0")
    Optional<PurchaseDetail> findById(int id);

    @Query("SELECT purchaseDetail FROM PurchaseDetail purchaseDetail" +
           " WHERE purchaseDetail.purchaseInvoiceId = :id " +
           " AND purchaseDetail.deleted = 0")
    Page<PurchaseDetail> findAllByPurchaseInvoice(
            int id, Pageable pageable
    );

    @Query("SELECT purchaseDetail FROM PurchaseDetail purchaseDetail" +
           " WHERE purchaseDetail.deleted = 1")
    Page<PurchaseDetail> findAllDeleted(Pageable pageable);

    @Query("SELECT purchaseDetail FROM PurchaseDetail purchaseDetail" +
           " WHERE purchaseDetail.id = :id" +
           " AND purchaseDetail.deleted = 1")
    Optional<PurchaseDetail> findDeleted(int id);
}
