package io.github.dnloop.inventorycom.repository;

import io.github.dnloop.inventorycom.model.PurchaseInvoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PurchaseInvoiceRepository extends JpaRepository<PurchaseInvoice, Integer> {

    @Query("SELECT purchaseInvoice FROM PurchaseInvoice purchaseInvoice WHERE purchaseInvoice.deleted = 0")
    Page<PurchaseInvoice> findAll(Pageable pageable);

    @Query("SELECT purchaseInvoice FROM PurchaseInvoice purchaseInvoice" +
           " WHERE purchaseInvoice.id = :id AND purchaseInvoice.deleted = 0")
    Optional<PurchaseInvoice> findById(int id);

    @Query("SELECT purchaseInvoice FROM PurchaseInvoice purchaseInvoice WHERE purchaseInvoice.deleted = 1")
    Page<PurchaseInvoice> findAllDeleted(Pageable pageable);

    @Query("SELECT purchaseInvoice FROM PurchaseInvoice purchaseInvoice" +
           " WHERE purchaseInvoice.id = :id AND purchaseInvoice.deleted = 1")
    Optional<PurchaseInvoice> findDeleted(int id);
}
