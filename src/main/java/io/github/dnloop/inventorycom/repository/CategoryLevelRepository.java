package io.github.dnloop.inventorycom.repository;

import io.github.dnloop.inventorycom.model.CategoryLevel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.HashSet;
import java.util.Optional;

public interface CategoryLevelRepository extends CrudRepository<CategoryLevel, Integer> {

    @Query("SELECT categoryLevel FROM CategoryLevel categoryLevel" +
           " WHERE categoryLevel.deleted = 0" +
           " ORDER BY categoryLevel.l1," +
           " categoryLevel.l2," +
           " categoryLevel.l3," +
           " categoryLevel.l4 DESC")
    HashSet<CategoryLevel> findAll();

    @Query("SELECT categoryLevel FROM CategoryLevel categoryLevel" +
           " WHERE categoryLevel.id = :id" +
           " AND categoryLevel.deleted = 0")
    Optional<CategoryLevel> findById(int id);

    @Query("SELECT categoryLevel FROM CategoryLevel categoryLevel" +
           " WHERE categoryLevel.deleted = 1 " +
           " ORDER BY categoryLevel.l1," +
           " categoryLevel.l2," +
           " categoryLevel.l3," +
           " categoryLevel.l4 DESC")
    HashSet<CategoryLevel> findAllDeleted();

    @Query("SELECT categoryLevel FROM CategoryLevel categoryLevel" +
           " WHERE categoryLevel.id = :id" +
           " AND categoryLevel.deleted = 1")
    Optional<CategoryLevel> findDeleted(int id);
}
