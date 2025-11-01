package lpr.olil.calculator;

import lpr.olil.model.Crystallizer;
import lpr.olil.model.Slab;
import lpr.olil.model.Wall;

import java.util.Objects;

public class DuctCalculator {
    public static class Result {
        public final double perimeter;
        public final double distanceBetweenDucts;
        public final int ductCount;

        private Result(double perimeter, double distanceBetweenDucts, int ductCount) {
            this.perimeter = perimeter;
            this.distanceBetweenDucts = distanceBetweenDucts;
            this.ductCount = ductCount;
        }
    }

    public static Result calculateDuctCount(
            final Slab slab,
            final Crystallizer crystallizer
    ) {

        final double ductDiameter = crystallizer.getDuctDiameter();
        final Wall wall = crystallizer.getWall();

        final double perimeter = GeometryCalculator.calculatePerimeter(slab);

        final double distanceBetweenDucts = GeometryCalculator.calculateDistanceBetweenDucts(wall, ductDiameter);

        final int ductCount = (int)Math.ceil((perimeter) / (distanceBetweenDucts + ductDiameter));

        return new Result(perimeter, distanceBetweenDucts, ductCount);
    }
}
