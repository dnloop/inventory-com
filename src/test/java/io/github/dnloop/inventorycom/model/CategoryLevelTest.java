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
public class CategoryLevelTest {

    private final CategoryLevel validCategoryLevel = new CategoryLevel(1, new LevelBuilder().buildLevel());

    private final CategoryLevel emptyCategoryLevel = new CategoryLevel();

    @Autowired
    private EntityValidator validator;

    @Test
    void contextLoads() {}

    @Test
    void validCategoryLevel() {
        Map<String, String> constraints = validator.validate(validCategoryLevel);
        assertThat(constraints.isEmpty()).isTrue();
    }

    @Test
    void emptyCategoryLevel() {
        Map<String, String> constraints = validator.validate(emptyCategoryLevel);
        assertThat(constraints.containsKey("categoryId")).isTrue();
        assertThat(constraints).hasSize(1);
    }
}
