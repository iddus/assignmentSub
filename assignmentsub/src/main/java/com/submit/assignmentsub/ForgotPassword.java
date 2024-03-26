package main.java.com.submit.assignmentsub;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class ForgotPassword extends JFrame {
    private JTextField RegNoField;
    private JTextField emailField;
    private JButton resetPasswordButton;

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/AssignSub";
    private static final String USERNAME = "";
    private static final String PASSWORD = "";

    public ForgotPassword() {
        setTitle("Forgot Password");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        JLabel RegNoLabel = new JLabel("RegNo:");
        panel.add(RegNoLabel);

        RegNoField = new JTextField(20);
        panel.add(RegNoField);

        JLabel emailLabel = new JLabel("Email:");
        panel.add(emailLabel);

        emailField = new JTextField(20);
        panel.add(emailField);

        resetPasswordButton = new JButton("Reset Password");
        resetPasswordButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String RegNo = RegNoField.getText();
                String email = emailField.getText();
                // Verify RegNo and email in the database
                if (verifyRegNoAndEmail(RegNo, email)) {
                    // Allow password reset
                    // You can implement password reset logic here
                    JOptionPane.showMessageDialog(ForgotPassword.this, "Password reset allowed for user: " + RegNo);
                } else {
                    // Deny password reset
                    JOptionPane.showMessageDialog(ForgotPassword.this,
                            "Invalid RegNo or email. Password reset denied.");
                }
            }
        });
        panel.add(resetPasswordButton);

        add(panel);
        setVisible(true);
    }

    private boolean verifyRegNoAndEmail(String RegNo, String email) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            // Establish connection to the database
            connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);

            // Prepare SQL statement to query the user table
            String query = "SELECT * FROM Users WHERE RegNo = ? AND email = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, RegNo);
            statement.setString(2, email);

            // Execute the query
            resultSet = statement.executeQuery();

            // Check if a matching user was found
            return resultSet.next();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            // Close resources
            try {
                if (resultSet != null)
                    resultSet.close();
                if (statement != null)
                    statement.close();
                if (connection != null)
                    connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ForgotPassword();
            }
        });
    }
}
