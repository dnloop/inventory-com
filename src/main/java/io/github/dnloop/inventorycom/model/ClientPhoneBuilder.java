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

public class ClientPhoneBuilder {
    private PhoneNumber phoneNumber;
    private String number;
    private Integer clientId;

    public ClientPhoneBuilder setPhoneNumber(PhoneNumber phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public ClientPhoneBuilder setNumber(String number) {
        this.number = number;
        return this;
    }

    public ClientPhoneBuilder setClientId(Integer clientId) {
        this.clientId = clientId;
        return this;
    }

    public ClientPhone createClientPhone() {
        return new ClientPhone(phoneNumber, number, clientId);
    }
}