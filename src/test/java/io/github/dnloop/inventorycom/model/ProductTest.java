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
public class ProductTest {
    private static final Log log = LogFactory.getLog(ProductTest.class);

    private final LoremIpsum lorem = new LoremIpsum();

    private final Product product = new ProductBuilder()
            .setDescription(lorem.getWords(1))
            .setStock(1)
            .setProductCode("DN12")
            .createProduct();

    private final Product emptyProduct = new Product();

    @Autowired
    private EntityValidator validator;

    @Test
    void contextLoads() {}

    @Test
    void validProduct() {
        Map<String, String> constraints = validator.validate(product);
        log.debug(constraints);
        assertThat(constraints.isEmpty()).isTrue();
    }

    @Test
    void fieldsUnderSize() {
        // check it is under required size
        product.setDescription("e");
        product.setStock(-1);
        product.setProductCode("");
        Map<String, String> constraints = validator.validate(product);
        log.debug(constraints);
        assertThat(constraints.containsKey("product.description.size"));
        assertThat(constraints.containsKey("product.stock.range"));
        assertThat(constraints.containsKey("product.code.required"));
        assertThat(constraints).hasSize(3);
    }

    @Test
    void fieldsOverSize() {
        // check it is over required size
        product.setDescription(lorem.getWords(61));
        product.setStock(-1);
        product.setProductCode("");
        product.setImage(lorem.getWords(501));
        Map<String, String> constraints = validator.validate(product);
        log.debug(constraints);
        assertThat(constraints.containsKey("product.description.size"));
        assertThat(constraints.containsKey("product.stock.range"));
        assertThat(constraints.containsKey("product.code.required"));
        assertThat(constraints.containsKey("product.code.size"));
        assertThat(constraints.containsKey("product.image.size"));
        assertThat(constraints).hasSize(5);
    }

    @Test
    void emptyProduct() {
        Map<String, String> constraints = validator.validate(emptyProduct);
        log.debug(constraints);
        assertThat(constraints.containsKey("product.description.required"));
        assertThat(constraints.containsKey("product.code.required"));
        assertThat(constraints).hasSize(2);
    }
}
