package lpr.olil;

import lpr.olil.calculator.*;
import lpr.olil.model.*;
import lpr.olil.view.GeometryCalculatorPanel;

import javax.swing.*;

public class App {
    // main frame
    private static JFrame frame;

    private static void createAndShowGUI() {
        frame = new JFrame("Алчевск: калькулятор металлурга");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setSize(300, 300);

        GeometryCalculatorPanel geometryCalculatorPanel = new GeometryCalculatorPanel();

        frame.add(geometryCalculatorPanel);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });

        final double ductDiameter = 0.02;

        final Wall wall = new SmoothedWall(1.0, 0.9,0.01);
        final Slab slab = new Slab(1.3, 0.16);

        Crystallizer crystallizer = new CurvedCrystallizer(wall, ductDiameter, 1.3);

        final DuctCalculator.Result ductCalculationResult = DuctCalculator.calculateDuctCount(
                slab,
                crystallizer
        );

        System.out.println(ductCalculationResult.ductCount);

        WaterFlow waterFlow = new WaterFlow(20, 50, 1000, 4.187);

        final WaterFlowCalculator.Result waterFlowCalculationResult = WaterFlowCalculator.calculateWaterFlow(
                ductCalculationResult,
                crystallizer,
                waterFlow
        );

        System.out.println(waterFlowCalculationResult.flowVelocity);
        System.out.println(waterFlowCalculationResult.consumption);
    }
}
