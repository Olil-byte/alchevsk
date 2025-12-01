package lpr.olil.view;

import lpr.olil.calculator.DuctCalculator;
import lpr.olil.calculator.WaterFlowCalculator;
import lpr.olil.model.*;

public class FormulaBuilder {
    private static String formatNumber(double value) {
        if (value == (long) value) {
            return String.format("%d", (long) value);
        } else {
            String formatted = String.format("%.3f", value);
            return formatted.replaceAll("0*$", "").replaceAll("\\.$", "");
        }
    }

    public static String buildDistanceFormula(Wall wall, Crystallizer crystallizer, double distance) {
        return String.format(
                "Z = 2 \\cdot \\sqrt{\\left(2 \\cdot %s + \\frac{%s}{2}\\right)^2 - \\left(%s + \\frac{%s}{2}\\right)^2} = %s\\,м",
                formatNumber(wall.getLength()),
                formatNumber(crystallizer.getDuctDiameter()),
                formatNumber(wall.getLength()),
                formatNumber(crystallizer.getDuctDiameter()),
                formatNumber(distance)
        );
    }

    public static String buildPerimeterFormula(Slab slab, double perimeter) {
        return String.format(
                "П = 2 \\cdot (%s + %s) = %s\\,м",
                formatNumber(slab.getWidth()),
                formatNumber(slab.getLength()),
                formatNumber(perimeter)
        );
    }

    public static String buildDuctCountFormula(double perimeter, double distance, double diameter, int ductCount) {
        return String.format(
                "n = \\frac{%s}{%s + %s} = %d\\,шт",
                formatNumber(perimeter),
                formatNumber(distance),
                formatNumber(diameter),
                ductCount
        );
    }

    public static String buildFlowVelocityFormula(
            DuctCalculator.Result ductResult,
            Wall wall,
            Crystallizer crystallizer,
            Slab slab,
            Ccm ccm,
            WaterFlow waterFlow,
            WaterFlowCalculator.Result waterResult) {
        return String.format(
                "W_{в} = \\frac{2 %s %s %s (%s + %s) \\cdot %s}{%s \\left(\\pi %s^2/4\\right) \\cdot %s \\cdot %s \\left(%s - %s\\right)} = %s\\,м/c",
                formatNumber(WaterFlowCalculator.getScalarA(ccm, wall)),
                formatNumber(ccm.getCastingSpeed()),
                formatNumber(WaterFlowCalculator.getScalarM(ccm, wall)),
                formatNumber(slab.getLength()),
                formatNumber(slab.getWidth()),
                formatNumber(wall.getActiveLength()),
                formatNumber(ductResult.ductCount),
                formatNumber(crystallizer.getDuctDiameter()),
                formatNumber(waterFlow.getConductivity()),
                formatNumber(waterFlow.getDensity()),
                formatNumber(waterFlow.getOutletTemperature()),
                formatNumber(waterFlow.getInletTemperature()),
                formatNumber(waterResult.flowVelocity)
        );
    }

    public static String buildConsumptionFormula(
            Crystallizer crystallizer,
            DuctCalculator.Result ductResult,
            WaterFlowCalculator.Result waterResult) {
        return String.format(
                "G_{в} = \\left(\\pi %s^2 / 4\\right) \\cdot %s \\cdot %s = %s\\,л/c",
                formatNumber(crystallizer.getDuctDiameter()),
                formatNumber(ductResult.ductCount),
                formatNumber(waterResult.flowVelocity),
                formatNumber(waterResult.consumption)
        );
    }
}