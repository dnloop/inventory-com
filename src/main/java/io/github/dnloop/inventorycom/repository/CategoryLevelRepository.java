package io.github.dnloop.inventorycom.repository;

import io.github.dnloop.inventorycom.model.CategoryLevel;
import org.springframework.data.jpa.repository.Modifying;
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
           " categoryLevel.l4 ASC")
    HashSet<CategoryLevel> findAll();

    @Query("SELECT categoryLevel FROM CategoryLevel categoryLevel" +
           " WHERE categoryLevel.id = :id" +
           " AND categoryLevel.deleted = 0")
    Optional<CategoryLevel> findById(int id);

    @Query("SELECT categoryLevel FROM CategoryLevel categoryLevel" +
           " WHERE categoryLevel.l1 = :levelOne" +
           " AND categoryLevel.l2 = :levelTwo" +
           " AND categoryLevel.l3 = :levelThree" +
           " AND categoryLevel.l4 = :levelFour" +
           " AND categoryLevel.deleted = 0")
    Optional<CategoryLevel> findByLevel(int levelOne, int levelTwo, int levelThree, int levelFour);

    @Query("SELECT categoryLevel FROM CategoryLevel categoryLevel" +
           " WHERE categoryLevel.deleted = 1 " +
           " ORDER BY categoryLevel.l1," +
           " categoryLevel.l2," +
           " categoryLevel.l3," +
           " categoryLevel.l4 ASC")
    HashSet<CategoryLevel> findAllDeleted();

    @Query("SELECT categoryLevel FROM CategoryLevel categoryLevel" +
           " WHERE categoryLevel.id = :id" +
           " AND categoryLevel.deleted = 1")
    Optional<CategoryLevel> findDeleted(int id);

    /**
     * This way the root node is left untouched and checks if it is possible to delete its
     * leaves.
     * If no value is returned it is assumed that the category level can be deleted.
     * It must be taken in consideration that the selected root node (L1 != 0) must
     * be equal to selected category levels L1 value since this method assumes root
     * node is safe for deletion.
     */
    @Query("SELECT COUNT(categoryLevel.id) FROM CategoryLevel categoryLevel" +
           " WHERE categoryLevel.l1 = :levelOne" +
           " AND EXISTS " +
           " (SELECT product.id FROM Product product" +
           " WHERE product.categoryId = categoryLevel.categoryId)")
    Integer childNodesExistsInProduct(int levelOne);

    /**
     * Once it is checked that no category is assigned to a product delete all
     * categories under the root node.
     */
    @Modifying
    @Query("UPDATE CategoryLevel categoryLevel" +
           " SET categoryLevel.deleted = 1," +
           " categoryLevel.l1 = 0," +
           " categoryLevel.l2 = 0," +
           " categoryLevel.l3 = 0," +
           " categoryLevel.l4 = 0" +
           " WHERE categoryLevel.l1 = :levelOne" +
           " AND categoryLevel.l2 > 0")
    void deleteRootNode(int levelOne);

    /**
     * Method to check if a child node is assigned to a product.
     */
    @Query("SELECT COUNT(categoryLevel.id) FROM CategoryLevel categoryLevel" +
           " WHERE categoryLevel.categoryId = :categoryId" +
           " AND EXISTS " +
           " (SELECT product.id FROM Product product" +
           " WHERE product.categoryId = categoryLevel.categoryId)")
    Integer categoryLevelExistsInProduct(int categoryId);

    /**
     * Method to delete a child node.
     */
    @Modifying
    @Query("UPDATE CategoryLevel categoryLevel" +
           " SET categoryLevel.deleted = 1," +
           " categoryLevel.l1 = 0," +
           " categoryLevel.l2 = 0," +
           " categoryLevel.l3 = 0," +
           " categoryLevel.l4 = 0" +
           " WHERE categoryLevel.categoryId = :categoryId" +
           " AND categoryLevel.l2 > 0")
    void deleteChildNode(int categoryId);
}
