package io.github.dnloop.inventorycom.service;

import io.github.dnloop.inventorycom.model.Supplier;
import io.github.dnloop.inventorycom.model.SupplierPhone;
import io.github.dnloop.inventorycom.repository.SupplierPhoneRepository;
import io.github.dnloop.inventorycom.repository.SupplierRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.LinkedHashSet;
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

    /* Supplier */

    @Async
    public CompletableFuture<Optional<Supplier>> findSupplierById(Integer id) {
        return CompletableFuture.completedFuture(supplierRepository.findById(id));
    }

    @Async
    public CompletableFuture<Page<Supplier>> findAllSuppliers() {
        return CompletableFuture.completedFuture(supplierRepository.findAll(pageableFifty));
    }

    @Async
    public CompletableFuture<Page<Supplier>> findAllSuppliers(Pageable pageable) {
        return CompletableFuture.completedFuture(supplierRepository.findAll(pageable));
    }

    @Async
    public CompletableFuture<Page<Supplier>> findAllDeletedSuppliers() {
        return CompletableFuture.completedFuture(supplierRepository.findAllDeleted(pageableFifty));
    }

    @Async
    public CompletableFuture<Page<Supplier>> findAllDeletedSuppliers(Pageable pageable) {
        return CompletableFuture.completedFuture(supplierRepository.findAllDeleted(pageable));
    }

    @Async
    public void saveSupplier(Supplier supplier) {
        supplierRepository.save(supplier);
    }

    @Transactional
    public void deleteSupplier(Supplier supplier) {
        supplierRepository.delete(supplier);
        log.debug("Record Deleted: " + supplier.toString());
        log.debug("Deleting Relationships");
        supplier.getSupplierPhonesById().forEach(phoneNumber -> {
            phoneRepository.deleteById(phoneNumber.getId());
            log.debug("Record Deleted: " + phoneNumber.toString());
        });
    }

    @Async
    public void deleteAllSupplier(Collection<Supplier> collectionSupplier) {
        collectionSupplier.forEach(this::deleteSupplier);
        log.debug("Records Deleted: " + collectionSupplier.size());
    }

    /* Supplier Phone */

    @Async
    public CompletableFuture<Optional<SupplierPhone>> findSupplierPhoneById(Integer id) {
        return CompletableFuture.completedFuture(phoneRepository.findById(id));
    }

    @Async
    public CompletableFuture<LinkedHashSet<SupplierPhone>> findAllSupplierPhones() {
        return CompletableFuture.completedFuture(phoneRepository.findAll());
    }

    @Async
    public CompletableFuture<LinkedHashSet<SupplierPhone>> findAllDeletedSupplierPhones() {
        return CompletableFuture.completedFuture(phoneRepository.findAllDeleted());
    }

    @Async
    public void saveSupplierPhone(SupplierPhone supplierPhone) {
        phoneRepository.save(supplierPhone);
    }

    @Async
    public void deleteSupplierPhone(SupplierPhone supplierPhone) {
        phoneRepository.delete(supplierPhone);
        log.debug("Record Deleted: " + supplierPhone.toString());
    }

    @Async
    public void deleteAllSupplierPhones(Collection<SupplierPhone> collectionSupplierPhone) {
        collectionSupplierPhone.forEach(this::deleteSupplierPhone);
        log.debug("Records Deleted: " + collectionSupplierPhone.size());
    }
}
