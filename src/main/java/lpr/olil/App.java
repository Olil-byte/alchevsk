package lpr.olil;

import lpr.olil.calculator.*;
import lpr.olil.model.*;

public class App {

    public static void main(String[] args) {
        final double ductDiameter = 0.02;

        final Wall wall = new SmoothedWall(1.0, 0.9,0.01);
        final Slab slab = new Slab(1.3, 0.16);

        final DuctCalculator.Result ductCalculationResult = DuctCalculator.calculateDuctCount(
                slab,
                wall,
                ductDiameter
        );

        System.out.println(ductCalculationResult.ductCount);

        Crystallizer crystallizer = new CurvedCrystallizer(1.3);
        WaterFlow waterFlow = new WaterFlow(20, 50, 1000, 4.187);

        final WaterFlowCalculator.Result waterFlowCalculationResult = WaterFlowCalculator.calculateWaterFlow(
                ductCalculationResult,
                crystallizer,
                wall,
                waterFlow,
                ductDiameter
        );

        System.out.println(waterFlowCalculationResult.flowVelocity);
        System.out.println(waterFlowCalculationResult.consumption);
    }
}
