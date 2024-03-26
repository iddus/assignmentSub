package com.submit.assignmentsub;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthenticationService {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/AssignSub";
    private static final String USERNAME = "";
    private static final String PASSWORD = "";

    public boolean authenticateUser(String RegNo, String password) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            // Establish connection to the database
            connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);

            // Prepare SQL statement to query the user table
            String query = "SELECT * FROM User WHERE RegNo = ? AND password = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, RegNo);
            statement.setString(2, password);

            // Execute the query
            resultSet = statement.executeQuery();

            // Check if a matching user was found
            if (resultSet.next()) {
                // User authenticated successfully
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (resultSet != null)
                    resultSet.close();
                if (statement != null)
                    statement.close();
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        // User authentication failed
        return false;
    }
}