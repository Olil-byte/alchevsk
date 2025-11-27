package lpr.olil.view;

import lpr.olil.user.UserDb;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CoolingHistoryPanel extends JPanel {
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

    private final JPanel dataPanel;
    private final JPanel loginRequiredPanel;

    public CoolingHistoryPanel() {
        setLayout(new BorderLayout());

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        dataPanel = new JPanel(new BorderLayout());

        loginRequiredPanel = createLoginRequiredPanel();

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

        dataPanel.add(scrollPane, BorderLayout.CENTER);

        // Добавляем панели в CardLayout
        cardPanel.add(loginRequiredPanel, "LOGIN_REQUIRED");
        cardPanel.add(dataPanel, "DATA");

        add(cardPanel, BorderLayout.CENTER);

        refresh();
    }

    private JPanel createLoginRequiredPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        JLabel messageLabel = new JLabel(
                "<html><center><h2>Требуется авторизация</h2>" +
                        "<p>Для просмотра истории расчетов необходимо войти в систему</p></center></html>",
                JLabel.CENTER
        );
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        JButton refreshButton = new JButton("Обновить");
        refreshButton.addActionListener(e -> refresh());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(refreshButton);

        panel.add(messageLabel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void setupTableColumns() {
        table.getColumnModel().getColumn(0).setPreferredWidth(120);
        table.getColumnModel().getColumn(1).setPreferredWidth(150);
        table.getColumnModel().getColumn(2).setPreferredWidth(100);
        table.getColumnModel().getColumn(3).setPreferredWidth(100);
        table.getColumnModel().getColumn(4).setPreferredWidth(100);
        table.getColumnModel().getColumn(5).setPreferredWidth(100);
        table.getColumnModel().getColumn(6).setPreferredWidth(120);
        table.getColumnModel().getColumn(7).setPreferredWidth(100);
        table.getColumnModel().getColumn(8).setPreferredWidth(100);
        table.getColumnModel().getColumn(9).setPreferredWidth(120);
        table.getColumnModel().getColumn(10).setPreferredWidth(120);
        table.getColumnModel().getColumn(11).setPreferredWidth(100);
        table.getColumnModel().getColumn(12).setPreferredWidth(120);
    }

    public void refresh() {
        if (UserDb.INSTANCE == null) {
            cardLayout.show(cardPanel, "LOGIN_REQUIRED");
        } else {
            cardLayout.show(cardPanel, "DATA");
            loadData();
        }
    }

    private void loadData() {
        tableModel.setRowCount(0);

        Connection dbConnection = UserDb.INSTANCE.getConnection();
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
                row[0] = results.getTimestamp("date");
                row[1] = results.getString("ccm_geometry");
                row[2] = results.getDouble("slab_width");
                row[3] = results.getDouble("slab_height");
                row[4] = results.getString("wall_type");
                row[5] = results.getDouble("wall_length");
                row[6] = results.getDouble("wall_active_length");
                row[7] = results.getDouble("wall_thickness");
                row[8] = results.getDouble("duct_diameter");
                row[9] = results.getDouble("inlet_temperature");
                row[10] = results.getDouble("outlet_temperature");
                row[11] = results.getDouble("water_density");
                row[12] = results.getDouble("water_conductivity");

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