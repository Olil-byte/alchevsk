package lpr.olil.view;

import javax.swing.*;
import java.awt.*;

public class WaterFlowHelper extends JPanel {
    private static final int MAX_COMPONENT_HEIGHT = 20;

    private GroupLayout layout;

    private JLabel inletTemperatureLabel;
    private JTextField inletTemperatureField;

    private JLabel outletTemperatureLabel;
    private JTextField outletTemperatureField;

    private JLabel densityLabel;
    private JTextField densityField;

    private JLabel conductivityLabel;
    private JTextField conductivityField;

    public WaterFlowHelper() {
        super();

        layout = new GroupLayout(this);
        setLayout(layout);

        inletTemperatureLabel = new JLabel("Температура на входе (\u00B0C)");

        inletTemperatureField = new JTextField();
        inletTemperatureField.setMaximumSize(new Dimension(Integer.MAX_VALUE, MAX_COMPONENT_HEIGHT));

        outletTemperatureLabel = new JLabel("Температура на выходе (\u00B0C)");

        outletTemperatureField = new JTextField();
        outletTemperatureField.setMaximumSize(new Dimension(Integer.MAX_VALUE, MAX_COMPONENT_HEIGHT));

        densityLabel = new JLabel("Плотность (кг/м\u00B3)");

        densityField = new JTextField();
        densityField.setMaximumSize(new Dimension(Integer.MAX_VALUE, MAX_COMPONENT_HEIGHT));

        conductivityLabel = new JLabel("Теплопроводность (Дж/(кг\u22C5К))");

        conductivityField = new JTextField();
        conductivityField.setMaximumSize(new Dimension(Integer.MAX_VALUE, MAX_COMPONENT_HEIGHT));

        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(inletTemperatureLabel)
                        .addComponent(inletTemperatureField)
                        .addComponent(outletTemperatureLabel)
                        .addComponent(outletTemperatureField)
                        .addComponent(densityLabel)
                        .addComponent(densityField)
                        .addComponent(conductivityLabel)
                        .addComponent(conductivityField)
        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addComponent(inletTemperatureLabel)
                        .addComponent(inletTemperatureField)
                        .addComponent(outletTemperatureLabel)
                        .addComponent(outletTemperatureField)
                        .addComponent(densityLabel)
                        .addComponent(densityField)
                        .addComponent(conductivityLabel)
                        .addComponent(conductivityField)
        );

        setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                "Охлаждающая жидкость"
        ));
    }
}
