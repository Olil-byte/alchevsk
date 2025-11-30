package lpr.olil.view;

import lpr.olil.calculator.DuctCalculator;
import lpr.olil.model.*;

import lpr.olil.user.User;
import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.util.Objects;

public class CoolingCalculationPanel extends JScrollPane {

    private static final int MAX_COMPONENT_HEIGHT = 20;
    private static final String DUCT_COUNT_LABEL = "Количество каналов (шт.)";
    private static final String CALCULATE_BUTTON_TEXT = "Рассчитать";

    private final CoolingParametersForm coolingParametersForm;
    private final JPanel content;
    private final JTextField ductCountField;

    private final CoolingHistoryPanel historyPanel;

    private JLabel distanceBetweenDuctsSymbolicFormulaLabel;
    private JLabel distanceBetweenDuctsEvaluatedExpressionLabel;

    private JLabel perimeterSymbolicFormulaLabel;
    private JLabel perimeterEvaluatedExpressionLabel;

    private JLabel ductCountSymbolicFormulaLabel;
    private JLabel ductCountEvaluatedExpressionLabel;

    public CoolingCalculationPanel(CoolingParametersForm coolingParametersForm, CoolingHistoryPanel historyPanel) {
        this.coolingParametersForm = Objects.requireNonNull(coolingParametersForm,
                "CoolingParametersForm cannot be null");

        this.historyPanel = historyPanel;

        this.content = createContentPanel();
        this.ductCountField = createDuctCountField();

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

    private void setupUI() {
        addDuctCountComponents();
        addCalculateButton();
        setViewportView(content);
    }

    private void prepareFormulas() {
        prepareDistanceBetweenDuctsFormula();
        preparePerimeterFormula();
        prepareDuctCountFormula();
    }

    private void prepareDistanceBetweenDuctsFormula() {
        final TeXFormula symbolicFormula =
                new TeXFormula("Z = 2 \\cdot \\sqrt{\\left(2 \\cdot l_{ст.} + \\frac{d}{2}\\right)^2 - \\left(l_{ст.} + \\frac{d}{2}\\right)^2} \\quad \\text{-- расстояние между каналами, м}");

        final TeXIcon symbolicIcon = symbolicFormula.createTeXIcon(TeXConstants.STYLE_DISPLAY, 20);

        distanceBetweenDuctsSymbolicFormulaLabel = new JLabel(symbolicIcon);
        distanceBetweenDuctsSymbolicFormulaLabel.setVisible(false);
        distanceBetweenDuctsSymbolicFormulaLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        content.add(distanceBetweenDuctsSymbolicFormulaLabel);
        content.add(Box.createRigidArea(new Dimension(0, 5)));

        distanceBetweenDuctsEvaluatedExpressionLabel = new JLabel();
        distanceBetweenDuctsEvaluatedExpressionLabel.setVisible(false);
        distanceBetweenDuctsEvaluatedExpressionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        content.add(distanceBetweenDuctsEvaluatedExpressionLabel);
        content.add(Box.createRigidArea(new Dimension(0, 10)));
    }

    private void preparePerimeterFormula() {
        final TeXFormula symbolicFormula =
                new TeXFormula("П = 2 \\cdot \\left( B + b \\right) \\quad \\text{-- периметр стенок кристаллизатора, м}");

        final TeXIcon symbolicIcon = symbolicFormula.createTeXIcon(TeXConstants.STYLE_DISPLAY, 20);

        perimeterSymbolicFormulaLabel = new JLabel(symbolicIcon);
        perimeterSymbolicFormulaLabel.setVisible(false);
        perimeterSymbolicFormulaLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        content.add(perimeterSymbolicFormulaLabel);
        content.add(Box.createRigidArea(new Dimension(0, 5)));

        perimeterEvaluatedExpressionLabel = new JLabel();
        perimeterEvaluatedExpressionLabel.setVisible(false);
        perimeterEvaluatedExpressionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        content.add(perimeterEvaluatedExpressionLabel);
        content.add(Box.createRigidArea(new Dimension(0, 10)));
    }

    private void prepareDuctCountFormula() {
        final TeXFormula symbolicFormula =
                new TeXFormula("n = \\frac{П}{Z + d} \\quad \\text{-- количество каналов, шт.}");

        final TeXIcon symbolicIcon = symbolicFormula.createTeXIcon(TeXConstants.STYLE_DISPLAY, 20);

        ductCountSymbolicFormulaLabel = new JLabel(symbolicIcon);
        ductCountSymbolicFormulaLabel.setVisible(false);
        ductCountSymbolicFormulaLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        content.add(ductCountSymbolicFormulaLabel);
        content.add(Box.createRigidArea(new Dimension(0, 5)));

        ductCountEvaluatedExpressionLabel = new JLabel();
        ductCountEvaluatedExpressionLabel.setVisible(false);
        ductCountEvaluatedExpressionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        content.add(ductCountEvaluatedExpressionLabel);
        content.add(Box.createRigidArea(new Dimension(0, 10)));
    }

    private void addDuctCountComponents() {
        JLabel label = new JLabel(DUCT_COUNT_LABEL);
        content.add(label);
        content.add(ductCountField);
        content.add(Box.createRigidArea(new Dimension(0, 10)));
    }

    private void addCalculateButton() {
        JButton button = new JButton(CALCULATE_BUTTON_TEXT);
        button.addActionListener(this::handleCalculation);
        content.add(button);
        content.add(Box.createRigidArea(new Dimension(0, 15)));
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
        hideAllFormulas();
    }

    private void performCalculation() {
        Slab slab = createSlab();
        Wall wall = createWall();
        Crystallizer crystallizer = createCrystallizer(wall);
        Ccm ccm = createCcm(crystallizer);

        DuctCalculator.Result result = DuctCalculator.calculateDuctCount(slab, ccm);
        displayResult(result);

        showSymbolicFormulas();

        showEvaluatedExpressions(result, wall, crystallizer, slab);

        WaterFlow waterFlow = createWaterFlow();

        saveToHistoryIfAuthorized(slab, wall, crystallizer, ccm, waterFlow);
    }

    private void showSymbolicFormulas() {
        distanceBetweenDuctsSymbolicFormulaLabel.setVisible(true);
        perimeterSymbolicFormulaLabel.setVisible(true);
        ductCountSymbolicFormulaLabel.setVisible(true);
    }

    private void hideAllFormulas() {
        distanceBetweenDuctsSymbolicFormulaLabel.setVisible(false);
        distanceBetweenDuctsEvaluatedExpressionLabel.setVisible(false);
        perimeterSymbolicFormulaLabel.setVisible(false);
        perimeterEvaluatedExpressionLabel.setVisible(false);
        ductCountSymbolicFormulaLabel.setVisible(false);
        ductCountEvaluatedExpressionLabel.setVisible(false);
    }

    private void showEvaluatedExpressions(DuctCalculator.Result result, Wall wall, Crystallizer crystallizer, Slab slab) {
        String distanceFormula = String.format(
                "Z = 2 \\cdot \\sqrt{\\left(2 \\cdot %s + \\frac{%s}{2}\\right)^2 - \\left(%s + \\frac{%s}{2}\\right)^2} = %s\\,м",
                formatNumber(wall.getLength()),
                formatNumber(crystallizer.getDuctDiameter()),
                formatNumber(wall.getLength()),
                formatNumber(crystallizer.getDuctDiameter()),
                formatNumber(result.distanceBetweenDucts)
        );
        displayFormula(distanceBetweenDuctsEvaluatedExpressionLabel, distanceFormula);

        String perimeterFormula = String.format(
                "П = 2 \\cdot (%s + %s) = %s\\,м",
                formatNumber(slab.getWidth()),
                formatNumber(slab.getLength()),
                formatNumber(result.perimeter)
        );
        displayFormula(perimeterEvaluatedExpressionLabel, perimeterFormula);

        String ductCountFormula = String.format(
                "n = \\frac{%s}{%s + %s} = %d\\,шт",
                formatNumber(result.perimeter),
                formatNumber(result.distanceBetweenDucts),
                formatNumber(crystallizer.getDuctDiameter()),
                result.ductCount
        );
        displayFormula(ductCountEvaluatedExpressionLabel, ductCountFormula);
    }

    private void displayFormula(JLabel label, String formula) {
        try {
            TeXFormula texFormula = new TeXFormula(formula);
            TeXIcon icon = texFormula.createTeXIcon(TeXConstants.STYLE_DISPLAY, 20);
            label.setIcon(icon);
            label.setVisible(true);
        } catch (Exception e) {
            label.setIcon(null);
            label.setText(formula.replace("\\,", " ").replace("\\", ""));
            label.setVisible(true);
        }
    }

    private String formatNumber(double value) {
        if (value == (long) value) {
            return String.format("%d", (long) value);
        } else {
            String formatted = String.format("%.3f", value);
            return formatted.replaceAll("0*$", "").replaceAll("\\.$", "");
        }
    }

    private void saveToHistoryIfAuthorized(Slab slab, Wall wall, Crystallizer crystallizer, Ccm ccm, WaterFlow waterFlow) {
        if (!User.isAuthorized()) {
            return;
        }

        Connection dbConnection = User.getDbConnection();
        String sql =
                """
                INSERT INTO alchevsk.cooling_history (
                    ccm_geometry,
                    slab_width,
                    slab_height,
                    wall_type,
                    wall_length,
                    wall_active_length,
                    wall_thickness,
                    duct_diameter,
                    inlet_temperature,
                    outlet_temperature,
                    water_density,
                    water_conductivity
                ) VALUES (?::alchevsk.ccm_geometry, ?, ?, ?::alchevsk.wall_type, ?, ?, ?, ?, ?, ?, ?, ?);
                """;

        try (PreparedStatement statement = dbConnection.prepareStatement(sql)) {
            statement.setString(1, (ccm instanceof VerticalCcm ? "vertical" : "curved"));
            statement.setDouble(2, slab.getWidth());
            statement.setDouble(3, slab.getLength());
            statement.setString(4, (wall instanceof SmoothedWall ? "smoothed" : "profiled"));
            statement.setDouble(5, wall.getLength());
            statement.setDouble(6, wall.getActiveLength());
            statement.setDouble(7, wall.getThickness());
            statement.setDouble(8, crystallizer.getDuctDiameter());
            statement.setDouble(9, waterFlow.getInletTemperature());
            statement.setDouble(10, waterFlow.getOutletTemperature());
            statement.setDouble(11, waterFlow.getDensity());
            statement.setDouble(12, waterFlow.getConductivity());
            statement.executeUpdate();

            historyPanel.refresh();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "Ошибка сохранения в историю: " + e.getMessage(),
                    "Ошибка базы данных",
                    JOptionPane.WARNING_MESSAGE);
        }
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

    private WaterFlow createWaterFlow() {
        WaterFlowForm waterFlowForm = coolingParametersForm.getWaterFlowForm();

        return new WaterFlow(
                waterFlowForm.getInletTemperatureValue(),
                waterFlowForm.getOutletTemperatureValue(),
                waterFlowForm.getDensityValue(),
                waterFlowForm.getConductivityValue()
        );
    }
}