package io.github.dnloop.inventorycom.service;

import io.github.dnloop.inventorycom.model.SaleDetail;
import io.github.dnloop.inventorycom.model.SaleInvoice;
import io.github.dnloop.inventorycom.repository.SaleInvoiceDetailsRepository;
import io.github.dnloop.inventorycom.repository.SaleInvoiceRepository;
import io.github.dnloop.inventorycom.repository.SaleShareRepository;
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
public class SaleService {

    private static final Log log = LogFactory.getLog(SaleService.class);

    private final SaleInvoiceRepository invoiceRepository;

    private final SaleInvoiceDetailsRepository detailsRepository;

    private final SaleShareRepository shareRepository;

    private final PageableProperty pageableProperty = new PageableProperty();

    public SaleService(
            SaleInvoiceRepository invoiceRepository,
            SaleInvoiceDetailsRepository detailsRepository,
            SaleShareRepository shareRepository
    ) {
        this.invoiceRepository = invoiceRepository;
        this.detailsRepository = detailsRepository;
        this.shareRepository = shareRepository;
    }

    /* Sale Invoice */

    @Async
    public CompletableFuture<Optional<SaleInvoice>> findInvoiceById(Integer id) {
        return CompletableFuture.completedFuture(invoiceRepository.findById(id));
    }

    @Async
    public CompletableFuture<Page<SaleInvoice>> findAllInvoices() {
        PageableProperty pageableProperty = new PageableProperty("number");
        return CompletableFuture.completedFuture(
                invoiceRepository.findAll(pageableProperty.getPageable())
        );
    }

    @Async
    public CompletableFuture<Page<SaleInvoice>> findAllInvoices(PageableProperty pageableProperty) {
        return CompletableFuture.completedFuture(invoiceRepository.findAll(pageableProperty.getPageable()));
    }

    @Async
    public CompletableFuture<SaleInvoice> saveInvoice(SaleInvoice invoice) {
        return CompletableFuture.completedFuture(invoiceRepository.save(invoice));
    }

    /* Sale Detail */

    @Async
    public CompletableFuture<Optional<SaleDetail>> findDetailById(Integer id) {
        return CompletableFuture.completedFuture(detailsRepository.findById(id));
    }

    @Async
    public CompletableFuture<ArrayList<SaleDetail>> findAllDetailsById(int invoiceId) {

        return CompletableFuture.completedFuture(
                detailsRepository.findAllBySaleInvoiceId(invoiceId)
        );
    }

    @Async
    public CompletableFuture<SaleDetail> saveDetail(SaleDetail invoice) {
        return CompletableFuture.completedFuture(detailsRepository.save(invoice));
    }
}