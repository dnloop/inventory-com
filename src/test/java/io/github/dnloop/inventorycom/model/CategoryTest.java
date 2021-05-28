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
public class CategoryTest {

    private final Category validCategory = new Category("Lorem ipsum dolor sit amet.");

    private final Category emptyCategory = new Category();

    private final Category overSizeCategory = new Category(
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
            "Nam ullamcorper diam lacus, vel fringilla magna tristique at. " +
            "Donec elementum sapien in urna porttitor, id ornare justo tempor. " +
            "Phasellus nec vehicula nisi. Vestibulum sit amet auctor sapien, nec euismod felis. " +
            "Aliquam tristique efficitur scelerisque. Aliquam nec vulputate nunc. " +
            "Integer varius imperdiet dictum." +
            "Nulla tortor turpis, pharetra sit amet ex id, ornare condimentum arcu." +
            " Vestibulum ut tincidunt orci, eu sodales neque. Sed quis augue erat. " +
            "Nulla finibus nulla eu vehicula posuere. Praesent sit amet venenatis eros." +
            " Duis non eleifend purus. Donec tincidunt, orci at molestie bibendum, velit nisi " +
            "tincidunt mauris, vitae pellentesque sapien urna a magna. Vivamus massa neque, auctor " +
            "ut lectus in, pulvinar porta leo. Maecenas ultricies facilisis tortor, non euismod eros" +
            " suscipit vitae. Aliquam rhoncus ac mauris id mattis. Donec metus sapien, dignissim non sodales " +
            "et, accumsan at libero. Aenean. End"
    );

    @Autowired
    private EntityValidator validator;

    @Test
    void contextLoads() {}

    @Test
    void validCategory() {
        Map<String, String> constraints = validator.validate(validCategory);
        assertThat(constraints.isEmpty()).isTrue();
    }

    @Test
    void emptyCategory() {
        Map<String, String> constraints = validator.validate(new Category());
        assertThat(constraints.containsKey("description")).isTrue();
        assertThat(constraints).hasSize(1);
    }

    @Test
    void overSizeCategory() {
        Map<String, String> constraints = validator.validate(overSizeCategory);
        assertThat(constraints.containsKey("description")).isTrue();
        assertThat(constraints).hasSize(1);
    }
}
