package lpr.olil.view;

import javax.swing.*;
import java.awt.*;

import lpr.olil.model.WallBuilder;

public class WallHelper extends JPanel {
    private static final int MAX_COMPONENT_HEIGHT = 20;

    private static final int SMOOTHED = 0;
    private static final int PROFILED = 1;
    private static final String[] types = new String[]{"Гладкая", "Профилированная"};

    private GroupLayout layout;

    private JLabel typeLabel;
    private JComboBox<String> typeSelector;

    private NumberParameterField lengthField;

    private NumberParameterField activeLengthField;

    private NumberParameterField thicknessField;

    public WallHelper() {
        super();

        layout = new GroupLayout(this);
        setLayout(layout);

        typeLabel = new JLabel("Тип");

        typeSelector = new JComboBox<>(types);
        typeSelector.setMaximumSize(new Dimension(Integer.MAX_VALUE, MAX_COMPONENT_HEIGHT));

        lengthField = new NumberParameterField("Длина (м)");

        activeLengthField = new NumberParameterField("Активная длина (м)");

        thicknessField = new NumberParameterField("Толщина (м)");

        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(typeLabel)
                        .addComponent(typeSelector)
                        .addComponent(lengthField)
                        .addComponent(activeLengthField)
                        .addComponent(thicknessField)
        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addComponent(typeLabel)
                        .addComponent(typeSelector)
                        .addComponent(lengthField)
                        .addComponent(activeLengthField)
                        .addComponent(thicknessField)
        );

        setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                "Стенка"
        ));
    }

    public WallBuilder.Type getSelectedType() {
        return switch (typeSelector.getSelectedIndex()) {
            case SMOOTHED -> WallBuilder.Type.SMOOTHED;
            case PROFILED -> WallBuilder.Type.PROFILED;
            default -> null;
        };
    }

    public double getLengthValue() {
        return lengthField.getValue();
    }

    public double getActiveLengthValue() {
        return activeLengthField.getValue();
    }

    public double getThicknessValue() {
        return thicknessField.getValue();
    }
}
