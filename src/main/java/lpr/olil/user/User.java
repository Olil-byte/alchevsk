package lpr.olil.user;

import java.sql.Connection;
import java.sql.DriverManager;

public class User {
    private static Connection dbConnection = null;

    public static Connection getDbConnection() {
        return dbConnection;
    }

    public static void authorize(String login, String password) {
        try {
            dbConnection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/alchevsk",
                    login,
                    password
            );
        } catch (Exception e) {
        }
    }

    public static boolean isAuthorized() {
        return dbConnection != null;
    }
}
