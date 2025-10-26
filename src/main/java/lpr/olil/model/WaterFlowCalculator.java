package lpr.olil.model;

public class WaterFlowCalculator {
    private static double getScalarA(final Crystallizer crystallizer, final Wall wall) {
        if (crystallizer instanceof CurvedCrystallizer && wall instanceof SmoothedWall) {
            return 953.0;
        } else if (crystallizer instanceof VerticalCrystallizer && wall instanceof SmoothedWall) {
            return 1020.0;
        } else {
            throw new IllegalStateException();
        }
    }

    private static double getScalarM(final Crystallizer crystallizer, final Wall wall) {
        if (crystallizer instanceof CurvedCrystallizer && wall instanceof SmoothedWall) {
            return 1.0 / 3.0;
        } else if (crystallizer instanceof VerticalCrystallizer && wall instanceof SmoothedWall) {
            return 1.0 / 2.0;
        } else {
            throw new IllegalStateException();
        }
    }

    private static double calculateFlowVelocity(
            final DuctCalculator.Result ductResult,
            final Crystallizer crystallizer,
            final Wall wall,
            final WaterFlow waterFlow,
            double castingSpeed,
            double ductDiameter
    ) {

        final double A = getScalarA(crystallizer, wall);
        final double m = getScalarM(crystallizer, wall);

        return (A * castingSpeed * m * ductResult.perimeter * wall.getActiveLength()) /
                (ductResult.ductCount * Math.PI * ductDiameter * ductDiameter / 4.0 * waterFlow.getConductivity() *
                        waterFlow.getDensity() * (waterFlow.getOutletTemperature() - waterFlow.getInletTemperature()));
    }

    private static double calculateFlowArea(
            final DuctCalculator.Result ductResult,
            final double ductDiameter
    ) {
        final double singleFlowArea = Math.PI * Math.pow(ductDiameter, 2.0) / 4.0;

        return ductResult.ductCount * singleFlowArea;
    }

    public static class Result {
        public final double flowVelocity;
        public final double flowArea;
        public final double consumption;

        private Result(
                double flowVelocity,
                double flowArea,
                double consumption
        ) {
            this.flowVelocity = flowVelocity;
            this.flowArea = flowArea;
            this.consumption = consumption;
        }
    }

    public static Result calculateWaterFlow(
            final DuctCalculator.Result ductResult,
            final Crystallizer crystallizer,
            final Wall wall,
            final WaterFlow waterFlow,
            double castingSpeed,
            double ductDiameter
    ) {

        final double flowVelocity = calculateFlowVelocity(ductResult, crystallizer, wall, waterFlow, castingSpeed, ductDiameter);

        final double flowArea = calculateFlowArea(ductResult, ductDiameter);

        final double consumption = 3600.0 * flowVelocity * flowArea;

        return new Result(flowVelocity, flowArea, consumption);
    }
}
