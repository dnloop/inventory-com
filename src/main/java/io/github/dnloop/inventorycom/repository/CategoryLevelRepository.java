/*
 *     Inventory-Com: Inventory and Commerce Management Application.
 *     Copyright (C) 2021. dnloop.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 */

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
           " categoryLevel.l4")
    HashSet<CategoryLevel> findAll();

    @Query("SELECT categoryLevel FROM CategoryLevel categoryLevel" +
           " WHERE categoryLevel.id = :id" +
           " AND categoryLevel.deleted = 0")
    Optional<CategoryLevel> findById(int id);

    @Query("SELECT categoryLevel FROM CategoryLevel categoryLevel" +
           " WHERE categoryLevel.categoryId = :categoryId" +
           " AND categoryLevel.deleted = 0")
    Optional<CategoryLevel> findByCategoryId(int categoryId);

    @Query("SELECT categoryLevel FROM CategoryLevel categoryLevel" +
           " WHERE categoryLevel.deleted = 1 " +
           " ORDER BY categoryLevel.l1," +
           " categoryLevel.l2," +
           " categoryLevel.l3," +
           " categoryLevel.l4")
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
     * Method to delete a single node.
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
    void deleteNode(int categoryId);
}
