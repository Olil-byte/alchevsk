package lpr.olil.view;

import lpr.olil.util.PositiveNumberValidator;

import javax.swing.*;
import java.awt.*;

public class SlabHelper extends JPanel {

    private GroupLayout layout;

    private NumberField widthField;

    private NumberField lengthField;

    public SlabHelper() {
        super();

        layout = new GroupLayout(this);
        setLayout(layout);

        widthField = new NumberField("Ширина (м)");
        widthField.addValidator(new PositiveNumberValidator("Ширина должна быть больше нуля!"));

        lengthField = new NumberField("Длина (м)");
        lengthField.addValidator(new PositiveNumberValidator("Длина должна быть больше нуля!"));

        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(widthField)
                        .addComponent(lengthField)
        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addComponent(widthField)
                        .addComponent(lengthField)
        );

        setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                "Сляб"
        ));
    }

    public double getWidthValue() {
        return widthField.getValue();
    }

    public double getLengthValue() {
        return lengthField.getValue();
    }

    public boolean isValidForm() {
        return widthField.hasValidValue() && lengthField.hasValidValue();
    }
}
