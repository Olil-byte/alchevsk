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

    private JLabel lengthLabel;
    private JTextField lengthField;

    private JLabel activeLengthLabel;
    private JTextField activeLengthField;

    private JLabel thicknessLabel;
    private JTextField thicknessField;

    public WallHelper() {
        super();

        layout = new GroupLayout(this);
        setLayout(layout);

        typeLabel = new JLabel("Тип");

        typeSelector = new JComboBox<>(types);
        typeSelector.setMaximumSize(new Dimension(Integer.MAX_VALUE, MAX_COMPONENT_HEIGHT));

        lengthLabel = new JLabel("Длина (м)");

        lengthField = new JTextField();
        lengthField.setMaximumSize(new Dimension(Integer.MAX_VALUE, MAX_COMPONENT_HEIGHT));

        activeLengthLabel = new JLabel("Активная длина (м)");

        activeLengthField = new JTextField();
        activeLengthField.setMaximumSize(new Dimension(Integer.MAX_VALUE, MAX_COMPONENT_HEIGHT));

        thicknessLabel = new JLabel("Толщина (м)");

        thicknessField = new JTextField();
        thicknessField.setMaximumSize(new Dimension(Integer.MAX_VALUE, MAX_COMPONENT_HEIGHT));

        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(typeLabel)
                        .addComponent(typeSelector)
                        .addComponent(lengthLabel)
                        .addComponent(lengthField)
                        .addComponent(activeLengthLabel)
                        .addComponent(activeLengthField)
                        .addComponent(thicknessLabel)
                        .addComponent(thicknessField)
        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addComponent(typeLabel)
                        .addComponent(typeSelector)
                        .addComponent(lengthLabel)
                        .addComponent(lengthField)
                        .addComponent(activeLengthLabel)
                        .addComponent(activeLengthField)
                        .addComponent(thicknessLabel)
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
        return Double.parseDouble(lengthField.getText());
    }

    public double getActiveLengthValue() {
        return Double.parseDouble(activeLengthField.getText());
    }

    public double getThicknessValue() {
        return Double.parseDouble(thicknessField.getText());
    }
}
