package lpr.olil;

import lpr.olil.calculator.*;
import lpr.olil.model.*;
import lpr.olil.user.User;
import lpr.olil.view.MainFrame;

import javax.swing.*;

public class App {
    // main frame
    private static JFrame frame;

    private static void createAndShowGUI() {
        frame = new MainFrame("Алчевск: металлургический калькулятор");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setSize(300, 300);

        frame.setVisible(true);
    }

    public static void main(String[] args) {

        final double ductDiameter = 0.02;

        final Wall wall = new SmoothedWall(1.0, 0.9,0.01);
        final Slab slab = new Slab(1.3, 0.16);

        Crystallizer crystallizer = new Crystallizer(wall, ductDiameter);

        Ccm ccm = new CurvedCcm(1.3, crystallizer);

        final DuctCalculator.Result ductCalculationResult = DuctCalculator.calculateDuctCount(
                slab,
                ccm
        );

        System.out.println(ductCalculationResult.ductCount);

        WaterFlow waterFlow = new WaterFlow(20, 50, 1000, 4.187);

        final WaterFlowCalculator.Result waterFlowCalculationResult = WaterFlowCalculator.calculate(
                ductCalculationResult,
                ccm,
                waterFlow
        );

        System.out.println(waterFlowCalculationResult.flowVelocity);
        System.out.println(waterFlowCalculationResult.consumption);

        User.authorize("test", "1234");

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
