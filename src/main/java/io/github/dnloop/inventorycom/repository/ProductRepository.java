package io.github.dnloop.inventorycom.repository;

import io.github.dnloop.inventorycom.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query("SELECT product FROM Product product" +
           " WHERE product.deleted = 0")
    Page<Product> findAll(Pageable pageable);

    @Query("SELECT product FROM Product product" +
           " WHERE product.id = :id" +
           " AND product.deleted = 0")
    Optional<Product> findById(int id);

    @Query("SELECT product FROM Product product" +
           " WHERE product.deleted = 1")
    Page<Product> findAllDeleted(Pageable pageable);

    @Query("SELECT product FROM Product product" +
           " WHERE product.id = :id" +
           " AND product.deleted = 1")
    Optional<Product> findDeleted(int id);
}
