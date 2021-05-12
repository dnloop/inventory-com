package io.github.dnloop.inventorycom.service;

import io.github.dnloop.inventorycom.model.SaleShare;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class SaleService {

    private static final Log log = LogFactory.getLog(SaleService.class);

    private final SaleInvoiceRepository invoiceRepository;

    private final SaleInvoiceDetailsRepository detailsRepository;

    private final SaleShareRepository shareRepository;

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

    /* Share */

    @Async
    public CompletableFuture<Optional<SaleShare>> findSaleShareById(int id) {
        return CompletableFuture.completedFuture(shareRepository.findById(id));
    }

    @Async
    public CompletableFuture<Optional<SaleShare>> findDeletedSaleShare(int id) {
        return CompletableFuture.completedFuture(shareRepository.findDeleted(id));
    }

    @Async
    public CompletableFuture<LinkedHashSet<SaleShare>> findAllSaleShares() {
        return CompletableFuture.completedFuture(shareRepository.findAll());
    }

    @Async
    public CompletableFuture<LinkedHashSet<SaleShare>> findAllDeletedSaleShares() {
        return CompletableFuture.completedFuture(shareRepository.findAllDeleted());
    }

    @Async
    public CompletableFuture<SaleShare> saveSaleShare(SaleShare saleShare) {
        return CompletableFuture.completedFuture(shareRepository.save(saleShare));
    }

    @Transactional
    public void deleteSaleShare(SaleShare saleShare) {
        shareRepository.delete(saleShare);
        log.debug("SaleShare Deleted: " + saleShare.toString());
    }
}