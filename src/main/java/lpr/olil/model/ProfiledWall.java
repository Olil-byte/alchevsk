package lpr.olil.model;

public class ProfiledWall extends Wall {
    private final double thickness; // m
    private final double length; // m
    private final double activeLength; // m

    public ProfiledWall(double length, double activeLength, double thickness) {
        this.length = length;
        this.activeLength = activeLength;
        this.thickness = thickness;
    }

    @Override
    public double getLength() {
        return length;
    }
    @Override
    public double getActiveLength() {
        return activeLength;
    }
    @Override
    public double getThickness() {
        return thickness;
    }
}
