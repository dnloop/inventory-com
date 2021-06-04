/*
 *     Inventory-Com: Inventory and Commerce Management Application.
 *     Copyright (C) 2021. dnloop.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 */

package io.github.dnloop.inventorycom.model;

import io.github.dnloop.inventorycom.support.validator.EntityValidator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PurchaseInvoiceTest {
    private static final Log log = LogFactory.getLog(PurchaseInvoiceTest.class);

    private final PurchaseInvoice purchaseInvoice = new PurchaseInvoiceBuilder()
            .setNumber(1)
            .setGenerationDate(Timestamp.from(Instant.now()))
            .setSupplierId(1)
            .setPaymentType("cash")
            .setTotal(new BigDecimal("200.32"))
            .createPurchaseInvoice();

    private final PurchaseInvoice emptyPurchaseInvoice = new PurchaseInvoice();

    @Autowired
    private EntityValidator validator;

    @Test
    void contextLoads() {}

    @Test
    void validPurchaseInvoice() {
        Map<String, String> constraints = validator.validate(purchaseInvoice);
        log.debug(constraints);
        assertThat(constraints.isEmpty()).isTrue();
    }

    @Test
    void fieldsUnderSize() {
        // check it is under required size
        purchaseInvoice.setNumber(0);
        purchaseInvoice.setGenDate(Instant.ofEpochSecond(1591270270L));
        purchaseInvoice.setTotal(BigDecimal.ZERO);
        purchaseInvoice.setSurcharge(new BigDecimal("-0.1"));
        purchaseInvoice.setDiscount(new BigDecimal("-0.1"));
        Map<String, String> constraints = validator.validate(purchaseInvoice);
        log.debug(constraints);
        assertThat(constraints.containsKey("purchaseInvoice.number.range"));
        assertThat(constraints.containsKey("purchaseInvoice.generationDate.dateFoP"));
        assertThat(constraints.containsKey("purchaseInvoice.surcharge.min"));
        assertThat(constraints.containsKey("purchaseInvoice.discount.min"));
        assertThat(constraints.containsKey("purchaseInvoice.total.min"));
        assertThat(constraints).hasSize(5);
    }

    @Test
    void fieldsOverSize() {
        // check it is over required size
        purchaseInvoice.setNumber(2147483647);
        purchaseInvoice.setGenDate(Instant.ofEpochSecond(1591270270L));
        purchaseInvoice.setTotal(BigDecimal.ZERO);
        purchaseInvoice.setSurcharge(new BigDecimal("1000000000000000.000"));
        purchaseInvoice.setDiscount(new BigDecimal("1000000000000000.000"));
        Map<String, String> constraints = validator.validate(purchaseInvoice);
        log.debug(constraints);
        assertThat(constraints.containsKey("purchaseInvoice.generationDate.dateFoP"));
        assertThat(constraints.containsKey("purchaseInvoice.surcharge.digit"));
        assertThat(constraints.containsKey("purchaseInvoice.discount.digit"));
        assertThat(constraints.containsKey("purchaseInvoice.total.digit"));
        assertThat(constraints).hasSize(4);
    }

    @Test
    void emptyPurchaseInvoice() {
        Map<String, String> constraints = validator.validate(emptyPurchaseInvoice);
        log.debug(constraints);
        assertThat(constraints.containsKey("purchaseInvoice.total.digit"));
        assertThat(constraints.containsKey("purchaseInvoice.supplier.required"));
        assertThat(constraints).hasSize(2);
    }
}
