package lpr.olil.view;

import javax.swing.*;
import java.awt.*;

public class DuctHelper extends JPanel {
    private static final int MAX_COMPONENT_HEIGHT = 20;

    private GroupLayout layout;

    private JLabel diameterLabel;
    private JTextField diameterField;

    public DuctHelper() {
        super();

        layout = new GroupLayout(this);
        setLayout(layout);

        diameterLabel = new JLabel("Диаметр (м)");

        diameterField = new JTextField();
        diameterField.setMaximumSize(new Dimension(Integer.MAX_VALUE, MAX_COMPONENT_HEIGHT));

        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(diameterLabel)
                        .addComponent(diameterField)
        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addComponent(diameterLabel)
                        .addComponent(diameterField)
        );

        setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                "Каналы"
        ));
    }

    public double getDiameterValue() {
        return Double.parseDouble(diameterField.getText());
    }
}
