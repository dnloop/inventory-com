package io.github.dnloop.inventorycom.service;

import io.github.dnloop.inventorycom.model.PurchaseDetail;
import io.github.dnloop.inventorycom.model.PurchaseInvoice;
import io.github.dnloop.inventorycom.repository.PurchaseInvoiceDetailsRepository;
import io.github.dnloop.inventorycom.repository.PurchaseInvoiceRepository;
import io.github.dnloop.inventorycom.repository.PurchaseShareRepository;
import io.github.dnloop.inventorycom.utils.PageableProperty;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class PurchaseService {
    private static final Log log = LogFactory.getLog(PurchaseService.class);

    private final PurchaseInvoiceRepository invoiceRepository;

    private final PurchaseInvoiceDetailsRepository detailRepository;

    private final PurchaseShareRepository shareRepository;

    public PurchaseService(
            PurchaseInvoiceRepository invoiceRepository,
            PurchaseInvoiceDetailsRepository detailRepository,
            PurchaseShareRepository shareRepository
    ) {
        this.invoiceRepository = invoiceRepository;
        this.detailRepository = detailRepository;
        this.shareRepository = shareRepository;
    }

    /* Invoice */

    @Async
    public CompletableFuture<Optional<PurchaseInvoice>> findInvoiceById(Integer id) {
        return CompletableFuture.completedFuture(invoiceRepository.findById(id));
    }

    @Async
    public CompletableFuture<Page<PurchaseInvoice>> findAllInvoices() {
        PageableProperty pageableProperty = new PageableProperty("number");
        return CompletableFuture.completedFuture(invoiceRepository.findAll(pageableProperty.getPageable()));
    }

    @Async
    public CompletableFuture<Page<PurchaseInvoice>> findAllInvoices(PageableProperty pageableProperty) {
        return CompletableFuture.completedFuture(invoiceRepository.findAll(pageableProperty.getPageable()));
    }

    @Async
    public CompletableFuture<PurchaseInvoice> saveInvoice(PurchaseInvoice invoice) {
        return CompletableFuture.completedFuture(invoiceRepository.save(invoice));
    }

    /* Detail */

    @Async
    public CompletableFuture<Optional<PurchaseDetail>> findDetailById(Integer id) {
        return CompletableFuture.completedFuture(detailRepository.findById(id));
    }

    @Async
    public CompletableFuture<ArrayList<PurchaseDetail>> findAllDetailsById(int invoiceId) {

        return CompletableFuture.completedFuture(
                detailRepository.findAllBySaleInvoiceId(invoiceId)
        );
    }

    @Async
    public CompletableFuture<PurchaseDetail> saveDetail(PurchaseDetail invoice) {
        return CompletableFuture.completedFuture(detailRepository.save(invoice));
    }

    /* Share */
}