package io.github.dnloop.inventorycom.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProductDetailRepository extends JpaRepository<ProductDetailRepository, Integer> {

    @Query("SELECT productDetail FROM ProductDetail productDetail" +
           " WHERE productDetail.deleted = 0")
    Page<ProductDetailRepository> findAll(Pageable pageable);

    @Query("SELECT productDetail FROM ProductDetail productDetail" +
           " WHERE productDetail.deleted = 1" +
           " ORDER BY productDetail.deletedAt")
    Page<ProductDetailRepository> findAllDeleted(Pageable pageable);

    @Query("SELECT productDetail FROM ProductDetail productDetail" +
           " WHERE productDetail.id = :id AND productDetail.deleted = 1")
    Optional<ProductDetailRepository> findDeleted(int id);
}
