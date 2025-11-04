package lpr.olil.view;

import lpr.olil.util.PositiveNumberValidator;

import javax.swing.*;
import java.awt.*;

public class DuctForm extends JPanel implements ValidatableForm {
    private static final int MAX_COMPONENT_HEIGHT = 20;

    private final GroupLayout layout;

    private final NumberField diameterField;

    public DuctForm() {
        super();

        layout = new GroupLayout(this);
        setLayout(layout);

        diameterField = new NumberField("Диаметр (м)");
        diameterField.addValidator(new PositiveNumberValidator("Диаметр должен быть больше нуля!"));

        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(diameterField)
        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addComponent(diameterField)
        );

        setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                "Каналы"
        ));
    }

    public double getDiameterValue() {
        return diameterField.getValue();
    }

    @Override
    public void validateForm() {
        diameterField.validateField();
    }

    @Override
    public boolean isValidForm() {
        return diameterField.hasValidValue();
    }
}
