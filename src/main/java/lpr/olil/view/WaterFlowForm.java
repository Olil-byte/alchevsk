package lpr.olil.view;

import lpr.olil.util.PositiveNumberValidator;

import javax.swing.*;
import java.awt.*;

public class WaterFlowForm extends JPanel implements ValidatableForm {

    private final GroupLayout layout;

    private final NumberField inletTemperatureField;

    private final NumberField outletTemperatureField;

    private final NumberField densityField;

    private final NumberField conductivityField;

    public WaterFlowForm() {
        super();

        layout = new GroupLayout(this);
        setLayout(layout);

        inletTemperatureField = new NumberField("Температура на входе (\u00B0C)");
        inletTemperatureField.addValidator(new PositiveNumberValidator("Температура на входе должна быть больше нуля!"));

        outletTemperatureField = new NumberField("Температура на выходе (\u00B0C)");
        outletTemperatureField.addValidator(new PositiveNumberValidator("Температура на выходе должна быть больше нуля!"));

        densityField = new NumberField("Плотность (кг/м\u00B3)");
        outletTemperatureField.addValidator(new PositiveNumberValidator("Плотность должна быть больше нуля!"));

        conductivityField = new NumberField("Теплопроводность (Дж/(кг\u22C5К))");
        conductivityField.addValidator(new PositiveNumberValidator("Теплопроводность должна быть больше нуля!"));

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

    public double getInletTemperatureValue() {
        return inletTemperatureField.getValue();
    }

    public double getOutletTemperatureValue() {
        return outletTemperatureField.getValue();
    }

    public double getConductivityValue() {
        return conductivityField.getValue();
    }

    public double getDensityValue() {
        return densityField.getValue();
    }

    @Override
    public void validateForm() {
        inletTemperatureField.validateField();
        outletTemperatureField.validateField();
        conductivityField.validateField();
        densityField.validateField();
    }

    @Override
    public boolean isValidForm() {
        return inletTemperatureField.hasValidValue() && outletTemperatureField.hasInvalidValue() && conductivityField.hasValidValue() && densityField.hasValidValue();
    }
}
