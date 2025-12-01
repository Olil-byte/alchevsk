package lpr.olil.user;

import java.sql.Connection;
import java.sql.DriverManager;

public class User {
    private static Connection dbConnection = null;
    private static String name = null;

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

        name = login;
    }

    public static boolean isAuthorized() {
        return dbConnection != null;
    }

    public static String getName() {
        return name;
    }
}
