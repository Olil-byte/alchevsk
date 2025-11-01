package lpr.olil.view;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class CcmHelper extends JPanel {
    private static final int MAX_COMPONENT_HEIGHT = 20;

    private GroupLayout layout;

    private static final int CURVED = 0;
    private static final int VERTICAL = 1;
    private static final String[] geometries = new String[]{"Криволинейная", "Вертикальная"};

    private JLabel geometryLabel;
    private JComboBox<String> geometrySelector;

    private JLabel castingSpeedLabel;
    private JTextField castingSpeedField;

    public CcmHelper() {
        layout = new GroupLayout(this);
        setLayout(layout);

        geometryLabel = new JLabel("Геометрия");

        geometrySelector = new JComboBox<>(geometries);
        geometrySelector.setMaximumSize(new Dimension(Integer.MAX_VALUE, MAX_COMPONENT_HEIGHT));

        castingSpeedLabel = new JLabel("Скорость разливки (м/мин)");
        castingSpeedField = new JTextField();
        castingSpeedField.setMaximumSize(new Dimension(Integer.MAX_VALUE, MAX_COMPONENT_HEIGHT));

        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(geometryLabel)
                        .addComponent(geometrySelector)
                        .addComponent(castingSpeedLabel)
                        .addComponent(castingSpeedField)
        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addComponent(geometryLabel)
                        .addComponent(geometrySelector)
                        .addComponent(castingSpeedLabel)
                        .addComponent(castingSpeedField)
        );

        setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                "МНЛЗ"
        ));
    }

}
