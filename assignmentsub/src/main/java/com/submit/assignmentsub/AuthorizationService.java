package main.java.com.submit.assignmentsub;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorizationService {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/your_database_name";
    private static final String USERNAME = "your_database_username";
    private static final String PASSWORD = "your_database_password";

    public boolean authorizeUser(String username, String action) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            // Establish connection to the database
            connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);

            // Prepare SQL statement to query the user table for role
            String query = "SELECT role FROM user WHERE username = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, username);

            // Execute the query
            resultSet = statement.executeQuery();

            // Check if a matching user was found
            if (resultSet.next()) {
                String role = resultSet.getString("role");
                // Check if user has permission to perform the action
                if (role.equals("admin")) {
                    // Admin has permission for all actions
                    return true;
                } else if (role.equals("teacher") && action.equals("submit")) {
                    // Teachers can submit assignments
                    return true;
                } else if (role.equals("student") && action.equals("view")) {
                    // Students can view assignments
                    return true;
                }
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
        // User is not authorized to perform the action
        return false;
    }
}
