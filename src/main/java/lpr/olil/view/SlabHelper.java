package lpr.olil.view;

import javax.swing.*;
import java.awt.*;

public class SlabHelper extends JPanel {
    private static final int MAX_COMPONENT_HEIGHT = 20;

    private GroupLayout layout;

    private JLabel widthLabel;
    private JTextField widthField;

    private JLabel lengthLabel;
    private JTextField lengthField;

    public SlabHelper() {
        super();

        layout = new GroupLayout(this);
        setLayout(layout);

        widthLabel = new JLabel("Ширина (м)");

        widthField = new JTextField();
        widthField.setMaximumSize(new Dimension(Integer.MAX_VALUE, MAX_COMPONENT_HEIGHT));

        lengthLabel = new JLabel("Длина (м)");

        lengthField = new JTextField();
        lengthField.setMaximumSize(new Dimension(Integer.MAX_VALUE, MAX_COMPONENT_HEIGHT));

        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(widthLabel)
                        .addComponent(widthField)
                        .addComponent(lengthLabel)
                        .addComponent(lengthField)
        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addComponent(widthLabel)
                        .addComponent(widthField)
                        .addComponent(lengthLabel)
                        .addComponent(lengthField)
        );

        setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                "Сляб"
        ));
    }

    public double getWidthValue() {
        return Double.parseDouble(widthField.getText());
    }

    public double getLengthValue() {
        return Double.parseDouble(lengthField.getText());
    }
}
