package io.github.dnloop.inventorycom.repository;

import io.github.dnloop.inventorycom.model.Measure;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MeasureRepository extends JpaRepository<Measure, Integer> {

    @Query("SELECT measure FROM Measure measure" +
           " WHERE measure.deleted = 0")
    Page<Measure> findAll(Pageable pageable);

    @Query("SELECT measure FROM Measure measure" +
           " WHERE measure.id = :id" +
           " AND measure.deleted = 0")
    Optional<Measure> findById(int id);

    @Query("SELECT measure FROM Measure measure" +
           " WHERE measure.deleted = 1" +
           " ORDER BY measure.deletedAt")
    Page<Measure> findAllDeleted(Pageable pageable);

    @Query("SELECT measure FROM Measure measure" +
           " WHERE measure.id = :id" +
           " AND measure.deleted = 1")
    Optional<Measure> findDeleted(int id);

    /**
     * Returns the number of assigned measures to a Product Detail.
     */
    @Query("SELECT COUNT (productDetail.id)" +
           " FROM ProductDetail productDetail" +
           " WHERE productDetail.measureId = :measureId" +
           " AND productDetail.deleted = 0")
    Integer existsInProductDetail(int measureId);

    /**
     * Method to delete a single measure.
     * <p>
     * By using this method we ensure Product Detail is not in an invalid state. If
     * a record is 'deleted' we assign a default 'unavailable' (00) value.
     */
    @Modifying
    @Query("UPDATE Measure measure" +
           " SET measure.deleted = 1" +
           " WHERE measure.id = :measureId")
    void delete(int measureId);
}
