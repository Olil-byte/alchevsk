package lpr.olil.model;

public class Slab {
    private final double width; // m
    private final double height; // m

    public Slab(double width, double height) {
        final boolean isNonPositiveWidth = width <= 0.0;
        final boolean isNonPositiveHeight = height <= 0.0;

        if (isNonPositiveWidth || isNonPositiveHeight) {
            throw new InvalidSlabException(isNonPositiveWidth, isNonPositiveHeight);
        }

        this.width = width;
        this.height = height;
    }

    public double getWidth() {
        return width;
    }
    public double getHeight() {
        return height;
    }
}
