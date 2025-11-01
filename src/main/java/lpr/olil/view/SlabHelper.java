package lpr.olil.view;

import javax.swing.*;
import java.awt.*;

public class SlabHelper extends JPanel {
    private static final int MAX_COMPONENT_HEIGHT = 20;

    private GroupLayout layout;

    private JLabel widthLabel;
    private JTextField widthField;

    private JLabel heightLabel;
    private JTextField heightField;

    public SlabHelper() {
        super();

        layout = new GroupLayout(this);
        setLayout(layout);

        widthLabel = new JLabel("Ширина (м)");

        widthField = new JTextField();
        widthField.setMaximumSize(new Dimension(Integer.MAX_VALUE, MAX_COMPONENT_HEIGHT));

        heightLabel = new JLabel("Длина (м)");

        heightField = new JTextField();
        heightField.setMaximumSize(new Dimension(Integer.MAX_VALUE, MAX_COMPONENT_HEIGHT));

        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(widthLabel)
                        .addComponent(widthField)
                        .addComponent(heightLabel)
                        .addComponent(heightField)
        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addComponent(widthLabel)
                        .addComponent(widthField)
                        .addComponent(heightLabel)
                        .addComponent(heightField)
        );

        setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                "Сляб"
        ));
    }
}
