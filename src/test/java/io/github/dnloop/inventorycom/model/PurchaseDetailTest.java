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

import com.thedeanda.lorem.LoremIpsum;
import io.github.dnloop.inventorycom.support.validator.EntityValidator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PurchaseDetailTest {
    private static final Log log = LogFactory.getLog(PurchaseDetailTest.class);

    private final PurchaseDetail purchaseDetail = new PurchaseDetailBuilder()
            .setAmount(1)
            .setProductId(1)
            .setUnitPrice(new BigDecimal("200.32"))
            .setSubtotal(new BigDecimal("200.32"))
            .setPurchaseInvoiceId(1)
            .setProductId(1)
            .createPurchaseDetail();

    private final PurchaseDetail emptyPurchaseDetail = new PurchaseDetail();

    @Autowired
    private EntityValidator validator;

    @Test
    void contextLoads() {}

    @Test
    void validPurchaseDetail() {
        Map<String, String> constraints = validator.validate(purchaseDetail);
        log.debug(constraints);
        assertThat(constraints.isEmpty()).isTrue();
    }

    @Test
    void fieldsUnderSize() {
        // check it is under required size
        purchaseDetail.setAmount(0);
        purchaseDetail.setUnitPrice(BigDecimal.ZERO);
        purchaseDetail.setSubtotal(BigDecimal.ZERO);
        Map<String, String> constraints = validator.validate(purchaseDetail);
        log.debug(constraints);
        assertThat(constraints.containsKey("purchaseDetail.amount.range"));
        assertThat(constraints.containsKey("purchaseDetail.unitPrice.min"));
        assertThat(constraints.containsKey("purchaseDetail.subtotal.size"));
        assertThat(constraints).hasSize(3);
    }

    @Test
    void fieldsOverSize() {
        // check it is over required size
        purchaseDetail.setAmount(2147483647);
        purchaseDetail.setUnitPrice(new BigDecimal("1000000000000000.000"));
        purchaseDetail.setSubtotal(new BigDecimal("1000000000000000.000"));
        Map<String, String> constraints = validator.validate(purchaseDetail);
        log.debug(constraints);
        assertThat(constraints.containsKey("purchaseDetail.unitPrice.digit"));
        assertThat(constraints.containsKey("purchaseDetail.subtotal.digit"));
        assertThat(constraints).hasSize(2);
    }

    @Test
    void emptyPurchaseDetail() {
        Map<String, String> constraints = validator.validate(emptyPurchaseDetail);
        log.debug(constraints);
        assertThat(constraints.containsKey("purchaseDetail.brand.required"));
        assertThat(constraints.containsKey("purchaseDetail.purchaseInvoice.required"));
        assertThat(constraints.containsKey("purchaseDetail.subtotal.min"));
        assertThat(constraints).hasSize(3);
    }
}
