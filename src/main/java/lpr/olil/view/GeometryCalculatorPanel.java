package lpr.olil.view;

import lpr.olil.calculator.DuctCalculator;

import lpr.olil.calculator.WaterFlowCalculator;
import lpr.olil.model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GeometryCalculatorPanel extends JPanel {

    private class ParameterField extends JPanel {
        private BoxLayout layout;

        private JLabel label;
        private JTextField field;

        public ParameterField(String title) {
            layout = new BoxLayout(this, BoxLayout.Y_AXIS);
            setLayout(layout);

            label = new JLabel(title);
            add(label);

            field = new JTextField();
            field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));
            add(field);
        }

        public String getText() {
            return field.getText();
        }
    }

    private class CalculateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println(wallLengthField.getText());
            Wall wall = null;
            try {
                final double length = Double.parseDouble(wallLengthField.getText());
                System.out.println(length);
                final double activeLength = Double.parseDouble(wallActiveLengthField.getText());
                final double thickness = Double.parseDouble(wallActiveLengthField.getText());

                wall = new SmoothedWall(length, activeLength, thickness);
            } catch (RuntimeException exception) {

            }

            Slab slab = null;
            try {
                final double width = Double.parseDouble(slabWidthField.getText());
                final double height = Double.parseDouble(slabHeightField.getText());

                slab = new Slab(width, height);
            } catch (InvalidSlabException exception) {

            }

            double ductDiameter = 0.0;
            try {
                ductDiameter = Double.parseDouble(ductDiameterField.getText());
            } catch (RuntimeException exception) {

            }

            double castingSpeed = 0.0;
            try {
                castingSpeed = Double.parseDouble(castingSpeedField.getText());
            } catch (RuntimeException exception) {

            }

            Crystallizer crystallizer = new CurvedCrystallizer(wall, ductDiameter, castingSpeed);

            final DuctCalculator.Result ductCalculationResult = DuctCalculator.calculateDuctCount(
                    slab,
                    crystallizer
            );
//            WaterFlowCalculator.calculateWaterFlow(
//                    ductCalculationResult,
//                    crystallizer
//                    );

            ductCountField.field.setText(Integer.toString(ductCalculationResult.ductCount));
        }
    }

    private BoxLayout layout;

    private ParameterField ductDiameterField;

    private ParameterField slabWidthField;
    private ParameterField slabHeightField;

    private ParameterField wallLengthField;
    private ParameterField wallActiveLengthField;
    private ParameterField wallThicknessField;

    private ParameterField ductCountField;

    private ParameterField castingSpeedField;

    private CalculateButtonListener calculateBtnListener;

    public GeometryCalculatorPanel() {
        layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(layout);

        ductDiameterField = new ParameterField("Диаметр каналов (м)");
        add(ductDiameterField);

        slabWidthField = new ParameterField("Ширина сляба (м)");
        add(slabWidthField);
        slabHeightField = new ParameterField("Толщина сляба (м)");
        add(slabHeightField);

        wallLengthField = new ParameterField("Длина стенки (м)");
        add(wallLengthField);
        wallActiveLengthField = new ParameterField("Длина активной зоны (м)");
        add(wallActiveLengthField);
        wallThicknessField = new ParameterField("Толщина стенки (м)");
        add(wallThicknessField);

        castingSpeedField = new ParameterField("Скорость разливки (м/мин)");

        JButton calculateBtn = new JButton("Рассчитать");
        calculateBtnListener = new CalculateButtonListener();
        calculateBtn.addActionListener(calculateBtnListener);
        add(calculateBtn);

        ParameterField ductCountField = new ParameterField("Количество каналов (шт.)");
        ductCountField.field.setEditable(false);
        add(ductCountField);
    }
}
