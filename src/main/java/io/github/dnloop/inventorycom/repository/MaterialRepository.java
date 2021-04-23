package io.github.dnloop.inventorycom.repository;

import io.github.dnloop.inventorycom.model.Material;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MaterialRepository extends JpaRepository<Material, Integer> {

    @Query("SELECT material FROM Material material" +
           " WHERE material.deleted = 0")
    Page<Material> findAll(Pageable pageable);

    @Query("SELECT material FROM Material material" +
           " WHERE material.id = :id" +
           " AND material.deleted = 0")
    Optional<Material> findById(int id);

    @Query("SELECT material FROM Material material" +
           " WHERE material.deleted = 1" +
           " ORDER BY material.deletedAt")
    Page<Material> findAllDeleted(Pageable pageable);

    @Query("SELECT material FROM Material material" +
           " WHERE material.id = :id" +
           " AND material.deleted = 1")
    Optional<Material> findDeleted(int id);

    /**
     * Returns the number of assigned materials to a Product Detail.
     */
    @Query("SELECT COUNT (productDetail.id)" +
           " FROM ProductDetail productDetail" +
           " WHERE productDetail.materialId = :materialId" +
           " AND productDetail.deleted = 0")
    Integer existsInProductDetail(int materialId);

    /**
     * Method to delete a single material.
     * <p>
     * By using this method we ensure Product Detail is not in an invalid state. If
     * a record is 'deleted' we assign a default 'unavailable' value.
     */
    @Modifying
    @Query("UPDATE Material material" +
           " SET material.deleted = 1" +
           " WHERE material.id = :materialId")
    void delete(int materialId);
}
