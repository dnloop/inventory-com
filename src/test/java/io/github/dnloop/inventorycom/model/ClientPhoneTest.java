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
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ClientPhoneTest {

    private final ClientPhone clientPhone = new ClientPhoneBuilder()
            .setClientId(1)
            .createClientPhone();

    private final ClientPhone emptyClientPhone = new ClientPhoneBuilder()
            .createClientPhone();

    @Autowired
    private EntityValidator validator;

    @Test
    void validClientPhoneNumber() {
        PhoneNumber phoneNumber = new PhoneNumber("3764446612", 54);
        clientPhone.setPhoneNumber(phoneNumber);
        Map<String, String> constraints = validator.validate(clientPhone);
        assertThat(constraints).isEmpty();
    }

    @Test
    void invalidClientPhoneNumber() {
        PhoneNumber phoneNumber = new PhoneNumber("312612", 54);
        clientPhone.setPhoneNumber(phoneNumber);
        Map<String, String> constraints = validator.validate(clientPhone);
        assertThat(constraints.containsKey("client.phone.invalid"));
        assertThat(constraints).hasSize(1);
    }

    @Test
    void emptyClientPhoneNumber() {
        PhoneNumber phoneNumber = new PhoneNumber();
        clientPhone.setPhoneNumber(phoneNumber);
        Map<String, String> constraints = validator.validate(clientPhone);
        assertThat(constraints.containsKey("client.phone.invalid"));
        assertThat(constraints).hasSize(1);
    }
}
