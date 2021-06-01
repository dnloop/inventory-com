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
public class MeasureTest {
    private static final Log log = LogFactory.getLog(MeasureTest.class);

    private final LoremIpsum lorem = new LoremIpsum();

    private final Measure measure = new MeasureBuilder()
            .setType("cm")
            .setLength(1.0)
            .setWidth(1.0)
            .createMeasure();

    private final Measure emptyMeasure = new MeasureBuilder().createMeasure();

    @Autowired
    private EntityValidator validator;

    @Test
    void contextLoads() {}

    @Test
    void validMeasure() {
        Map<String, String> constraints = validator.validate(measure);
        assertThat(constraints.isEmpty()).isTrue();
    }

    @Test
    void fieldsUnderSize() {
        // check it is under required size
        measure.setType("");
        measure.setLength(-1.0);
        measure.setWidth(-1.0);
        measure.setDiameter(-1.0);
        Map<String, String> constraints = validator.validate(measure);
        log.debug(constraints);
        assertThat(constraints.containsKey("measure.type.required"));
        assertThat(constraints.containsKey("measure.length.min"));
        assertThat(constraints.containsKey("measure.width.min"));
        assertThat(constraints.containsKey("measure.diameter.min"));
        assertThat(constraints).hasSize(5);
    }

    @Test
    void fieldsOverSize() {
        // check it is over required size
        measure.setType(lorem.getWords(5));
        measure.setLength(1111111.666);
        measure.setWidth(1111111.666);
        measure.setDiameter(1111111.666);
        Map<String, String> constraints = validator.validate(measure);
        log.debug(constraints);
        assertThat(constraints.containsKey("measure.type.size"));
        assertThat(constraints.containsKey("measure.length.digit"));
        assertThat(constraints.containsKey("measure.width.digit"));
        assertThat(constraints.containsKey("measure.diameter.digit"));
        assertThat(constraints).hasSize(4);
    }

    @Test
    void emptyMeasure() {
        Map<String, String> constraints = validator.validate(emptyMeasure);
        log.debug(constraints);
        assertThat(constraints.containsKey("measure.type.required"));
        assertThat(constraints).hasSize(1);
    }
}
