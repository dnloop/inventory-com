package io.github.dnloop.inventorycom.repository;

import io.github.dnloop.inventorycom.model.SaleDetail;
import io.github.dnloop.inventorycom.model.SaleInvoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SaleInvoiceDetailsRepository extends JpaRepository<SaleDetail, Integer> {

    @Query("SELECT saleDetail FROM SaleDetail saleDetail" +
           " WHERE saleDetail.deleted = 0")
    Page<SaleDetail> findAll(Pageable pageable);

    @Query("SELECT saleDetail FROM SaleDetail saleDetail" +
           " WHERE saleDetail.id = :id" +
           " AND saleDetail.deleted = 0")
    Optional<SaleDetail> findById(int id);

    @Query("SELECT saleDetail FROM SaleDetail saleDetail" +
           " WHERE saleDetail.saleInvoiceId = :id" +
           " AND saleDetail.deleted = 0")
    Page<SaleDetail> findAllBySaleInvoiceId(
            int id, Pageable pageable
    );

    @Query("SELECT saleDetail FROM SaleDetail saleDetail" +
           " WHERE saleDetail.deleted = 1")
    Page<SaleDetail> findAllDeleted(Pageable pageable);

    @Query("SELECT saleDetail FROM SaleDetail saleDetail" +
           " WHERE saleDetail.id = :id" +
           " AND saleDetail.deleted = 1")
    Optional<SaleDetail> findDeleted(int id);
}
