package io.github.dnloop.inventorycom.repository;

import io.github.dnloop.inventorycom.model.SaleShare;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.LinkedHashSet;
import java.util.Optional;

public interface SaleShareRepository extends CrudRepository<SaleShare, Integer> {

    @Query("SELECT saleShare FROM SaleShare saleShare" +
           " WHERE saleShare.deleted = 0" +
           " ORDER BY saleShare.dueDate")
    LinkedHashSet<SaleShare> findAll();

    @Query("SELECT saleShare FROM SaleShare saleShare" +
           " WHERE saleShare.id = :id" +
           " AND saleShare.deleted = 0")
    Optional<SaleShare> findById(int id);

    @Query("SELECT saleShare FROM SaleShare saleShare" +
           " WHERE saleShare.deleted = 1" +
           " ORDER BY saleShare.dueDate")
    LinkedHashSet<SaleShare> findAllDeleted();

    @Query("SELECT saleShare FROM SaleShare saleShare" +
           " WHERE saleShare.id = :id" +
           " AND saleShare.deleted = 1")
    Optional<SaleShare> findDeleted(int id);

    @Query("SELECT saleShare FROM SaleShare saleShare" +
           " WHERE saleShare.deleted = 0" +
           " AND saleShare.saleInvoiceId = :invoiceId" +
           " ORDER BY saleShare.dueDate")
    LinkedHashSet<SaleShare> findAllByInvoiceId(int invoiceId);
}
