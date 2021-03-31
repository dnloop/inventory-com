package io.github.dnloop.inventorycom.repository;

import io.github.dnloop.inventorycom.model.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SupplierRepository extends JpaRepository<Supplier, Integer> {

    @Query("SELECT supplier FROM Supplier supplier" +
           " WHERE supplier.deleted = 0")
    Page<Supplier> findAll(Pageable pageable);

    @Query("SELECT supplier FROM Supplier supplier" +
           " WHERE supplier.id = :id" +
           " AND supplier.deleted = 0")
    Optional<Supplier> findById(int id);

    @Query("SELECT supplier FROM Supplier supplier" +
           " WHERE supplier.deleted = 1")
    Page<Supplier> findAllDeleted(Pageable pageable);

    @Query("SELECT supplier FROM Supplier supplier" +
           " WHERE supplier.id = :id" +
           " AND supplier.deleted = 1")
    Optional<Supplier> findDeleted(int id);
}
