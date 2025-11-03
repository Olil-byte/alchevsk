package lpr.olil.view;

import javax.swing.*;
import java.awt.*;

public class WaterFlowHelper extends JPanel {

    private GroupLayout layout;

    private NumberParameterField inletTemperatureField;

    private NumberParameterField outletTemperatureField;

    private NumberParameterField densityField;

    private NumberParameterField conductivityField;

    public WaterFlowHelper() {
        super();

        layout = new GroupLayout(this);
        setLayout(layout);

        inletTemperatureField = new NumberParameterField("Температура на входе (\u00B0C)");

        outletTemperatureField = new NumberParameterField("Температура на выходе (\u00B0C)");

        densityField = new NumberParameterField("Плотность (кг/м\u00B3)");

        conductivityField = new NumberParameterField("Теплопроводность (Дж/(кг\u22C5К))");

        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(inletTemperatureField)
                        .addComponent(outletTemperatureField)
                        .addComponent(densityField)
                        .addComponent(conductivityField)
        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addComponent(inletTemperatureField)
                        .addComponent(outletTemperatureField)
                        .addComponent(densityField)
                        .addComponent(conductivityField)
        );

        setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                "Охлаждающая жидкость"
        ));
    }
}
