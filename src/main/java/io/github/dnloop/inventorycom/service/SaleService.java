package io.github.dnloop.inventorycom.service;

import io.github.dnloop.inventorycom.model.SaleInvoice;
import io.github.dnloop.inventorycom.repository.SaleInvoiceDetailsRepository;
import io.github.dnloop.inventorycom.repository.SaleInvoiceRepository;
import io.github.dnloop.inventorycom.repository.SaleShareRepository;
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
public class SaleService {

    private static final Log log = LogFactory.getLog(SaleService.class);

    private final SaleInvoiceRepository invoiceRepository;

    private final SaleInvoiceDetailsRepository invoiceDetailRepository;

    private final SaleShareRepository shareRepository;

    private final Pageable pageableFifty = PageRequest.of(0, 50);

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
        return CompletableFuture.completedFuture(invoiceRepository.findAll(pageableFifty));
    }

    @Async
    public CompletableFuture<Page<SaleInvoice>> findAll(Pageable pageable) {
        return CompletableFuture.completedFuture(invoiceRepository.findAll(pageable));
    }

    @Async
    public CompletableFuture<Page<SaleInvoice>> findAllDeleted() {
        return CompletableFuture.completedFuture(invoiceRepository.findAllDeleted(pageableFifty));
    }

    @Async
    public CompletableFuture<Page<SaleInvoice>> findAllDeleted(Pageable pageable) {
        return CompletableFuture.completedFuture(invoiceRepository.findAllDeleted(pageable));
    }

    @Async
    public void save(SaleInvoice invoice) {
        invoiceRepository.save(invoice);
    }

    @Async
    public void delete(SaleInvoice invoice) {
        invoiceRepository.delete(invoice);

        invoice.getSaleDetailsById().forEach(saleDetail -> invoiceDetailRepository
                .deleteById(saleDetail.getId())
        );

        invoice.getSaleSharesById().forEach(purchaseShare -> shareRepository
                .deleteById(purchaseShare.getId())
        );
    }

    @Async
    public void deleteAll(Collection<SaleInvoice> collectionSaleInvoice) {
        collectionSaleInvoice.forEach(this::delete);
    }
}