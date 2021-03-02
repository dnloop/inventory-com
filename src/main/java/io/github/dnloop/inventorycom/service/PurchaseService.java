package io.github.dnloop.inventorycom.service;

import io.github.dnloop.inventorycom.model.PurchaseInvoice;
import io.github.dnloop.inventorycom.repository.PurchaseInvoiceDetailsRepository;
import io.github.dnloop.inventorycom.repository.PurchaseInvoiceRepository;
import io.github.dnloop.inventorycom.repository.PurchaseShareRepository;
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
public class PurchaseService {
    private static final Log log = LogFactory.getLog(PurchaseService.class);

    private final PurchaseInvoiceRepository invoiceRepository;

    private final PurchaseInvoiceDetailsRepository invoiceDetailRepository;

    private final PurchaseShareRepository purchaseShareRepository;

    private final Pageable pageableFifty = PageRequest.of(0, 50);

    public PurchaseService(
            PurchaseInvoiceRepository invoiceRepository,
            PurchaseInvoiceDetailsRepository invoiceDetailRepository,
            PurchaseShareRepository purchaseShareRepository
    ) {
        this.invoiceRepository = invoiceRepository;
        this.invoiceDetailRepository = invoiceDetailRepository;
        this.purchaseShareRepository = purchaseShareRepository;
    }

    @Async
    public CompletableFuture<Optional<PurchaseInvoice>> findById(Integer id) {
        return CompletableFuture.completedFuture(invoiceRepository.findById(id));
    }

    @Async
    public CompletableFuture<Page<PurchaseInvoice>> findAll() {
        return CompletableFuture.completedFuture(invoiceRepository.findAll(pageableFifty));
    }

    @Async
    public CompletableFuture<Page<PurchaseInvoice>> findAll(Pageable pageable) {
        return CompletableFuture.completedFuture(invoiceRepository.findAll(pageable));
    }

    @Async
    public CompletableFuture<Page<PurchaseInvoice>> findAllDeleted() {
        return CompletableFuture.completedFuture(invoiceRepository.findAllDeleted(pageableFifty));
    }

    @Async
    public CompletableFuture<Page<PurchaseInvoice>> findAllDeleted(Pageable pageable) {
        return CompletableFuture.completedFuture(invoiceRepository.findAllDeleted(pageable));
    }

    @Async
    public void save(PurchaseInvoice invoice) {
        invoiceRepository.save(invoice);
    }

    @Async
    public void delete(PurchaseInvoice invoice) {
        invoiceRepository.delete(invoice);

        invoice.getPurchaseDetailsById().forEach(purchaseDetail -> invoiceDetailRepository
                .deleteById(purchaseDetail.getId())
        );

        invoice.getPurchaseSharesById().forEach(purchaseShare -> purchaseShareRepository
                .deleteById(purchaseShare.getId())
        );
    }

    @Async
    public void deleteAll(Collection<PurchaseInvoice> collectionPurchaseInvoice) {
        collectionPurchaseInvoice.forEach(this::delete);
    }
}