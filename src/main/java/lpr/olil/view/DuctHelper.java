package lpr.olil.view;

import javax.swing.*;
import java.awt.*;

public class DuctHelper extends JPanel {
    private static final int MAX_COMPONENT_HEIGHT = 20;

    private GroupLayout layout;

    private NumberParameterField diameterField;

    public DuctHelper() {
        super();

        layout = new GroupLayout(this);
        setLayout(layout);

        diameterField = new NumberParameterField("Диаметр (м)");

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
}
