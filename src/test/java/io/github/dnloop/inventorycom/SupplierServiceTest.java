package io.github.dnloop.inventorycom;

import io.github.dnloop.inventorycom.repository.SupplierRepository;
import io.github.dnloop.inventorycom.service.SupplierService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

import java.util.concurrent.ExecutionException;

/**
 * Test {@link SupplierRepository} property in  {@link SupplierService}.
 */
@SpringBootTest
@EnableAsync
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Sql({

     })
public class SupplierServiceTest {

    @Autowired
    SupplierService supplierService;

    @Test
    void contextLoads() throws ExecutionException, InterruptedException {}

    @Test
    void purchaseInvoiceNull() throws ExecutionException, InterruptedException {

    }

    @Test
    void findSupplierById() {

    }

    /**
     * Query a deleted record with a non-delete clause
     */
    @Test
    void findSupplierByIdDeleted() throws ExecutionException, InterruptedException {

    }

    @Test
    void findDeletedSupplier() throws ExecutionException, InterruptedException {

    }

    @Test
    void findAllSupplier() throws ExecutionException, InterruptedException {

    }

    @Test
    void findAllDeletedSupplier() throws ExecutionException, InterruptedException {

    }

    @Test
    void saveSupplier() throws ExecutionException, InterruptedException {

    }

    @Test
    void modifySupplier() throws ExecutionException, InterruptedException {

    }

    @Test
    void deleteSupplier() throws ExecutionException, InterruptedException {

    }
}
