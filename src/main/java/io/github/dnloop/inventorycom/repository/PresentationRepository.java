package io.github.dnloop.inventorycom.repository;

import io.github.dnloop.inventorycom.model.Presentation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PresentationRepository extends JpaRepository<Presentation, Integer> {

    @Query("SELECT presentation FROM Presentation presentation" +
           " WHERE presentation.deleted = 0")
    Page<Presentation> findAll(Pageable pageable);

    @Query("SELECT presentation from Presentation presentation" +
           " WHERE presentation.deleted = 0")
    Optional<Presentation> findByProductDetailsById(int id);

    @Query("SELECT presentation FROM Presentation presentation" +
           " WHERE presentation.deleted = 1" +
           " ORDER BY presentation.deletedAt")
    Page<Presentation> findAllDeleted(Pageable pageable);

    @Query("SELECT presentation FROM Presentation presentation" +
           " WHERE presentation.id = :id AND presentation.deleted = 1")
    Optional<Presentation> findDeleted(int id);
}
