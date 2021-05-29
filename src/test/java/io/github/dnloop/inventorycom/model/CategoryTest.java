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
public class CategoryTest {

    private final LoremIpsum lorem = new LoremIpsum();

    private final Category validCategory = new Category("Lorem ipsum dolor sit amet.");

    private final Category emptyCategory = new Category();

    private final Category overSizeCategory = new Category(lorem.getWords(141));

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
        Map<String, String> constraints = validator.validate(emptyCategory);
        assertThat(constraints.containsKey("category.required"));
        assertThat(constraints).hasSize(1);
    }

    @Test
    void overSizeCategory() {
        Map<String, String> constraints = validator.validate(overSizeCategory);
        assertThat(constraints.containsKey("category.description.size"));
        assertThat(constraints).hasSize(1);
    }
}
