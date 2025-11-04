package lpr.olil.view;

import lpr.olil.calculator.DuctCalculator;
import lpr.olil.model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.NoSuchElementException;
import java.util.Objects;

public class CoolingCalculationPanel extends JScrollPane {

    private static final int MAX_COMPONENT_HEIGHT = 20;

    private CoolingParametersPanel coolingParametersPanel;

    private JPanel content;

    private BoxLayout layout;

    private JButton calculateButton;

    private class ButtonController implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            Slab slab = createSlab();

            final WallHelper wallHelper = coolingParametersPanel.getWallHelper();

            Wall wall = null;

            try {
                final WallBuilder.Type type = wallHelper.getSelectedType();

                final double length = wallHelper.getLengthValue();
                final double activeLength = wallHelper.getActiveLengthValue();
                final double thickness = wallHelper.getThicknessValue();

                WallBuilder builder = new WallBuilder();

                wall = builder.ofType(type)
                        .withLength(length)
                        .withActiveLength(activeLength)
                        .withThickness(thickness)
                        .build();

            } catch (RuntimeException re) {
                System.out.println(re);
            }

            final DuctHelper ductHelper = coolingParametersPanel.getDuctHelper();

            Crystallizer crystallizer = null;

            try {
                final double ductDiameter = ductHelper.getDiameterValue();

                crystallizer = new Crystallizer(wall, ductDiameter);

            } catch (RuntimeException re) {
                System.out.println(re);
            }

            CcmHelper ccmHelper = coolingParametersPanel.getCcmHelper();

            Ccm ccm = null;

            try {
                final CcmBuilder.Geometry geometry = ccmHelper.getSelectedGeometryType();

                final double castingSpeed = ccmHelper.getCastingSpeedValue();

                CcmBuilder builder = new CcmBuilder();

                ccm = builder.withGeometry(geometry)
                        .withCastingSpeed(castingSpeed)
                        .withCrystallizer(crystallizer)
                        .build();
            } catch (RuntimeException re) {
                System.out.println(re);
            }

            final DuctCalculator.Result ductCalculationResult = DuctCalculator.calculateDuctCount(
                    slab,
                    Objects.requireNonNull(ccm)
            );

            ductCountField.setText(Double.toString(ductCalculationResult.ductCount));
            //distanceBetweenDuctsField.setText(Double.toString(ductCalculationResult.distanceBetweenDucts));
        }
    }

    private Slab createSlab() {
        final SlabHelper slabHelper = coolingParametersPanel.getSlabHelper();

        final double width = slabHelper.getWidthValue();
        final double length = slabHelper.getLengthValue();

        return new Slab(width, length);
    }

    ButtonController buttonController;

    JLabel ductCountLabel;
    JTextField ductCountField;

    JLabel distanceBetweenDuctsLabel;
    //JTextField distanceBetweenDuctsField;

    public CoolingCalculationPanel(CoolingParametersPanel coolingParametersPanel) {
        this.coolingParametersPanel = coolingParametersPanel;

        buttonController = new ButtonController();

        content = new JPanel();

        layout = new BoxLayout(content, BoxLayout.Y_AXIS);

        content.setLayout(layout);

        ductCountLabel = new JLabel("Количество каналов (шт.)");
        content.add(ductCountLabel);

        ductCountField = new JTextField();
        ductCountField.setMaximumSize(new Dimension(Integer.MAX_VALUE, MAX_COMPONENT_HEIGHT));
        ductCountField.setEditable(false);
        content.add(ductCountField);

//        distanceBetweenDuctsLabel = new JLabel("Расстояние между каналами (м)");
//        content.add(distanceBetweenDuctsLabel);

//        distanceBetweenDuctsField = new JTextField();
//        distanceBetweenDuctsField.setMaximumSize(new Dimension(Integer.MAX_VALUE, MAX_COMPONENT_HEIGHT));
//        content.add(distanceBetweenDuctsField);

        calculateButton = new JButton("Расчитать");
        content.add(calculateButton);

        calculateButton.addActionListener(buttonController);

        setViewportView(content);
    }
}
