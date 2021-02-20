package io.github.dnloop.inventorycom.repository;

import io.github.dnloop.inventorycom.model.ClientPhone;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.HashSet;
import java.util.LinkedHashSet;

public interface ClientPhoneRepository extends CrudRepository<ClientPhone, Integer> {
    LinkedHashSet<ClientPhone> findAll();

    @Query("SELECT clientPhone FROM ClientPhone clientPhone WHERE clientPhone.deleted = 1")
    LinkedHashSet<ClientPhone> findAllDeleted();
}
