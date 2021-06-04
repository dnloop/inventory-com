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
import org.joda.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PurchaseShareTest {
    private static final Log log = LogFactory.getLog(PurchaseShareTest.class);

    private final PurchaseShare purchaseShare = new PurchaseShareBuilder()
            .setNumber(1)
            .setDueDate(org.joda.time.LocalDate.now())
            .setPaymentDate(org.joda.time.LocalDate.now().plusMonths(3))
            .setPurchaseInvoiceId(1)
            .createPurchaseShare();

    private final PurchaseShare emptyPurchaseShare = new PurchaseShare();

    @Autowired
    private EntityValidator validator;

    @Test
    void contextLoads() {}

    @Test
    void validPurchaseShare() {
        Map<String, String> constraints = validator.validate(purchaseShare);
        log.debug(constraints);
        assertThat(constraints.isEmpty()).isTrue();
    }

    @Test
    void fieldsUnderSize() {
        // check it is under required size
        purchaseShare.setNumber(0);
        purchaseShare.setDueDate(org.joda.time.LocalDate.now().minusYears(1));
        purchaseShare.setPaymentDate(LocalDate.now().minusYears(1));
        Map<String, String> constraints = validator.validate(purchaseShare);
        log.debug(constraints);
        assertThat(constraints.containsKey("purchaseShare.number.range"));
        assertThat(constraints.containsKey("purchaseShare.dueDate.dateFoP"));
        assertThat(constraints.containsKey("purchaseShare.paymentDate.dateFoP"));
        assertThat(constraints).hasSize(3);
    }

    @Test
    void emptyPurchaseShare() {
        Map<String, String> constraints = validator.validate(emptyPurchaseShare);
        log.debug(constraints);
        assertThat(constraints.containsKey("purchaseShare.purchaseInvoice.required"));
        assertThat(constraints).hasSize(1);
    }
}
