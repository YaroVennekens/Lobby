package io.yennekens.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLConnection {
    private static Connection connection;
    private static boolean isConnected = false;

    public static boolean connect(String host, String port, String dbName, String username, String password) {
        try {

            String url = "jdbc:mysql://" + host + ":" + port + "?useUnicode=true&characterEncoding=UTF-8";
            connection = DriverManager.getConnection(url, username, password);



            String finalUrl = "jdbc:mysql://" + host + ":" + port + "/" + dbName + "?useUnicode=true&characterEncoding=UTF-8";
            connection = DriverManager.getConnection(finalUrl, username, password);
            isConnected = true;
            System.out.println("MySQL connection established successfully!");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Failed to establish MySQL connection!");
            isConnected = false;
            return false;
        }
    }



    public static void disconnect() {
        if (connection != null) {
            try {
                connection.close();
                isConnected = false;
                System.out.println("MySQL connection closed!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean isConnected() {
        return isConnected;
    }

    public static Connection getConnection() {
        return connection;
    }
}