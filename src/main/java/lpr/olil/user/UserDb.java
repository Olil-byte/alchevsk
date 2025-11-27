package lpr.olil.user;

import java.sql.Connection;
import java.sql.DriverManager;

public class UserDb {
    private Connection connection;

    public static UserDb INSTANCE = null;

    public UserDb(Connection connection) {
        this.connection = connection;

        INSTANCE = this;
    }

    public Connection getConnection() {
        return connection;
    }
}
