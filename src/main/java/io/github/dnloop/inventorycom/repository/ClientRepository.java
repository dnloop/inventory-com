package io.github.dnloop.inventorycom.repository;

import io.github.dnloop.inventorycom.model.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Integer> {
    Page<Client> findClientsByOrderBySurnameAsc(Pageable pageable);

    @Query("SELECT client FROM Client client WHERE  client.deleted = 1")
    Page<Client> findAllDeleted(Pageable pageable);

    @Query("SELECT client FROM Client client WHERE client.id = :id AND client.deleted = 1")
    Optional<Client> findDeleted(int id);
}
