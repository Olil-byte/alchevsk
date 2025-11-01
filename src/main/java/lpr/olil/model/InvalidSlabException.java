package lpr.olil.model;

public class InvalidSlabException extends RuntimeException {
    private final boolean isNonPositiveWidth;
    private final boolean isNonPositiveHeight;

    public InvalidSlabException(boolean isNonPositiveWidth, boolean isNonPositiveHeight) {
        super();

        this.isNonPositiveWidth = isNonPositiveWidth;
        this.isNonPositiveHeight = isNonPositiveHeight;
    }

    public boolean isNonPositiveWidth() {
        return isNonPositiveWidth;
    }
    public boolean isNonPositiveHeight() {
        return isNonPositiveHeight;
    }
}
