package lpr.olil.view;

import lpr.olil.util.NumberValidator;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.Optional;

public class NumberField extends JPanel {

    private static final int FIELD_HEIGHT = 20;
    private static final int ICON_SIZE = 16;
    private static final Color ERROR_HIGHLIGHT = new Color(255, 220, 220);
    private static final String NAN_ERROR_TOOLTIP = "Введённое значение не является числом!";
    private static final Icon ERROR_ICON = createErrorIcon();

    private final ArrayList<NumberValidator> validators;
    private final StringBuilder tooltipBuilder;

    private boolean isValidValue;

    private final JLabel label;
    private final JLabel icon;
    private final JTextField textField;

    private final Color initialBackground;
    private Optional<Double> value;

    public NumberField(String labelText) {
        initializeLayout();
        this.label = createLabel(labelText);
        this.icon = createErrorIconLabel();
        this.textField = createTextField();
        this.initialBackground = textField.getBackground();
        this.value = Optional.empty();

        this.validators = new ArrayList<>();
        this.tooltipBuilder = new StringBuilder();

        this.isValidValue = false;

        this.textField.getDocument().addDocumentListener(
                new DocumentListener() {
                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        validateField();
                    }
                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        validateField();
                    }

                    @Override
                    public void changedUpdate(DocumentEvent e) {
                        validateField();
                    }
                }
        );

        setupUI();
    }

    private void initializeLayout() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    private JLabel createLabel(String labelText) {
        return new JLabel(labelText);
    }

    private JLabel createErrorIconLabel() {
        JLabel iconLabel = new JLabel(ERROR_ICON);
        iconLabel.setVerticalAlignment(SwingConstants.CENTER);
        iconLabel.setVisible(false);
        iconLabel.setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));
        return iconLabel;
    }

    private JTextField createTextField() {
        JTextField field = new JTextField();
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, FIELD_HEIGHT));
        return field;
    }

    private void setupUI() {
        JPanel labelPanel = createLabelPanel();
        add(labelPanel);
        add(textField);
    }

    private JPanel createLabelPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        panel.add(label);
        panel.add(Box.createHorizontalGlue());
        panel.add(icon);

        return panel;
    }

    private static Icon createErrorIcon() {
        ImageIcon errorIcon = (ImageIcon) UIManager.getIcon("OptionPane.errorIcon");
        Image image = errorIcon.getImage();
        Image scaledImage = image.getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    public double getValue() throws ValidationException {
        return value.orElseThrow(() -> new ValidationException(textField.getText()));
    }

    public void addValidator(NumberValidator validator) {
        validators.add(validator);
    }

    public void validateField() {
        resetAppearance();

        try {
            value = Optional.of(Double.parseDouble(textField.getText()));
        } catch (NumberFormatException exception) {
            value = Optional.empty();
        }

        isValidValue = value.isPresent();

        if (value.isPresent()) {
            tooltipBuilder.setLength(0);

            for (NumberValidator validator : validators) {
                if (!validator.isValid(value.get())) {
                    isValidValue = false;

                    tooltipBuilder.append("ОШИБКА: ");
                    tooltipBuilder.append(validator.getMessage());
                    tooltipBuilder.append('\n');
                }
            }
        }

        if (!isValidValue) {
            setErrorAppearance();
        }
    }

    public void resetAppearance() {
        if (textField.isBackgroundSet()) {
            SwingUtilities.invokeLater(() -> {
                textField.setBackground(initialBackground);
                setToolTipText(null);
                textField.setToolTipText(null);
                icon.setVisible(false);
            });
        }
    }

    public void setErrorAppearance() {
        SwingUtilities.invokeLater(() -> {
            textField.setBackground(ERROR_HIGHLIGHT);
            if (value.isEmpty()) {
                setToolTipText(NAN_ERROR_TOOLTIP);
                textField.setToolTipText(NAN_ERROR_TOOLTIP);
            } else {
                setToolTipText(tooltipBuilder.toString());
                textField.setToolTipText(tooltipBuilder.toString());
            }
            icon.setVisible(true);
        });
    }

    public boolean hasValidValue() {
        return value.isPresent();
    }

    public boolean hasInvalidValue() {
        return value.isEmpty();
    }

    public static class ValidationException extends RuntimeException {
        private final String inputValue;

        public ValidationException(String inputValue) {
            super("The value '" + inputValue + "' cannot be converted to a number");
            this.inputValue = inputValue;
        }

        public String getInputValue() {
            return inputValue;
        }
    }
}