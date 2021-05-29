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
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ClientTest {

    private final LoremIpsum lorem = new LoremIpsum();

    private final Client client = new ClientBuilder()
            .setName("John")
            .setSurname("Doe")
            .setAddress("No name street")
            .setCuit(2155469878L)
            .setDni("55469872")
            .setMail("johndoe@mail.com")
            .setLocalityId(1)
            .createClient();

    private final Client emptyClient = new ClientBuilder().createClient();

    @Autowired
    private EntityValidator validator;

    @Test
    void contextLoads() {}

    @Test
    void validClient() {
        Map<String, String> constraints = validator.validate(client);
        assertThat(constraints.isEmpty()).isTrue();
    }

    @Test
    void fieldsUnderSize() {
        // check it is under required size
        client.setName("E");
        client.setSurname("E");
        client.setAddress("neo");
        client.setCuit(12356L);
        client.setDni("12356");
        Map<String, String> constraints = validator.validate(client);
        assertThat(constraints.containsKey("client.address.size"));
        assertThat(constraints.containsKey("client.dni.size"));
        assertThat(constraints).hasSize(2);
    }

    @Test
    void fieldsOverSize() {
        // check it is over required size
        client.setName(lorem.getWords(61));
        client.setSurname(lorem.getWords(141));
        client.setAddress(lorem.getWords(281));
        client.setCuit(21236547895478L);
        client.setDni(lorem.getWords(9));
        client.setMail(
                "contact-admin-hello-webmaster-info-services-peter-crazy-but-oh-so-ubber-cool-english-alphabet-" +
                "loverer-abcdefghijklmnopqrstuvwxyz@please-try-to.send-me-an-email-if-you-can-possibly-begin-to-" +
                "remember-this-coz.this-is-the-longest-email-address-known-to-man-but-to-be-honest.this-is-such-" +
                "a-stupidly-long-sub-domain-it-could-go-on-forever.pacraig.com"
        );
        Map<String, String> constraints = validator.validate(client);
        assertThat(constraints.containsKey("client.name.size"));
        assertThat(constraints.containsKey("client.surname.size"));
        assertThat(constraints.containsKey("client.address.size"));
        assertThat(constraints.containsKey("client.cuit.size"));
        assertThat(constraints.containsKey("client.dni.size"));
        assertThat(constraints.containsKey("client.mail.max"));
        assertThat(constraints).hasSize(6);
    }

    @Test
    void emptyClient() {
        Map<String, String> constraints = validator.validate(emptyClient);
        assertThat(constraints.containsKey("client.name.required"));
        assertThat(constraints.containsKey("client.surname.required"));
        assertThat(constraints.containsKey("client.address.required"));
        assertThat(constraints.containsKey("client.localityId.required"));
        assertThat(constraints).hasSize(4);
    }
}
