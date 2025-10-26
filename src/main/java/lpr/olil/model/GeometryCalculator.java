package lpr.olil.model;

public class GeometryCalculator {
    public static double calculatePerimeter(final Slab slab) {
        return slab.getWidth() * slab.getHeight() * 2.0;
    }

    public static double calculateDistanceBetweenDucts(final Wall wall, double ductDiameter) {
        return 2.0 * Math.sqrt(
                Math.pow(2.0 * wall.getThickness() + ductDiameter / 2.0, 2.0) -
                        Math.pow(wall.getThickness() + ductDiameter / 2.0, 2.0)
        );
    }
}
