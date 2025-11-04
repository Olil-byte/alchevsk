package lpr.olil.util;

import lpr.olil.view.NumberField;

public class WallLengthValidator extends NumberValidator {
    private final NumberField activeLengthField;

    public WallLengthValidator(NumberField activeLengthField) {
        this.activeLengthField = activeLengthField;
    }

    @Override
    public boolean isValid(double length) {
        if (activeLengthField.hasValidValue()) {
            return length > activeLengthField.getValue();
        }

        return true;
    }

    @Override
    public String getMessage() {
        return "Общая длина должна быть больше активной длины!";
    }
}
