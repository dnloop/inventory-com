package io.github.dnloop.inventorycom.repository;

import io.github.dnloop.inventorycom.model.ClientPhone;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.LinkedHashSet;
import java.util.Optional;

public interface ClientPhoneRepository extends CrudRepository<ClientPhone, Integer> {

    @Query("SELECT clientPhone FROM ClientPhone clientPhone" +
           " WHERE clientPhone.deleted = 0")
    LinkedHashSet<ClientPhone> findAll();

    @Query("SELECT clientPhone FROM ClientPhone clientPhone" +
           " WHERE clientPhone.id = :id" +
           " AND clientPhone.deleted = 0")
    Optional<ClientPhone> findById(int id);

    @Query("SELECT clientPhone FROM ClientPhone clientPhone" +
           " WHERE clientPhone.deleted = 1")
    LinkedHashSet<ClientPhone> findAllDeleted();

    @Query("SELECT clientPhone FROM ClientPhone clientPhone" +
           " WHERE clientPhone.id = :id" +
           " AND clientPhone.deleted = 1")
    Optional<ClientPhone> findDeleted(int id);
}
