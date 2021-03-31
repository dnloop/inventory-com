package io.github.dnloop.inventorycom.repository;

import io.github.dnloop.inventorycom.model.PurchaseShare;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.LinkedHashSet;
import java.util.Optional;

public interface PurchaseShareRepository extends CrudRepository<PurchaseShare, Integer> {

    @Query("SELECT purchaseShare FROM PurchaseShare purchaseShare" +
           " WHERE purchaseShare.deleted = 0")
    LinkedHashSet<PurchaseShare> findAll();

    @Query("SELECT purchaseShare FROM PurchaseShare purchaseShare" +
           " WHERE purchaseShare.id = :id AND purchaseShare.deleted = 0")
    Optional<PurchaseShare> findById(int id);

    @Query("SELECT purchaseShare FROM PurchaseShare purchaseShare" +
           " WHERE purchaseShare.deleted = 1")
    LinkedHashSet<PurchaseShare> findAllDeleted();

    @Query("SELECT purchaseShare FROM PurchaseShare purchaseShare" +
           " WHERE purchaseShare.id = :id AND purchaseShare.deleted = 1")
    Optional<PurchaseShare> findDeleted(int id);
}
