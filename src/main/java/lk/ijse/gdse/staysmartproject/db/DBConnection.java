// DBConnection.java
package lk.ijse.gdse.staysmartproject.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static DBConnection dbConnection;
    private final Connection connection;
    private final String url = "jdbc:mysql://localhost:3306/staysmart";
    private final String user = "root";
    private final String password = "hasindu12345";

    private DBConnection() throws SQLException {
        connection = DriverManager.getConnection(url, user, password);
    }

    public static DBConnection getInstance() throws SQLException {
        if (dbConnection == null) {
            dbConnection = new DBConnection();
        }
        return dbConnection;
    }


    public Connection getConnection() {
        return connection;
    }
}