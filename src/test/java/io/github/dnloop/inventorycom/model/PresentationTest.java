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
public class PresentationTest {
    private static final Log log = LogFactory.getLog(PresentationTest.class);

    private final LoremIpsum lorem = new LoremIpsum();

    private final Presentation presentation = new Presentation("presentation");

    private final Presentation emptyPresentation = new Presentation();

    @Autowired
    private EntityValidator validator;

    @Test
    void contextLoads() {}

    @Test
    void validPresentation() {
        Map<String, String> constraints = validator.validate(presentation);
        assertThat(constraints.isEmpty()).isTrue();
    }

    @Test
    void fieldsUnderSize() {
        // check it is under required size
        presentation.setDescription("e");
        Map<String, String> constraints = validator.validate(presentation);
        log.debug(constraints);
        assertThat(constraints.containsKey("presentation.description.size"));
        assertThat(constraints).hasSize(1);
    }

    @Test
    void fieldsOverSize() {
        // check it is over required size
        presentation.setDescription(lorem.getWords(141));
        Map<String, String> constraints = validator.validate(presentation);
        log.debug(constraints);
        assertThat(constraints.containsKey("presentation.description.required"));
        assertThat(constraints.containsKey("presentation.description.size"));
        assertThat(constraints).hasSize(1);
    }

    @Test
    void emptyPresentation() {
        Map<String, String> constraints = validator.validate(emptyPresentation);
        log.debug(constraints);
        assertThat(constraints.containsKey("presentation.description.required"));
        assertThat(constraints).hasSize(1);
    }
}
