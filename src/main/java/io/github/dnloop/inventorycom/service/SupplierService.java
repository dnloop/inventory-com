package io.github.dnloop.inventorycom.service;

import io.github.dnloop.inventorycom.model.Supplier;
import io.github.dnloop.inventorycom.repository.SupplierPhoneRepository;
import io.github.dnloop.inventorycom.repository.SupplierRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class SupplierService {
    private static final Log log = LogFactory.getLog(ClientService.class);

    private final SupplierRepository supplierRepository;

    private final SupplierPhoneRepository phoneRepository;

    private final Pageable pageableFifty = PageRequest.of(0, 50);

    public SupplierService(
            SupplierRepository supplierRepository,
            SupplierPhoneRepository phoneRepository
    ) {
        this.supplierRepository = supplierRepository;
        this.phoneRepository = phoneRepository;
    }

    @Async
    public CompletableFuture<Optional<Supplier>> findById(Integer id) {
        return CompletableFuture.completedFuture(supplierRepository.findById(id));
    }

    @Async
    public CompletableFuture<Page<Supplier>> findAll() {
        return CompletableFuture.completedFuture(supplierRepository.findAll(pageableFifty));
    }

    @Async
    public CompletableFuture<Page<Supplier>> findAll(Pageable pageable) {
        return CompletableFuture.completedFuture(supplierRepository.findAll(pageable));
    }

    @Async
    public CompletableFuture<Page<Supplier>> findAllDeleted() {
        return CompletableFuture.completedFuture(supplierRepository.findAllDeleted(pageableFifty));
    }

    @Async
    public CompletableFuture<Page<Supplier>> findAllDeleted(Pageable pageable) {
        return CompletableFuture.completedFuture(supplierRepository.findAllDeleted(pageable));
    }

    @Async
    public void save(Supplier supplier) {
        supplierRepository.save(supplier);
    }

    @Async
    public void delete(Supplier supplier) {
        supplierRepository.delete(supplier);

        supplier.getSupplierPhonesById().forEach(phoneNumber -> phoneRepository
                .deleteById(phoneNumber.getId())
        );
    }

    @Async
    public void deleteAll(Collection<Supplier> collectionClient) {
        collectionClient.forEach(this::delete);
    }
}
