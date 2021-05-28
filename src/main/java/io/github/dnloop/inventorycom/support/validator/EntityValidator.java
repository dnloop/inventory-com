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

package io.github.dnloop.inventorycom.support.validator;

import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Utility class to validate entities and return their error messages.
 */
public final class EntityValidator {

    private final Validator validator;

    /**
     * Creates a custom validator for different types of entities.
     *
     * @param factoryBean autowired factory with property messages provided by spring.
     */
    public EntityValidator(LocalValidatorFactoryBean factoryBean) {
        validator = factoryBean.getValidator();
    }

    /**
     * Perform validation on the given entity and return the invalid fields.
     *
     * @param <T> entity from model package.
     * @return Map with key being the field of the model and value the message.
     */
    public <T> Map<String, String> validate(T entity) {
        return validator.validate(entity).stream().collect(
                Collectors.toMap(
                        constraintViolation -> constraintViolation.getPropertyPath().toString(),
                        ConstraintViolation::getMessage
                )
        );
    }
}
