package lpr.olil.view;

import lpr.olil.model.CcmBuilder;
import lpr.olil.util.PositiveNumberValidator;

import javax.swing.*;
import java.awt.*;

public class CcmForm extends JPanel implements ValidatableForm{
    private static final int MAX_COMPONENT_HEIGHT = 20;

    private final GroupLayout layout;

    private static final int CURVED = 0;
    private static final int VERTICAL = 1;
    private static final String[] geometries = new String[]{"Криволинейная", "Вертикальная"};

    private final JLabel geometryLabel;
    private final JComboBox<String> geometrySelector;

    private final NumberField castingSpeedField;

    public CcmForm() {
        layout = new GroupLayout(this);
        setLayout(layout);

        geometryLabel = new JLabel("Геометрия");

        geometrySelector = new JComboBox<>(geometries);
        geometrySelector.setMaximumSize(new Dimension(Integer.MAX_VALUE, MAX_COMPONENT_HEIGHT));

        castingSpeedField = new NumberField("Скорость разливки (м/мин)");
        castingSpeedField.addValidator(new PositiveNumberValidator("Скорость разливки должна быть больше нуля!"));

        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(geometryLabel)
                        .addComponent(geometrySelector)
                        .addComponent(castingSpeedField)
        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addComponent(geometryLabel)
                        .addComponent(geometrySelector)
                        .addComponent(castingSpeedField)
        );

        setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                "МНЛЗ"
        ));
    }

    public CcmBuilder.Geometry getSelectedGeometryType() {
        return switch (geometrySelector.getSelectedIndex()) {
            case CURVED -> CcmBuilder.Geometry.CURVED;
            case VERTICAL -> CcmBuilder.Geometry.VERTICAL;
            default -> null;
        };
    }

    public double getCastingSpeedValue() {
        return castingSpeedField.getValue();
    }

    @Override
    public void validateForm() {
        castingSpeedField.validateField();
    }

    @Override
    public boolean isValidForm() {
        return castingSpeedField.hasValidValue();
    }

}
