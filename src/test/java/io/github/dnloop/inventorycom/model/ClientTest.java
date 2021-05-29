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
            .setDni("5546987")
            .setMail("johndoe@mail.com")
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
        client.setMail("m@mail.com");
        Map<String, String> constraints = validator.validate(client);
        assertThat(constraints.containsKey("name")).isTrue();
        assertThat(constraints.containsKey("surname")).isTrue();
        assertThat(constraints.containsKey("address")).isTrue();
        assertThat(constraints.containsKey("cuit")).isTrue();
        assertThat(constraints.containsKey("dni")).isTrue();
        assertThat(constraints.containsKey("mail")).isTrue();
        assertThat(constraints).hasSize(6);
    }

    @Test
    void fieldsOverSize() {
        // check it is over required size
        client.setName(lorem.getWords(61));
        client.setSurname(lorem.getWords(141));
        client.setAddress(lorem.getWords(281));
        client.setCuit(21236547895478L);
        client.setDni(lorem.getWords(9));
        client.setMail(lorem.getWords(341) + "@mail.com");
        Map<String, String> constraints = validator.validate(client);
        assertThat(constraints.containsKey("name")).isTrue();
        assertThat(constraints.containsKey("surname")).isTrue();
        assertThat(constraints.containsKey("address")).isTrue();
        assertThat(constraints.containsKey("cuit")).isTrue();
        assertThat(constraints.containsKey("dni")).isTrue();
        assertThat(constraints.containsKey("mail")).isTrue();
        assertThat(constraints).hasSize(6);
    }

    @Test
    void emptyClient() {
        Map<String, String> constraints = validator.validate(emptyClient);
        assertThat(constraints.containsKey("name")).isTrue();
        assertThat(constraints.containsKey("surname")).isTrue();
        assertThat(constraints.containsKey("address")).isTrue();
        assertThat(constraints.containsKey("localityId")).isTrue();
        assertThat(constraints).hasSize(4);
    }
}
