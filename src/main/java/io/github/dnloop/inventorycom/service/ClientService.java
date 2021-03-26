package io.github.dnloop.inventorycom.service;

import io.github.dnloop.inventorycom.model.Client;
import io.github.dnloop.inventorycom.repository.ClientPhoneRepository;
import io.github.dnloop.inventorycom.repository.ClientRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class ClientService {
    private static final Log log = LogFactory.getLog(ClientService.class);

    private final ClientRepository clientRepository;

    private final ClientPhoneRepository phoneRepository;

    private final Pageable pageableFifty = PageRequest.of(0, 50, Sort.by("surname").ascending());

    private final Pageable pageableFiftyDeleted = PageRequest.of(
            0, 50,
            Sort.by("surname").ascending()
                .and(Sort.by("deletedAt").ascending())
    );

    public ClientService(
            ClientRepository clientRepository,
            ClientPhoneRepository phoneRepository
    ) {
        this.clientRepository = clientRepository;
        this.phoneRepository = phoneRepository;
    }

    @Async
    public CompletableFuture<Optional<Client>> findById(int id) {
        return CompletableFuture.completedFuture(clientRepository.findById(id));
    }

    @Async
    public CompletableFuture<Optional<Client>> findDeleted(Integer id) {
        return CompletableFuture.completedFuture(clientRepository.findDeleted(id));
    }

    @Async
    public CompletableFuture<Page<Client>> findAll() {
        return CompletableFuture.completedFuture(clientRepository.findAll(pageableFifty));
    }

    @Async
    public CompletableFuture<Page<Client>> findAll(Pageable pageable) {
        return CompletableFuture.completedFuture(clientRepository.findAll(pageable));
    }

    @Async
    public CompletableFuture<Page<Client>> findAllDeleted() {
        return CompletableFuture.completedFuture(clientRepository.findAllDeleted(pageableFiftyDeleted));
    }

    @Async
    public CompletableFuture<Page<Client>> findAllDeleted(Pageable pageable) {
        return CompletableFuture.completedFuture(clientRepository.findAllDeleted(pageable));
    }

    @Async
    public CompletableFuture<Client> save(Client client) {
        return CompletableFuture.completedFuture(clientRepository.save(client));
    }

    @Transactional
    public void delete(Client client) {
        clientRepository.delete(client);
        log.debug("Record Deleted: " + client.toString());
        log.debug("Deleting Relationships");
        client.getClientPhonesById().forEach(phoneNumber -> {
            phoneRepository.deleteById(phoneNumber.getId());
            log.debug("Record Deleted: " + phoneNumber.toString());
        });
    }

    @Async
    public void deleteAll(Collection<Client> collectionClient) {
        collectionClient.forEach(this::delete);
        log.debug("Records Deleted: " + collectionClient.size());
    }
}
