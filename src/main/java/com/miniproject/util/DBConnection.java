package com.miniproject.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String DB_URL  = "jdbc:mysql://localhost:3306/miniproject4";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "root1234"; // change to your password

    private static Connection connection = null;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        }
        return connection;
    }
}