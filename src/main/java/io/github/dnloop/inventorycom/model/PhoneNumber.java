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

import com.google.i18n.phonenumbers.Phonenumber;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

/**
 * <h4>Phone Number</h4>
 * <p>
 * This class is a wrapper used for libphonenumber model used for validation.
 * </p>
 * <p>
 * The related classes:  {@link ClientPhone} and {@link SupplierPhone} are used for persistence.
 * </p>
 *
 * @see <a href="https://github.com/google/libphonenumber">libphonenumber</a>
 */
public class PhoneNumber extends Phonenumber.PhoneNumber {
    @NotEmpty(message = "{phone.required}")
    private String value;

    public PhoneNumber() {}

    public PhoneNumber(String value, @Min(1) int countryCode) {
        this.value = "+" + countryCode + value;
        super.setRawInput(value);
        super.setCountryCode(countryCode);
    }

    /**
     * Method required for validation in PhoneNumberValidator.
     *
     * @see io.github.dnloop.inventorycom.support.validator.PhoneNumberValidator
     */
    public String getValue() {
        return value;
    }

    /**
     * Set a raw value to be parsed by the library.
     */
    public void setValue(String value) {
        this.value = value;
        super.setRawInput(value);
    }
}
