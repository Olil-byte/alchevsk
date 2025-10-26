package lpr.olil.model;

public class Slab {
    private final double width; // m
    private final double height; // m

    public Slab(double width, double height) {
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
