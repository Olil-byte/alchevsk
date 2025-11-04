package lpr.olil.util;

public class PositiveNumberValidator extends NumberValidator {

    private final String message;

    public PositiveNumberValidator(String message) {
        this.message = message;
    }

    @Override
    public boolean isValid(double value) {
        return value > 0.0;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
