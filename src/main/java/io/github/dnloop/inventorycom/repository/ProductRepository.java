package io.github.dnloop.inventorycom.repository;

import io.github.dnloop.inventorycom.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Page<Product> findAllByOrderByDescriptionAsc(Pageable pageable);

    @Query("SELECT product FROM Product product WHERE product.deleted = 1")
    Page<Product> findAllDeleted(Pageable pageable);
}
