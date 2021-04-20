package io.github.dnloop.inventorycom.repository;

import io.github.dnloop.inventorycom.model.Category;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.HashSet;
import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, Integer> {

    @Query("SELECT category FROM Category category" +
           " WHERE category.deleted = 0" +
           " ORDER BY category.description ASC")
    HashSet<Category> findAll();

    @Query("SELECT category FROM Category category" +
           " WHERE category.id = :id" +
           " AND category.deleted = 0")
    Optional<Category> findById(int id);

    @Query("SELECT category FROM Category category" +
           " WHERE category.deleted = 1 " +
           " ORDER BY category.description ASC")
    HashSet<Category> findAllDeleted();

    @Query("SELECT category FROM Category category" +
           " WHERE category.id = :id" +
           " AND category.deleted = 1")
    Optional<Category> findDeleted(int id);

    /**
     * Returns the number of assigned categories to a product.
     * <p>
     * This method is the same as {@link CategoryLevelRepository#categoryExistsInProduct(int)}
     */
    @Query("SELECT COUNT (product.id)" +
           " FROM Product product" +
           " WHERE product.categoryId = :categoryId" +
           " AND product.deleted = 0")
    Integer existsInProduct(int categoryId);

    /**
     * Returns the number of assigned categories to a category level.
     * <p>
     * This method shares similarities with others. This behaviour should be abstracted in
     * a general function to prevent duplication. So far it works as is.
     *
     * @see CategoryLevelRepository#categoryExistsInProduct(int)
     * @see #existsInProduct(int)
     * @see #existsInCategoryLevel(int)
     */
    @Query("SELECT COUNT (categoryLevel.id)" +
           " FROM CategoryLevel categoryLevel" +
           " WHERE categoryLevel.categoryId = :categoryId" +
           " AND categoryLevel.deleted = 0")
    Integer existsInCategoryLevel(int categoryId);

    /**
     * Method to delete a single category.
     */
    @Modifying
    @Query("UPDATE Category category" +
           " SET category.deleted = 1" +
           " WHERE category.id = :categoryId")
    void delete(int categoryId);
}
