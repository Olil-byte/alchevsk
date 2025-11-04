package lpr.olil.util;

import lpr.olil.view.NumberField;

public class WallActiveLengthAdviser extends NumberAdviser {
    private final NumberField lengthField;

    public WallActiveLengthAdviser(NumberField lengthField) {
        this.lengthField = lengthField;
    }

    @Override
    public boolean isValid(double activeLength) {
        if (lengthField.hasValidValue()) {
            return (activeLength - lengthField.getValue()) <= 0.15;
        }

        return true;
    }

    @Override
    public String getMessage() {
        return "Активная длина меньше общей более чем на 0.15 метра!";
    }
}
