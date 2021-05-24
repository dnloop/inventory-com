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

package io.github.dnloop.inventorycom.support;

import javafx.scene.control.TextFormatter;
import javafx.util.converter.DoubleStringConverter;

import java.util.regex.Pattern;

public class FieldFormatter {
    private static final Pattern integerText = Pattern.compile(PatternFormat.DECIMAL.getValue());

    private static final Pattern doubleText = Pattern.compile(PatternFormat.FLOAT.getValue());

    private TextFormatter<String> integer;

    private TextFormatter<Double> floatPoint;

    public FieldFormatter() {
        super();
    }

    public TextFormatter<Double> getFloatPoint() {
        return floatPoint;
    }

    public void setFloatPoint() {
        floatPoint = new TextFormatter<>(new DoubleStringConverter(), 0.0, change -> {
            String newText = change.getControlNewText();
            if (doubleText.matcher(newText).matches())
                return change;
            else
                return null;
        });
    }

    public TextFormatter<String> getInteger() {
        return integer;
    }

    public void setInteger() {
        integer = new TextFormatter<>(change -> {
            String text = change.getText();

            if (integerText.matcher(text).matches())
                return change;

            return null;
        });
    }
}
