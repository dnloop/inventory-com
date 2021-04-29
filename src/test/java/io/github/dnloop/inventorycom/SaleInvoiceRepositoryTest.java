package io.github.dnloop.inventorycom;

import io.github.dnloop.inventorycom.repository.SaleInvoiceRepository;
import io.github.dnloop.inventorycom.service.SaleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

import java.util.concurrent.ExecutionException;

/**
 * Test {@link SaleInvoiceRepository} property in  {@link SaleService}.
 */
@SpringBootTest
@EnableAsync
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Sql({
             "/db/data/localities.sql",
             "/db/data/clients.sql",
             "/db/data/saleInvoice.sql",
             "/db/data/saleDetail.sql"
     })
public class SaleInvoiceRepositoryTest {

    @Autowired
    SaleService saleService;

    @Test
    void contextLoads() throws ExecutionException, InterruptedException {}

    @Test
    void purchaseInvoiceNull() throws ExecutionException, InterruptedException {

    }

    @Test
    void findSaleInvoiceById() throws ExecutionException, InterruptedException {

    }

    @Test
    void findAllSaleInvoice() throws ExecutionException, InterruptedException {

    }

    @Test
    void saveSaleInvoice() throws ExecutionException, InterruptedException {

    }

    @Test
    void modifySaleInvoice() throws ExecutionException, InterruptedException {

    }
}
