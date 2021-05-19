package io.github.dnloop.inventorycom.service;

import io.github.dnloop.inventorycom.model.PurchaseDetail;
import io.github.dnloop.inventorycom.model.PurchaseInvoice;
import io.github.dnloop.inventorycom.model.PurchaseShare;
import io.github.dnloop.inventorycom.model.PurchaseShareBuilder;
import io.github.dnloop.inventorycom.repository.PurchaseInvoiceDetailsRepository;
import io.github.dnloop.inventorycom.repository.PurchaseInvoiceRepository;
import io.github.dnloop.inventorycom.repository.PurchaseShareRepository;
import io.github.dnloop.inventorycom.utils.BusinessDate;
import io.github.dnloop.inventorycom.utils.PageableProperty;
import net.objectlab.kit.datecalc.common.DateCalculator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class PurchaseService {
    private static final Log log = LogFactory.getLog(PurchaseService.class);

    private final PurchaseInvoiceRepository invoiceRepository;

    private final PurchaseInvoiceDetailsRepository detailRepository;

    private final PurchaseShareRepository shareRepository;

    private final DateCalculator<LocalDate> calculator;


    public PurchaseService(
            PurchaseInvoiceRepository invoiceRepository,
            PurchaseInvoiceDetailsRepository detailRepository,
            PurchaseShareRepository shareRepository,
            BusinessDate businessDate
    ) {
        this.invoiceRepository = invoiceRepository;
        this.detailRepository = detailRepository;
        this.shareRepository = shareRepository;
        calculator = businessDate.getDateCalculator();
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

    @Async
    public CompletableFuture<Optional<PurchaseShare>> findPurchaseShareById(int id) {
        return CompletableFuture.completedFuture(shareRepository.findById(id));
    }

    @Async
    public CompletableFuture<Optional<PurchaseShare>> findDeletedPurchaseShare(int id) {
        return CompletableFuture.completedFuture(shareRepository.findDeleted(id));
    }

    @Async
    public CompletableFuture<LinkedHashSet<PurchaseShare>> findAllPurchaseShares() {
        return CompletableFuture.completedFuture(shareRepository.findAll());
    }

    @Async
    public CompletableFuture<LinkedHashSet<PurchaseShare>> findAllPurchaseSharesByInvoice(int invoiceId) {
        return CompletableFuture.completedFuture(shareRepository.findAllByInvoiceId(invoiceId));
    }

    @Async
    public CompletableFuture<LinkedHashSet<PurchaseShare>> findAllDeletedPurchaseShares() {
        return CompletableFuture.completedFuture(shareRepository.findAllDeleted());
    }

    @Async
    public CompletableFuture<PurchaseShare> savePurchaseShare(PurchaseShare purchaseShare) {
        return CompletableFuture.completedFuture(shareRepository.save(purchaseShare));
    }

    @Async
    public CompletableFuture<Iterable<PurchaseShare>> saveAllPurchaseShares(List<PurchaseShare> purchaseShares) {
        return CompletableFuture.completedFuture(shareRepository.saveAll(purchaseShares));
    }

    @Transactional
    public void deletePurchaseShare(PurchaseShare purchaseShare) {
        shareRepository.delete(purchaseShare);
        log.debug("PurchaseShare Deleted: " + purchaseShare.toString());
    }

    public Date createDueDate() {
        calculator.setStartDate(LocalDate.now().plusMonths(1));
        return new Date(
                calculator.getCurrentBusinessDate()
                          .toDateTimeAtStartOfDay()
                          .getMillis()
        );
    }

    public Date createDueDate(LocalDate localDate) {
        calculator.setStartDate(localDate.plusMonths(1));
        return new Date(
                calculator.getCurrentBusinessDate()
                          .toDateTimeAtStartOfDay()
                          .getMillis()
        );
    }

    public Date createDueDate(LocalDate localDate, int months) {
        calculator.setStartDate(localDate.plusMonths(months));
        return new Date(
                calculator.getCurrentBusinessDate()
                          .toDateTimeAtStartOfDay()
                          .getMillis()
        );
    }

    public ArrayList<PurchaseShare> generateShares(int number, LocalDate currentDate) {
        PurchaseShareBuilder builder = new PurchaseShareBuilder();
        ArrayList<PurchaseShare> list = new ArrayList<>(number);
        for (int i = 1; i <= number; ++i) {
            Date dueDate = this.createDueDate(currentDate, i);
            list.add(
                    builder.setNumber(1)
                           .setDueDate(dueDate)
                           .setPurchaseInvoiceId(3)
                           .createPurchaseShare()
            );
        }
        return list;
    }
}