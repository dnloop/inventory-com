package io.github.dnloop.inventorycom;

import io.github.dnloop.inventorycom.repository.PurchaseInvoiceRepository;
import io.github.dnloop.inventorycom.service.PurchaseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

import java.util.concurrent.ExecutionException;

/**
 * Test {@link PurchaseInvoiceRepository} property in  {@link PurchaseService}.
 */
@SpringBootTest
@EnableAsync
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Sql({

     })
public class PurchaseInvoiceRepositoryTest {

    @Autowired
    PurchaseService purchaseService;

    @Test
    void contextLoads() throws ExecutionException, InterruptedException {}

    @Test
    void purchaseInvoiceNull() throws ExecutionException, InterruptedException {

    }

    @Test
    void findPurchaseInvoiceById() {

    }

    /**
     * Query a deleted record with a non-delete clause
     */
    @Test
    void findPurchaseInvoiceByIdDeleted() throws ExecutionException, InterruptedException {

    }

    @Test
    void findDeletedPurchaseInvoice() throws ExecutionException, InterruptedException {

    }

    @Test
    void findAllPurchaseInvoice() throws ExecutionException, InterruptedException {

    }

    @Test
    void findAllDeletedPurchaseInvoice() throws ExecutionException, InterruptedException {

    }

    @Test
    void savePurchaseInvoice() throws ExecutionException, InterruptedException {

    }

    @Test
    void modifyPurchaseInvoice() throws ExecutionException, InterruptedException {

    }

    @Test
    void deletePurchaseInvoice() throws ExecutionException, InterruptedException {

    }
}
