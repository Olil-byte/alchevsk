package lpr.olil.model;

public class SmoothedWall extends Wall {
    private final double thickness;
    private final double length;
    private final double activeLength;

    public SmoothedWall(double length, double activeLength, double thickness) {
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
