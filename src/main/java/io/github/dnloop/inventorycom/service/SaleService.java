package io.github.dnloop.inventorycom.service;

import io.github.dnloop.inventorycom.model.SaleInvoice;
import io.github.dnloop.inventorycom.repository.SaleInvoiceDetailsRepository;
import io.github.dnloop.inventorycom.repository.SaleInvoiceRepository;
import io.github.dnloop.inventorycom.repository.SaleShareRepository;
import io.github.dnloop.inventorycom.utils.PageableProperty;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class SaleService {

    private static final Log log = LogFactory.getLog(SaleService.class);

    private final SaleInvoiceRepository invoiceRepository;

    private final SaleInvoiceDetailsRepository invoiceDetailRepository;

    private final SaleShareRepository shareRepository;

    private final PageableProperty pageableProperty = new PageableProperty();

    public SaleService(
            SaleInvoiceRepository invoiceRepository,
            SaleInvoiceDetailsRepository invoiceDetailRepository,
            SaleShareRepository shareRepository
    ) {
        this.invoiceRepository = invoiceRepository;
        this.invoiceDetailRepository = invoiceDetailRepository;
        this.shareRepository = shareRepository;
    }

    @Async
    public CompletableFuture<Optional<SaleInvoice>> findById(Integer id) {
        return CompletableFuture.completedFuture(invoiceRepository.findById(id));
    }

    @Async
    public CompletableFuture<Page<SaleInvoice>> findAll() {
        return CompletableFuture.completedFuture(
                invoiceRepository.findAll(pageableProperty.getPageable())
        );
    }

    @Async
    public CompletableFuture<Page<SaleInvoice>> findAll(Pageable pageable) {
        return CompletableFuture.completedFuture(invoiceRepository.findAll(pageable));
    }

    @Async
    public void save(SaleInvoice invoice) {
        invoiceRepository.save(invoice);
    }
}