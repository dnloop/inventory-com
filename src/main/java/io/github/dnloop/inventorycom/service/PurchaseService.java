package io.github.dnloop.inventorycom.service;

import io.github.dnloop.inventorycom.model.PurchaseInvoice;
import io.github.dnloop.inventorycom.repository.PurchaseInvoiceDetailsRepository;
import io.github.dnloop.inventorycom.repository.PurchaseInvoiceRepository;
import io.github.dnloop.inventorycom.repository.PurchaseShareRepository;
import io.github.dnloop.inventorycom.utils.PageableProperty;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class PurchaseService {
    private static final Log log = LogFactory.getLog(PurchaseService.class);

    private final PurchaseInvoiceRepository invoiceRepository;

    private final PurchaseInvoiceDetailsRepository invoiceDetailRepository;

    private final PurchaseShareRepository purchaseShareRepository;

    private final PageableProperty pageableProperty = new PageableProperty();

    public PurchaseService(
            PurchaseInvoiceRepository invoiceRepository,
            PurchaseInvoiceDetailsRepository invoiceDetailRepository,
            PurchaseShareRepository purchaseShareRepository
    ) {
        this.invoiceRepository = invoiceRepository;
        this.invoiceDetailRepository = invoiceDetailRepository;
        this.purchaseShareRepository = purchaseShareRepository;
    }

    /* Invoice */

    @Async
    public CompletableFuture<Optional<PurchaseInvoice>> findInvoiceById(Integer id) {
        return CompletableFuture.completedFuture(invoiceRepository.findById(id));
    }

    @Async
    public CompletableFuture<Page<PurchaseInvoice>> findAllInvoices() {
        return CompletableFuture.completedFuture(invoiceRepository.findAll(pageableProperty.getPageable()));
    }

    @Async
    public CompletableFuture<Page<PurchaseInvoice>> findAllInvoices(PageableProperty pageableProperty) {
        return CompletableFuture.completedFuture(invoiceRepository.findAll(pageableProperty.getPageable()));
    }

    @Async
    public void saveInvoice(PurchaseInvoice invoice) {
        invoiceRepository.save(invoice);
    }

    /* Purchase Share */
}