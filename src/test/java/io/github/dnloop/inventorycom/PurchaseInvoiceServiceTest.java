package io.github.dnloop.inventorycom;

import io.github.dnloop.inventorycom.repository.PurchaseInvoiceRepository;
import io.github.dnloop.inventorycom.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

/**
 * Test {@link PurchaseInvoiceRepository} property in  {@link PurchaseService}.
 */
@SpringBootTest
@EnableAsync
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Sql({
             "/db/data/insert-category.sql",
     })
public class PurchaseInvoiceServiceTest {
     @Autowired
     PurchaseService purchaseService;


}
