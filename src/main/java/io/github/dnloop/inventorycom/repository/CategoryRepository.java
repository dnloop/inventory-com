package io.github.dnloop.inventorycom.repository;

import io.github.dnloop.inventorycom.model.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.HashSet;

public interface CategoryRepository extends CrudRepository<Category, Integer> {
    HashSet<Category> findAll();

    @Query("SELECT category FROM Category category WHERE category.deleted = 1")
    HashSet<Category> findAllDeleted();
}
