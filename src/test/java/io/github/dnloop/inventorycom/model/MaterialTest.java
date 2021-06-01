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
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MaterialTest {
    private static final Log log = LogFactory.getLog(MaterialTest.class);
    private final LoremIpsum lorem = new LoremIpsum();
    private final Material material = new Material("plastic");

    private final Material emptyMaterial = new Material();

    @Autowired
    private EntityValidator validator;

    @Test
    void contextLoads() {}

    @Test
    void validMaterial() {
        Map<String, String> constraints = validator.validate(material);
        assertThat(constraints.isEmpty()).isTrue();
    }

    @Test
    void fieldsUnderSize() {
        // check it is under required size
        material.setType("E");
        Map<String, String> constraints = validator.validate(material);
        log.debug(constraints);
        assertThat(constraints.containsKey("material.type.size"));
        assertThat(constraints).hasSize(1);
    }

    @Test
    void fieldsOverSize() {
        // check it is over required size
        material.setType(lorem.getWords(321));
        Map<String, String> constraints = validator.validate(material);
        log.debug(constraints);
        assertThat(constraints.containsKey("material.type.size"));
        assertThat(constraints).hasSize(1);
    }

    @Test
    void emptyMaterial() {
        Map<String, String> constraints = validator.validate(emptyMaterial);
        log.debug(constraints);
        assertThat(constraints.containsKey("material.type.required"));
        assertThat(constraints).hasSize(1);
    }
}
