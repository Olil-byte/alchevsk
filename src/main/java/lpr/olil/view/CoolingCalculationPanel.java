package lpr.olil.view;

import lpr.olil.calculator.DuctCalculator;
import lpr.olil.model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Objects;

public class CoolingCalculationPanel extends JScrollPane {

    private static final int MAX_COMPONENT_HEIGHT = 20;
    private static final String DUCT_COUNT_LABEL = "Количество каналов (шт.)";
    private static final String CALCULATE_BUTTON_TEXT = "Рассчитать";

    private final CoolingParametersForm coolingParametersForm;
    private final JPanel content;
    private final JTextField ductCountField;

    public CoolingCalculationPanel(CoolingParametersForm coolingParametersForm) {
        this.coolingParametersForm = Objects.requireNonNull(coolingParametersForm,
                "CoolingParametersForm cannot be null");

        this.content = createContentPanel();
        this.ductCountField = createDuctCountField();

        setupUI();
    }

    private JPanel createContentPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        return panel;
    }

    private JTextField createDuctCountField() {
        JTextField field = new JTextField();
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, MAX_COMPONENT_HEIGHT));
        field.setEditable(false);
        return field;
    }

    private void setupUI() {
        addDuctCountComponents();
        addCalculateButton();
        setViewportView(content);
    }

    private void addDuctCountComponents() {
        JLabel label = new JLabel(DUCT_COUNT_LABEL);
        content.add(label);
        content.add(ductCountField);
    }

    private void addCalculateButton() {
        JButton button = new JButton(CALCULATE_BUTTON_TEXT);
        button.addActionListener(this::handleCalculation);
        content.add(button);
    }

    private void handleCalculation(ActionEvent event) {
        coolingParametersForm.validateForm();

        if (!coolingParametersForm.isValidForm()) {
            showValidationError();
            return;
        }

        try {
            performCalculation();
        } catch (Exception e) {
            handleCalculationError(e);
        }
    }

    private void showValidationError() {
        JOptionPane.showMessageDialog(this,
                "Пожалуйста, заполните все поля корректно",
                "Ошибка валидации",
                JOptionPane.ERROR_MESSAGE);
    }

    private void handleCalculationError(Exception e) {
        String errorMessage = String.format("Ошибка при расчете: %s", e.getMessage());
        JOptionPane.showMessageDialog(this,
                errorMessage,
                "Ошибка расчета",
                JOptionPane.ERROR_MESSAGE);
        ductCountField.setText("");
    }

    private void performCalculation() {
        Slab slab = createSlab();
        Wall wall = createWall();
        Crystallizer crystallizer = createCrystallizer(wall);
        Ccm ccm = createCcm(crystallizer);

        DuctCalculator.Result result = DuctCalculator.calculateDuctCount(slab, ccm);
        displayResult(result);
    }

    private void displayResult(DuctCalculator.Result result) {
        ductCountField.setText(Integer.toString(result.ductCount));
    }

    private Slab createSlab() {
        SlabForm slabForm = coolingParametersForm.getSlabForm();
        return new Slab(slabForm.getWidthValue(), slabForm.getLengthValue());
    }

    private Wall createWall() {
        WallForm wallForm = coolingParametersForm.getWallForm();

        return new WallBuilder()
                .ofType(wallForm.getSelectedType())
                .withLength(wallForm.getLengthValue())
                .withActiveLength(wallForm.getActiveLengthValue())
                .withThickness(wallForm.getThicknessValue())
                .build();
    }

    private Crystallizer createCrystallizer(Wall wall) {
        DuctForm ductForm = coolingParametersForm.getDuctForm();
        return new Crystallizer(wall, ductForm.getDiameterValue());
    }

    private Ccm createCcm(Crystallizer crystallizer) {
        CcmForm ccmForm = coolingParametersForm.getCcmForm();

        return new CcmBuilder()
                .withGeometry(ccmForm.getSelectedGeometryType())
                .withCastingSpeed(ccmForm.getCastingSpeedValue())
                .withCrystallizer(crystallizer)
                .build();
    }
}