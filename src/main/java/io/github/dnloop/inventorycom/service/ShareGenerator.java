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

package io.github.dnloop.inventorycom.service;

import io.github.dnloop.inventorycom.support.BusinessDate;
import net.objectlab.kit.datecalc.common.DateCalculator;
import org.joda.time.LocalDate;

import java.sql.Date;
import java.util.ArrayList;

public abstract class ShareGenerator<T> {
    private final DateCalculator<LocalDate> calculator;

    protected ShareGenerator() {
        BusinessDate businessDate = new BusinessDate();
        calculator = businessDate.getDateCalculator();
    }

    public Date createDueDate() {
        calculator.setStartDate(LocalDate.now().plusMonths(1));
        return new Date(
                calculator.getCurrentBusinessDate()
                          .toDateTimeAtStartOfDay()
                          .getMillis()
        );
    }

    public LocalDate createDueDate(LocalDate localDate) {
        calculator.setStartDate(localDate.plusMonths(1));
        return new LocalDate(
                calculator.getCurrentBusinessDate()
                          .toDateTimeAtStartOfDay()
                          .getMillis()
        );
    }

    public LocalDate createDueDate(LocalDate localDate, int months) {
        calculator.setStartDate(localDate.plusMonths(months));
        return new LocalDate(
                calculator.getCurrentBusinessDate()
                          .toDateTimeAtStartOfDay()
                          .getMillis()
        );
    }

    abstract ArrayList<T> generateShares(int number, LocalDate currentDate);
}
