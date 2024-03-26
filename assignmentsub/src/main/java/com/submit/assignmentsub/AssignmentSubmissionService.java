package main.java.com.submit.assignmentsub;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AssignmentSubmissionService {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/your_database_name";
    private static final String USERNAME = "your_database_username";
    private static final String PASSWORD = "your_database_password";

    public static boolean submitAssignment(String course, String courseId, String uniqueId, File file) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            // Establish connection to the database
            connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);

            // Prepare SQL statement to insert assignment submission
            String query = "INSERT INTO assignment_submission (course, course_id, unique_id, file_data) VALUES (?, ?, ?, ?)";
            statement = connection.prepareStatement(query);
            statement.setString(1, course);
            statement.setString(2, courseId);
            statement.setString(3, uniqueId);
            statement.setBlob(4, new FileInputStream(file)); // Set the file data

            // Execute the update
            int rowsAffected = statement.executeUpdate();

            // Check if assignment submission was successful
            return rowsAffected > 0;
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            // Close resources
            try {
                if (statement != null)
                    statement.close();
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
