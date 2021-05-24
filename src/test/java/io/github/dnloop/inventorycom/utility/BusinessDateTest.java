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

package io.github.dnloop.inventorycom.utility;

import io.github.dnloop.inventorycom.model.PurchaseShare;
import io.github.dnloop.inventorycom.model.SaleShare;
import io.github.dnloop.inventorycom.support.BusinessDate;
import net.objectlab.kit.datecalc.common.DateCalculator;
import org.joda.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * <p>Test units applicable to {@link PurchaseShare} and {@link SaleShare}.</p>
 * <p>Ensure expected date is one day after non working days every time a month is added.</p>
 */
@SpringBootTest
public class BusinessDateTest {

    private final DateCalculator<LocalDate> calculator = new BusinessDate().getDateCalculator();

    @Test
    void contextLoads() {}


    @Test
    void moveOneDay() {
        LocalDate startDate = new LocalDate("2021-01-15").plusMonths(1);
        calculator.setStartDate(startDate);
        LocalDate expectedDate = new LocalDate("2021-02-17");
        LocalDate date = calculator.getCurrentBusinessDate();
        assertThat(date).isEqualTo(expectedDate);
    }

    @Test
    void moveOneDayOnWeekend() {
        LocalDate startDate = new LocalDate("2021-01-21").plusMonths(1);
        calculator.setStartDate(startDate);
        LocalDate expectedDate = new LocalDate("2021-02-22");
        LocalDate date = calculator.getCurrentBusinessDate();
        assertThat(date).isEqualTo(expectedDate);
    }

    @Test
    void moveBackOnEndOfMonth() {
        LocalDate startDate = new LocalDate("2021-01-28").plusMonths(1);
        calculator.setStartDate(startDate);
        LocalDate expectedDate = new LocalDate("2021-02-26");
        LocalDate date = calculator.getCurrentBusinessDate();
        assertThat(date).isEqualTo(expectedDate);
    }

}
