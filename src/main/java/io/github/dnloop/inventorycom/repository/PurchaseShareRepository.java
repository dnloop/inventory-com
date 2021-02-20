package io.github.dnloop.inventorycom.repository;

import io.github.dnloop.inventorycom.model.PurchaseShare;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.LinkedHashSet;

public interface PurchaseShareRepository extends CrudRepository<PurchaseShare, Integer> {
    LinkedHashSet<PurchaseShare> findAll();

    @Query("SELECT purchaseShare FROM PurchaseShare purchaseShare WHERE purchaseShare.deleted = 1")
    LinkedHashSet<PurchaseShare> findAllDeleted();
}
