package lpr.olil.util;

import lpr.olil.view.NumberField;

public class WallActiveLengthValidator extends NumberValidator {
    private final NumberField lengthField;

    public WallActiveLengthValidator(NumberField lengthField) {
        this.lengthField = lengthField;
    }

    @Override
    public boolean isValid(double activeLength) {
        if (lengthField.hasValidValue()) {
            return activeLength < lengthField.getValue();
        }

        return true;
    }

    @Override
    public String getMessage() {
        return "Активная длина должна быть меньше общей длины!";
    }
}
