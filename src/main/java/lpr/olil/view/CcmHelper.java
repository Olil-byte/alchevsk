package lpr.olil.view;

import lpr.olil.model.CcmBuilder;

import javax.swing.*;
import java.awt.*;

public class CcmHelper extends JPanel {
    private static final int MAX_COMPONENT_HEIGHT = 20;

    private GroupLayout layout;

    private static final int CURVED = 0;
    private static final int VERTICAL = 1;
    private static final String[] geometries = new String[]{"Криволинейная", "Вертикальная"};

    private JLabel geometryLabel;
    private JComboBox<String> geometrySelector;

    private NumberParameterField castingSpeedField;

    public CcmHelper() {
        layout = new GroupLayout(this);
        setLayout(layout);

        geometryLabel = new JLabel("Геометрия");

        geometrySelector = new JComboBox<>(geometries);
        geometrySelector.setMaximumSize(new Dimension(Integer.MAX_VALUE, MAX_COMPONENT_HEIGHT));

        castingSpeedField = new NumberParameterField("Скорость разливки (м/мин)");

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

}
