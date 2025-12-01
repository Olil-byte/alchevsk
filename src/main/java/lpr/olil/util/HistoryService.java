package lpr.olil.util;

import lpr.olil.user.User;
import lpr.olil.model.*;
import javax.swing.*;
import java.sql.*;

public class HistoryService {
    public void saveToHistoryIfAuthorized(
            Slab slab,
            Wall wall,
            Crystallizer crystallizer,
            Ccm ccm,
            WaterFlow waterFlow,
            JComponent parent,
            Runnable refreshCallback) {

        if (!User.isAuthorized()) {
            return;
        }

        Connection dbConnection = User.getDbConnection();
        String sql = """
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

            if (refreshCallback != null) {
                refreshCallback.run();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(parent,
                    "Ошибка сохранения в историю: " + e.getMessage(),
                    "Ошибка базы данных",
                    JOptionPane.WARNING_MESSAGE);
        }
    }
}