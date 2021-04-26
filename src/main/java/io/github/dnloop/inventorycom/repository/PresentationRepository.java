package io.github.dnloop.inventorycom.repository;

import io.github.dnloop.inventorycom.model.Presentation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PresentationRepository extends JpaRepository<Presentation, Integer> {

    @Query("SELECT presentation FROM Presentation presentation" +
           " WHERE presentation.deleted = 0")
    Page<Presentation> findAll(Pageable pageable);

    @Query("SELECT presentation FROM Presentation presentation" +
           " WHERE presentation.id = :id" +
           " AND presentation.deleted = 0")
    Optional<Presentation> findById(int id);

    @Query("SELECT presentation FROM Presentation presentation" +
           " WHERE presentation.deleted = 1" +
           " ORDER BY presentation.deletedAt")
    Page<Presentation> findAllDeleted(Pageable pageable);

    @Query("SELECT presentation FROM Presentation presentation" +
           " WHERE presentation.id = :id" +
           " AND presentation.deleted = 1")
    Optional<Presentation> findDeleted(int id);

    /**
     * Returns the number of assigned measures to a Product Detail.
     */
    @Query("SELECT COUNT (productDetail.id)" +
           " FROM ProductDetail productDetail" +
           " WHERE productDetail.presentationId = :presentationId" +
           " AND productDetail.deleted = 0")
    Integer existsInProductDetail(int presentationId);

    /**
     * Method to delete a single presentation.
     * <p>
     * By using this method we ensure Product Detail is not in an invalid state. If
     * a record is 'deleted' we assign a default 'none' value.
     */
    @Modifying
    @Query("UPDATE Presentation presentation" +
           " SET presentation.deleted = 1" +
           " WHERE presentation.id = :presentationId")
    void delete(int presentationId);
}
