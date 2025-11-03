package lpr.olil.view;

import javax.swing.*;
import java.awt.*;

public class SlabHelper extends JPanel {

    private GroupLayout layout;

    private NumberParameterField widthField;

    private NumberParameterField lengthField;

    public SlabHelper() {
        super();

        layout = new GroupLayout(this);
        setLayout(layout);

        widthField = new NumberParameterField("Ширина (м)");

        lengthField = new NumberParameterField("Длина (м)");

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
}
