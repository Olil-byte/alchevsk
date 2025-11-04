package lpr.olil.view;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.Optional;

public class NumberParameterField extends JPanel {

    private static final int FIELD_HEIGHT = 20;
    private static final int ICON_SIZE = 16;

    private static final Color ERROR_HIGHLIGHT = new Color(255, 220, 220);
    private static final String ERROR_TOOLTIP = "Введённое значение не является числом";

    private static final Icon ERROR_ICON = getErrorIconInstance();

    BoxLayout layout;

    private JPanel labelPanel;

    private final JLabel label;
    private final JLabel icon;

    private final JTextField textField;

    private final Color initialBackground;

    private Optional<Double> value;

    private class FieldController implements DocumentListener {

        @Override
        public void insertUpdate(DocumentEvent event) {
            handleInputChange();
        }

        @Override
        public void removeUpdate(DocumentEvent event) {
            handleInputChange();
        }

        @Override
        public void changedUpdate(DocumentEvent event) {
            handleInputChange();
        }

        private void handleInputChange() {
            try {
                value = Optional.of(Double.parseDouble(textField.getText()));

                resetAppearance();

            } catch (NumberFormatException exception){
                value = Optional.empty();

                setErrorAppearance();
            }
        }

        private void resetAppearance() {
            if (textField.isBackgroundSet()) {
                SwingUtilities.invokeLater(() -> {
                    textField.setBackground(initialBackground);
                    setToolTipText(null);
                    icon.setVisible(false);
                });
            }
        }

        private void setErrorAppearance() {
            SwingUtilities.invokeLater(() -> {
                textField.setBackground(ERROR_HIGHLIGHT);
                setToolTipText(ERROR_TOOLTIP);
                textField.setToolTipText(ERROR_TOOLTIP);
                icon.setVisible(true);
            });
        }
    }

    private final FieldController controller;

    public NumberParameterField(String labelText) {
        layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(layout);

        labelPanel = new JPanel();
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.X_AXIS));

        label = new JLabel(labelText);
        labelPanel.add(label);

        labelPanel.add(Box.createHorizontalGlue());

        icon = new JLabel(ERROR_ICON);
        icon.setVerticalAlignment(SwingConstants.CENTER);
        icon.setVisible(false);

        icon.setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));

        labelPanel.add(icon);

        add(labelPanel);

        textField = new JTextField();
        textField.setMaximumSize(new Dimension(Integer.MAX_VALUE, FIELD_HEIGHT));
        initialBackground = textField.getBackground();
        add(textField);

        controller = new FieldController();

        textField.getDocument().addDocumentListener(controller);

        value = Optional.empty();
    }

    private static Icon getErrorIconInstance() {
        final ImageIcon errorIcon = (ImageIcon)UIManager.getIcon("OptionPane.errorIcon");

        final Image image = errorIcon.getImage();
        final Image scaledImage = image.getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_SMOOTH);

        return new ImageIcon(scaledImage);
    }

    public double getValue() {
        return value.get();
    }
}