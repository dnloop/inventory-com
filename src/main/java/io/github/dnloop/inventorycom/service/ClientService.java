package io.github.dnloop.inventorycom.service;

import io.github.dnloop.inventorycom.model.Client;
import io.github.dnloop.inventorycom.model.ClientPhone;
import io.github.dnloop.inventorycom.repository.ClientPhoneRepository;
import io.github.dnloop.inventorycom.repository.ClientRepository;
import io.github.dnloop.inventorycom.support.uiloader.PageableProperty;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class ClientService {
    private static final Log log = LogFactory.getLog(ClientService.class);

    private final ClientRepository clientRepository;

    private final ClientPhoneRepository phoneRepository;

    private final PageableProperty pageableProperty = new PageableProperty();

    public ClientService(
            ClientRepository clientRepository,
            ClientPhoneRepository phoneRepository
    ) {
        this.clientRepository = clientRepository;
        this.phoneRepository = phoneRepository;
    }

    @Async
    public CompletableFuture<Optional<Client>> findClientById(int id) {
        return CompletableFuture.completedFuture(clientRepository.findById(id));
    }

    @Async
    public CompletableFuture<Optional<Client>> findDeletedClient(int id) {
        return CompletableFuture.completedFuture(clientRepository.findDeleted(id));
    }

    @Async
    public CompletableFuture<Page<Client>> findAllClients() {
        PageableProperty pageableProperty = new PageableProperty("surname");
        return CompletableFuture.completedFuture(clientRepository.findAll(pageableProperty.getPageable()));
    }

    @Async
    public CompletableFuture<Page<Client>> findAllClients(Pageable pageable) {
        return CompletableFuture.completedFuture(clientRepository.findAll(pageable));
    }

    @Async
    public CompletableFuture<Page<Client>> findAllDeletedClients() {
        PageableProperty pageableProperty = new PageableProperty("surname");
        return CompletableFuture.completedFuture(
                clientRepository.findAllDeleted(pageableProperty.getPageableDeleted())
        );
    }

    @Async
    public CompletableFuture<Page<Client>> findAllDeletedClients(PageableProperty pageableProperty) {
        return CompletableFuture.completedFuture(
                clientRepository.findAllDeleted(pageableProperty.getPageable())
        );
    }

    @Async
    public CompletableFuture<Client> saveClient(Client client) {
        return CompletableFuture.completedFuture(clientRepository.save(client));
    }

    @Transactional
    public void deleteClient(Client client) {
        clientRepository.delete(client);
        log.debug("Record Deleted: " + client.toString());
        log.debug("Deleting Relationships");
        client.getClientPhonesById().forEach(phoneNumber -> {
            phoneRepository.deleteById(phoneNumber.getId());
            log.debug("Record Deleted: " + phoneNumber.toString());
        });
    }

    @Transactional
    public void deleteAllClients(Collection<Client> collectionClient) {
        collectionClient.forEach(this::deleteClient);
        log.debug("Records Deleted: " + collectionClient.size());
    }

    /* Client Phones */

    @Async
    public CompletableFuture<Optional<ClientPhone>> findClientPhoneById(int id) {
        return CompletableFuture.completedFuture(phoneRepository.findById(id));
    }

    @Async
    public CompletableFuture<LinkedHashSet<ClientPhone>> findAllClientPhones() {
        return CompletableFuture.completedFuture(phoneRepository.findAll());
    }

    @Async
    public CompletableFuture<Optional<ClientPhone>> findDeletedClientPhoneById(int id) {
        return CompletableFuture.completedFuture(phoneRepository.findDeleted(id));
    }

    @Async
    public CompletableFuture<LinkedHashSet<ClientPhone>> findAllDeletedClientPhones() {
        return CompletableFuture.completedFuture(phoneRepository.findAllDeleted());
    }

    @Async
    public CompletableFuture<ClientPhone> saveClientPhone(ClientPhone clientPhone) {
        return CompletableFuture.completedFuture(phoneRepository.save(clientPhone));
    }

    @Transactional
    public void deleteClientPhone(ClientPhone clientPhone) {
        phoneRepository.delete(clientPhone);
        log.debug("Record Deleted: " + clientPhone.toString());
    }

    @Transactional
    public void deleteAllClientPhones(Collection<ClientPhone> collectionClientPhone) {
        collectionClientPhone.forEach(this::deleteClientPhone);
        log.debug("Records Deleted: " + collectionClientPhone.size());
    }
}
