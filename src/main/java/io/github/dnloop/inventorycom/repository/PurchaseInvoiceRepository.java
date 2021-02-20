package io.github.dnloop.inventorycom.repository;

import io.github.dnloop.inventorycom.model.PurchaseInvoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PurchaseInvoiceRepository extends JpaRepository<PurchaseInvoice, Integer> {
    Page<PurchaseInvoice> findAll(Pageable pageable);

    @Query("SELECT purchaseInvoice FROM PurchaseInvoice purchaseInvoice WHERE purchaseInvoice.deleted = 1")
    Page<PurchaseInvoice> findAllDeleted(Pageable pageable);
}
