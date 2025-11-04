package lpr.olil.view;

import javax.swing.*;
import java.awt.*;

import lpr.olil.model.WallBuilder;
import lpr.olil.util.PositiveNumberValidator;
import lpr.olil.util.WallActiveLengthValidator;
import lpr.olil.util.WallLengthValidator;

public class WallForm extends JPanel {
    private static final int MAX_COMPONENT_HEIGHT = 20;

    private static final int SMOOTHED = 0;
    private static final int PROFILED = 1;
    private static final String[] types = new String[]{"Гладкая", "Профилированная"};

    private GroupLayout layout;

    private JLabel typeLabel;
    private JComboBox<String> typeSelector;

    private NumberField lengthField;

    private NumberField activeLengthField;

    private NumberField thicknessField;

    public WallForm() {
        super();

        layout = new GroupLayout(this);
        setLayout(layout);

        typeLabel = new JLabel("Тип");

        typeSelector = new JComboBox<>(types);
        typeSelector.setMaximumSize(new Dimension(Integer.MAX_VALUE, MAX_COMPONENT_HEIGHT));

        lengthField = new NumberField("Длина (м)");
        lengthField.addValidator(new PositiveNumberValidator("Длина должна быть больше нуля!"));

        activeLengthField = new NumberField("Активная длина (м)");
        activeLengthField.addValidator(new PositiveNumberValidator("Активная длина должна быть больше нуля!"));

        lengthField.addValidator(new WallLengthValidator(activeLengthField));
        activeLengthField.addValidator(new WallActiveLengthValidator(lengthField));

        thicknessField = new NumberField("Толщина (м)");
        thicknessField.addValidator(new PositiveNumberValidator("Толщина должна быть больше нуля!"));

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

    public boolean isValidForm() {
        return lengthField.hasValidValue() && activeLengthField.hasValidValue() && thicknessField.hasValidValue();
    }
}
