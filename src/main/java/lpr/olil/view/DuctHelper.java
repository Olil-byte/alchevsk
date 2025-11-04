package lpr.olil.view;

import lpr.olil.util.PositiveNumberValidator;

import javax.swing.*;
import java.awt.*;

public class DuctHelper extends JPanel {
    private static final int MAX_COMPONENT_HEIGHT = 20;

    private GroupLayout layout;

    private NumberField diameterField;

    public DuctHelper() {
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

    public boolean isValidForm() {
        return diameterField.hasValidValue();
    }
}
