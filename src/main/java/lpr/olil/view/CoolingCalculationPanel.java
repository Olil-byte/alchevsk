package lpr.olil.view;

import lpr.olil.calculator.DuctCalculator;
import lpr.olil.model.*;

import lpr.olil.user.User;

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

    public CoolingCalculationPanel(CoolingParametersForm coolingParametersForm, CoolingHistoryPanel historyPanel) {
        this.coolingParametersForm = Objects.requireNonNull(coolingParametersForm,
                "CoolingParametersForm cannot be null");

        this.historyPanel = historyPanel;

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

        WaterFlow waterFlow = createWaterFlow();

        // add to history
        if (User.isAuthorized()) {
            Connection dbConnection = User.getDbConnection();
            try {
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

                java.sql.PreparedStatement statement = dbConnection.prepareStatement(sql);

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

                ResultSet results = statement.executeQuery();

                historyPanel.refresh();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
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