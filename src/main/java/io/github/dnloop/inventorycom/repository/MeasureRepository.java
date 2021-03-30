package io.github.dnloop.inventorycom.repository;

import io.github.dnloop.inventorycom.model.Measure;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MeasureRepository extends JpaRepository<Measure, Integer> {

    @Query("SELECT measure FROM Measure measure" +
           " WHERE measure.deleted = 0")
    Page<Measure> findAll(Pageable pageable);

    @Query("SELECT measure from Measure measure" +
           " WHERE measure.deleted = 0")
    Page<Measure> findByProductDetailsById(int id);

    @Query("SELECT measure FROM Measure measure" +
           " WHERE measure.deleted = 1" +
           " ORDER BY measure.deletedAt")
    Page<Measure> findAllDeleted(Pageable pageable);

    @Query("SELECT measure FROM Measure measure" +
           " WHERE measure.id = :id AND measure.deleted = 1")
    Optional<Measure> findDeleted(int id);
}
