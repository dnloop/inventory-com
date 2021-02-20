package io.github.dnloop.inventorycom.utils;

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
