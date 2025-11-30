package lpr.olil.view;

import lpr.olil.user.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CoolingHistoryPanel extends JPanel {
    private static final int DATE = 0;
    private static final int GEOMETRY = 1;
    private static final int SLAB_WIDTH = 2;
    private static final int SLAB_LENGTH = 3;
    private static final int WALL_TYPE = 4;
    private static final int WALL_LENGTH = 5;
    private static final int WALL_ACTIVE_LENGTH = 6;
    private static final int WALL_THICKNESS = 7;
    private static final int DUCT_DIAMETER = 8;
    private static final int INLET_TEMPERATURE = 9;
    private static final int OUTLET_TEMPERATURE = 10;
    private static final int WATER_DENSITY = 11;
    private static final int WATER_CONDUCTIVITY = 12;

    private final Object[] columnsHeaders = new String[] {
            "Дата",
            "Геометрия МНЛЗ",
            "Длина сляба (м)",
            "Ширина сляба (м)",
            "Тип стенки",
            "Длина стенки (м)",
            "Активная длина стенки (м)",
            "Толщина стенки (м)",
            "Диаметр каналов (м)",
            "Температура на входе (°C)",
            "Температура на выходе (°C)",
            "Плотность (кг/м³)",
            "Теплопроводность (Дж/(кг⋅К))"
    };

    private final DefaultTableModel tableModel;
    private final JTable table;
    private final JScrollPane scrollPane;
    private final CardLayout cardLayout;
    private final JPanel cardPanel;

    private LoginForm loginForm;

    public CoolingHistoryPanel() {
        setLayout(new BorderLayout());

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        loginForm = new LoginForm(this);

        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableModel.setColumnIdentifiers(columnsHeaders);

        table = new JTable(tableModel);
        table.setAutoCreateRowSorter(true);
        table.setFillsViewportHeight(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        setupTableColumns();

        scrollPane = new JScrollPane(table);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        cardPanel.add(loginForm, "LOGIN");
        cardPanel.add(scrollPane, "DATA");

        add(cardPanel, BorderLayout.CENTER);

        refresh();
    }

    private void setupTableColumns() {
        table.getColumnModel().getColumn(DATE).setPreferredWidth(120);
        table.getColumnModel().getColumn(GEOMETRY).setPreferredWidth(150);
        table.getColumnModel().getColumn(SLAB_WIDTH).setPreferredWidth(100);
        table.getColumnModel().getColumn(SLAB_LENGTH).setPreferredWidth(100);
        table.getColumnModel().getColumn(WALL_TYPE).setPreferredWidth(100);
        table.getColumnModel().getColumn(WALL_LENGTH).setPreferredWidth(100);
        table.getColumnModel().getColumn(WALL_ACTIVE_LENGTH).setPreferredWidth(120);
        table.getColumnModel().getColumn(WALL_THICKNESS).setPreferredWidth(100);
        table.getColumnModel().getColumn(DUCT_DIAMETER).setPreferredWidth(100);
        table.getColumnModel().getColumn(INLET_TEMPERATURE).setPreferredWidth(120);
        table.getColumnModel().getColumn(OUTLET_TEMPERATURE).setPreferredWidth(120);
        table.getColumnModel().getColumn(WATER_DENSITY).setPreferredWidth(100);
        table.getColumnModel().getColumn(WATER_CONDUCTIVITY).setPreferredWidth(120);
    }

    public void refresh() {
        if (!User.isAuthorized()) {
            cardLayout.show(cardPanel, "LOGIN");
        } else {
            cardLayout.show(cardPanel, "DATA");
            loadData();
        }
    }

    private void loadData() {
        tableModel.setRowCount(0);

        Connection dbConnection = User.getDbConnection();
        try {
            String sql = """
                SELECT
                    date,
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
                FROM alchevsk.cooling_history
                ORDER BY date DESC
                """;

            java.sql.PreparedStatement statement = dbConnection.prepareStatement(sql);
            ResultSet results = statement.executeQuery();

            int rowCount = 0;
            while (results.next()) {
                Object[] row = new Object[13];
                row[DATE] = results.getTimestamp("date");
                row[GEOMETRY] = results.getString("ccm_geometry");
                if (row[GEOMETRY].equals("curved")) {
                    row[GEOMETRY] = "Криволинейная";
                } else if (row[GEOMETRY].equals("vertical")) {
                    row[GEOMETRY] = "Вертикальная";
                }
                row[SLAB_WIDTH] = results.getDouble("slab_width");
                row[SLAB_LENGTH] = results.getDouble("slab_height");
                row[WALL_TYPE] = results.getString("wall_type");
                if (row[WALL_TYPE].equals("smoothed")) {
                    row[WALL_TYPE] = "Гладкая";
                } else if (row[WALL_TYPE].equals("profiled")) {
                    row[WALL_TYPE] = "Профилированная";
                }
                row[WALL_LENGTH] = results.getDouble("wall_length");
                row[WALL_ACTIVE_LENGTH] = results.getDouble("wall_active_length");
                row[WALL_THICKNESS] = results.getDouble("wall_thickness");
                row[DUCT_DIAMETER] = results.getDouble("duct_diameter");
                row[INLET_TEMPERATURE] = results.getDouble("inlet_temperature");
                row[OUTLET_TEMPERATURE] = results.getDouble("outlet_temperature");
                row[WATER_DENSITY] = results.getDouble("water_density");
                row[WATER_CONDUCTIVITY] = results.getDouble("water_conductivity");

                tableModel.addRow(row);
                rowCount++;
            }

            if (rowCount == 0) {
                JOptionPane.showMessageDialog(this,
                        "Нет данных для отображения",
                        "Информация",
                        JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "Ошибка загрузки данных: " + e.getMessage(),
                    "Ошибка",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}