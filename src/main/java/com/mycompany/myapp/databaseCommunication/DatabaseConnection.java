package com.mycompany.myapp.databaseCommunication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Class for making connection to the database.
 */
class DatabaseConnection {
    private Connection connection;

    DatabaseConnection() {
        String url = "jdbc:postgresql://localhost:5432/postgres"; // localhost has to be changed to the addess of server.
                                                                  // postgres has to be changed to the name of database.
        String username = "postgres"; // Has to be changed to the username of admin.
        String password = "aivar"; // Has to be changed to the password of admin.
        try {
            Class.forName("org.postgresql.Driver");
        }
        catch (java.lang.ClassNotFoundException e) {
            System.out.println("error 1");
            System.out.println(e.getMessage());
        }
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    Connection getConnection() {
        return connection;
    }
}
