package lpr.olil.view;

import lpr.olil.calculator.DuctCalculator;
import lpr.olil.calculator.WaterFlowCalculator;
import lpr.olil.model.*;

import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lpr.olil.util.HistoryService;

public class CoolingCalculationPanel extends JScrollPane {
    private static final int MAX_COMPONENT_HEIGHT = 20;
    private static final String DUCT_COUNT_LABEL = "Количество каналов (шт.)";

    private static final String FLOW_VELOCITY_LABEL = "Скорость потока, м/с";
    private static final String CONSUMPTION_LABEL = "Расход воды, л/с";

    private static final String CALCULATE_BUTTON_TEXT = "Рассчитать";

    private final CoolingParametersForm coolingParametersForm;
    private final JPanel content;
    private final JTextField ductCountField;
    private final JTextField flowVelocityField;
    private final JTextField consuptionField;
    private final CoolingHistoryPanel historyPanel;
    private final HistoryService historyService;

    private final List<FormulaPair> formulaPairs;

    public CoolingCalculationPanel(CoolingParametersForm coolingParametersForm, CoolingHistoryPanel historyPanel) {
        this.coolingParametersForm = Objects.requireNonNull(coolingParametersForm,
                "CoolingParametersForm cannot be null");
        this.historyPanel = historyPanel;
        this.historyService = new HistoryService();
        this.formulaPairs = new ArrayList<>();

        this.content = createContentPanel();
        this.ductCountField = createDuctCountField();
        this.flowVelocityField = createFlowVelocityField();
        this.consuptionField = createConsumptionField();

        setupUI();
        prepareFormulas();
    }

    private JPanel createContentPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return panel;
    }

    private JTextField createDuctCountField() {
        JTextField field = new JTextField();
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, MAX_COMPONENT_HEIGHT));
        field.setEditable(false);
        return field;
    }

    private JTextField createFlowVelocityField() {
        JTextField field = new JTextField();
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, MAX_COMPONENT_HEIGHT));
        field.setEditable(false);
        return field;
    }

    private JTextField createConsumptionField() {
        JTextField field = new JTextField();
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, MAX_COMPONENT_HEIGHT));
        field.setEditable(false);
        return field;
    }

    private void setupUI() {
        addDuctCountComponents();

        addFlowVelocityComponents();
        addConsumptionComponents();

        addCalculateButton();
        setViewportView(content);
    }

    private void prepareFormulas() {
        formulaPairs.add(createFormulaPair(
                "Z = 2 \\cdot \\sqrt{\\left(2 \\cdot l_{ст.} + \\frac{d}{2}\\right)^2 - \\left(l_{ст.} + \\frac{d}{2}\\right)^2} \\text{-- расстояние между каналами, м}",
                FormulaType.DISTANCE_BETWEEN_DUCTS
        ));

        formulaPairs.add(createFormulaPair(
                "П = 2 \\cdot \\left( B + b \\right) \\text{-- периметр стенок кристаллизатора, м}",
                FormulaType.PERIMETER
        ));

        formulaPairs.add(createFormulaPair(
                "n = \\frac{П}{Z + d} \\text{-- количество каналов, шт.}",
                FormulaType.DUCT_COUNT
        ));

        formulaPairs.add(createFormulaPair(
                "W_{в} = \\frac{2 A \\nu m (B + b) \\cdot l_{а}}{n \\left(\\pi d^2/4\\right) \\cdot C \\cdot \\rho_{в} \\left(T_{вых} - Т_{вх}\\right)} \\text{-- скорость течения, м/c}",
                FormulaType.FLOW_VELOCITY
        ));

        formulaPairs.add(createFormulaPair(
                "G_{в} = \\left(\\pi d^2 / 4\\right) \\cdot n \\cdot W_{в} \\text{-- расход воды, л/с}",
                FormulaType.CONSUMPTION
        ));
    }

    private FormulaPair createFormulaPair(String symbolicFormula, FormulaType type) {
        JLabel symbolicLabel = FormulaDisplayHelper.createFormulaLabel(symbolicFormula, false);
        content.add(symbolicLabel);
        content.add(Box.createRigidArea(new Dimension(0, 5)));

        JLabel evaluatedLabel = new JLabel();
        evaluatedLabel.setVisible(false);
        evaluatedLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        content.add(evaluatedLabel);
        content.add(Box.createRigidArea(new Dimension(0, 10)));

        return new FormulaPair(symbolicLabel, evaluatedLabel, type);
    }

    private void addDuctCountComponents() {
        JLabel label = new JLabel(DUCT_COUNT_LABEL);
        content.add(label);
        content.add(ductCountField);
        content.add(Box.createRigidArea(new Dimension(0, 10)));
    }

    private void addFlowVelocityComponents() {
        JLabel label = new JLabel(FLOW_VELOCITY_LABEL);
        content.add(label);
        content.add(flowVelocityField);
        content.add(Box.createRigidArea(new Dimension(0, 10)));
    }

    private void addConsumptionComponents() {
        JLabel label = new JLabel(CONSUMPTION_LABEL);
        content.add(label);
        content.add(consuptionField);
        content.add(Box.createRigidArea(new Dimension(0, 10)));
    }

    private void addCalculateButton() {
        JButton button = new JButton(CALCULATE_BUTTON_TEXT);
        button.addActionListener(this::handleCalculation);
        content.add(button);
        content.add(Box.createRigidArea(new Dimension(0, 15)));
    }

    private void handleCalculation(ActionEvent event) {
        if (!validateForm()) {
            return;
        }

        try {
            CalculationResult result = performCalculation();
            displayResults(result);
            saveToHistory(result);
        } catch (Exception e) {
            handleCalculationError(e);
        }
    }

    private boolean validateForm() {
        coolingParametersForm.validateForm();

        if (!coolingParametersForm.isValidForm()) {
            showValidationError();
            return false;
        }
        return true;
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
        flowVelocityField.setText("");
        consuptionField.setText("");
        hideAllFormulas();
    }

    private CalculationResult performCalculation() {
        Slab slab = createSlab();
        Wall wall = createWall();
        Crystallizer crystallizer = createCrystallizer(wall);
        Ccm ccm = createCcm(crystallizer);
        WaterFlow waterFlow = createWaterFlow();

        DuctCalculator.Result ductResult = DuctCalculator.calculateDuctCount(slab, ccm);
        WaterFlowCalculator.Result waterResult = WaterFlowCalculator.calculate(ductResult, ccm, waterFlow);

        return new CalculationResult(slab, wall, crystallizer, ccm, waterFlow, ductResult, waterResult);
    }

    private void displayResults(CalculationResult result) {
        ductCountField.setText(Integer.toString(result.ductResult.ductCount));

        flowVelocityField.setText(Double.toString(result.waterResult.flowVelocity));
        consuptionField.setText(Double.toString(result.waterResult.consumption));

        showSymbolicFormulas();
        updateEvaluatedFormulas(result);
    }

    private void showSymbolicFormulas() {
        formulaPairs.forEach(pair -> pair.symbolicLabel.setVisible(true));
    }

    private void updateEvaluatedFormulas(CalculationResult result) {
        for (FormulaPair pair : formulaPairs) {
            String formula = buildEvaluatedFormula(pair.type, result);
            FormulaDisplayHelper.updateFormulaLabel(pair.evaluatedLabel, formula);
        }
    }

    private String buildEvaluatedFormula(FormulaType type, CalculationResult result) {
        return switch (type) {
            case DISTANCE_BETWEEN_DUCTS -> FormulaBuilder.buildDistanceFormula(
                    result.wall, result.crystallizer, result.ductResult.distanceBetweenDucts);
            case PERIMETER -> FormulaBuilder.buildPerimeterFormula(
                    result.slab, result.ductResult.perimeter);
            case DUCT_COUNT -> FormulaBuilder.buildDuctCountFormula(
                    result.ductResult.perimeter, result.ductResult.distanceBetweenDucts,
                    result.crystallizer.getDuctDiameter(), result.ductResult.ductCount);
            case FLOW_VELOCITY -> FormulaBuilder.buildFlowVelocityFormula(
                    result.ductResult, result.wall, result.crystallizer,
                    result.slab, result.ccm, result.waterFlow, result.waterResult);
            case CONSUMPTION -> FormulaBuilder.buildConsumptionFormula(
                    result.crystallizer, result.ductResult, result.waterResult);
        };
    }

    private void hideAllFormulas() {
        formulaPairs.forEach(pair -> {
            pair.symbolicLabel.setVisible(false);
            pair.evaluatedLabel.setVisible(false);
        });
    }

    private void saveToHistory(CalculationResult result) {
        historyService.saveToHistoryIfAuthorized(
                result.slab, result.wall, result.crystallizer,
                result.ccm, result.waterFlow, this, historyPanel::refresh
        );
    }

    // Методы создания моделей остаются без изменений
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

    private WaterFlow createWaterFlow() {
        WaterFlowForm waterFlowForm = coolingParametersForm.getWaterFlowForm();
        return new WaterFlow(
                waterFlowForm.getInletTemperatureValue(),
                waterFlowForm.getOutletTemperatureValue(),
                waterFlowForm.getDensityValue(),
                waterFlowForm.getConductivityValue()
        );
    }

    // Вспомогательные классы
    private static class FormulaPair {
        final JLabel symbolicLabel;
        final JLabel evaluatedLabel;
        final FormulaType type;

        FormulaPair(JLabel symbolicLabel, JLabel evaluatedLabel, FormulaType type) {
            this.symbolicLabel = symbolicLabel;
            this.evaluatedLabel = evaluatedLabel;
            this.type = type;
        }
    }

    private static class CalculationResult {
        final Slab slab;
        final Wall wall;
        final Crystallizer crystallizer;
        final Ccm ccm;
        final WaterFlow waterFlow;
        final DuctCalculator.Result ductResult;
        final WaterFlowCalculator.Result waterResult;

        CalculationResult(Slab slab, Wall wall, Crystallizer crystallizer, Ccm ccm,
                          WaterFlow waterFlow, DuctCalculator.Result ductResult,
                          WaterFlowCalculator.Result waterResult) {
            this.slab = slab;
            this.wall = wall;
            this.crystallizer = crystallizer;
            this.ccm = ccm;
            this.waterFlow = waterFlow;
            this.ductResult = ductResult;
            this.waterResult = waterResult;
        }
    }

    private enum FormulaType {
        DISTANCE_BETWEEN_DUCTS,
        PERIMETER,
        DUCT_COUNT,
        FLOW_VELOCITY,
        CONSUMPTION
    }
}