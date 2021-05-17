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

package io.github.dnloop.inventorycom.utils;

import net.objectlab.kit.datecalc.common.DateCalculator;
import net.objectlab.kit.datecalc.common.DefaultHolidayCalendar;
import net.objectlab.kit.datecalc.common.HolidayCalendar;
import net.objectlab.kit.datecalc.joda.LocalDateKitCalculatorsFactory;
import org.joda.time.LocalDate;

import java.util.HashSet;
import java.util.Set;

/**
 * Wrapper class of the library provided by ObjectLab-Kit. It abstracts the creation of holidays
 * by returning a DateCalculator instance.
 *
 * @see <a href= "https://github.com/appendium/objectlabkit">ObjectLab-Kit</a>
 */
public class BusinessDate {
    private final Set<LocalDate> holidays = new HashSet<>();
    private final int year = LocalDate.now().getYear();

    public BusinessDate() {
        setImmovableHolidays();
        setTransferableHolidays();
        setNonWorkingHolidays();
        HolidayCalendar<LocalDate> calendar = new DefaultHolidayCalendar<>(
                holidays, new LocalDate(year + "-01-01"),
                new LocalDate(year + "-12-31")
        );
        LocalDateKitCalculatorsFactory.getDefaultInstance().registerHolidays("AR", calendar);
    }

    /**
     * Algorithm used to create long weekends using the following conditions:
     * if tuesday or wednesday then transfer to previous monday.
     * if thursday or friday then transfer to next monday.
     */
    private LocalDate transferableAlgorithm(LocalDate date) {
        int days = 0;
        int dayOfMonth = date.getDayOfMonth();
        int dayOfWeek = date.getDayOfWeek();

        do {
            ++days;
            --dayOfWeek;
        } while (dayOfWeek != 1);

        if (date.getDayOfWeek() == 2 || date.getDayOfWeek() == 3)
            dayOfMonth = date.minusDays(days).getDayOfMonth();

        if (date.getDayOfWeek() == 4 || date.getDayOfWeek() == 5)
            dayOfMonth = date.minusDays(days)
                             .plusWeeks(1)
                             .getDayOfMonth();

        String adjustedDate = String.valueOf(date.getYear()) +
                              '-' +
                              date.getMonthOfYear() +
                              '-' +
                              dayOfMonth;
        return new LocalDate(adjustedDate);
    }

    /**
     * Depending on which year is the library being executed load immovable holidays.
     */
    private void setImmovableHolidays() {
        holidays.add(new LocalDate(year + "-01-01"));
        holidays.add(new LocalDate(year + "-02-15"));
        holidays.add(new LocalDate(year + "-02-16"));
        holidays.add(new LocalDate(year + "-03-24"));
        holidays.add(new LocalDate(year + "-04-01"));
        holidays.add(new LocalDate(year + "-04-25"));
        holidays.add(new LocalDate(year + "-05-20"));
        holidays.add(new LocalDate(year + "-06-09"));
        holidays.add(new LocalDate(year + "-10-08"));
        holidays.add(new LocalDate(year + "-11-22"));
        holidays.add(new LocalDate(year + "-12-08"));
        holidays.add(new LocalDate(year + "-12-25"));
    }

    /**
     * Load transferable holidays after performing calculations.
     */
    private void setTransferableHolidays() {
        holidays.add(transferableAlgorithm(new LocalDate(year + "-05-17")));
        holidays.add(transferableAlgorithm(new LocalDate(year + "-10-12")));
        holidays.add(transferableAlgorithm(new LocalDate(year + "-11-20")));
    }

    /**
     * Load Non-working days.
     */
    private void setNonWorkingHolidays() {
        holidays.add(new LocalDate(year + "-03-28"));
        holidays.add(new LocalDate(year + "-03-29"));
        holidays.add(new LocalDate(year + "-04-01"));
        holidays.add(new LocalDate(year + "-04-03"));
        holidays.add(new LocalDate(year + "-04-04"));
        holidays.add(new LocalDate(year + "-04-24"));
        holidays.add(new LocalDate(year + "-05-13"));
        holidays.add(new LocalDate(year + "-07-20"));
        holidays.add(new LocalDate(year + "-08-08"));
        holidays.add(new LocalDate(year + "-09-07"));
        holidays.add(new LocalDate(year + "-09-08"));
        holidays.add(new LocalDate(year + "-09-16"));
    }

    public DateCalculator<LocalDate> getDateCalculator() {
        return LocalDateKitCalculatorsFactory.forwardCalculator("AR");
    }
}
