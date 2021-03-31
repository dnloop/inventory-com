package io.github.dnloop.inventorycom.repository;

import io.github.dnloop.inventorycom.model.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.HashSet;
import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, Integer> {

    @Query("SELECT category FROM Category category" +
           " WHERE category.deleted = 0")
    HashSet<Category> findAll();

    @Query("SELECT category FROM Category category" +
           " WHERE category.id = :id" +
           " AND category.deleted = 0")
    Optional<Category> findById(int id);

    @Query("SELECT category FROM Category category" +
           " WHERE category.deleted = 1")
    HashSet<Category> findAllDeleted();

    @Query("SELECT category FROM Category category" +
           " WHERE category.id = :id" +
           " AND category.deleted = 1")
    Optional<Category> findDeleted(int id);
}
