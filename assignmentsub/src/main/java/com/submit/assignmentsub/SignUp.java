package main.java.com.submit.assignmentsub;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class SignUp extends JFrame {
    private JTextField emailField;
    private JTextField RegNoField;
    private JPasswordField passwordField;
    private JButton signUpButton;

    // Update the JDBC URL to point to the SQLite database file correctly
    private static final String JDBC_URL = "jdbc:sqlite:C:/IDDUS-iddy/+/assignmentSub/AssignSub.db";

    public boolean authenticateUser(String email, String RegNo, String password) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL)) {
            String query = "INSERT INTO User (email, RegNo, password) VALUES (?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, email);
                statement.setString(2, RegNo);
                statement.setString(3, password);
                int rowsInserted = statement.executeUpdate();
                return rowsInserted > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQLException appropriately, or log the error
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public SignUp() {
        setTitle("Sign Up");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1));

        JLabel emailLabel = new JLabel("Email:");
        panel.add(emailLabel);

        emailField = new JTextField(20);
        panel.add(emailField);

        JLabel RegNoLabel = new JLabel("RegNo:");
        panel.add(RegNoLabel);

        RegNoField = new JTextField(20);
        panel.add(RegNoField);

        JLabel passwordLabel = new JLabel("Password:");
        panel.add(passwordLabel);

        passwordField = new JPasswordField(20);
        panel.add(passwordField);

        signUpButton = new JButton("Sign Up");
        signUpButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                String RegNo = RegNoField.getText();
                String password = new String(passwordField.getPassword());
                boolean success = authenticateUser(email, RegNo, password);
                if (success) {
                    JOptionPane.showMessageDialog(SignUp.this, "Sign Up successful");
                } else {
                    JOptionPane.showMessageDialog(SignUp.this, "Error: Sign Up failed");
                }
            }
        });
        panel.add(signUpButton);

        add(panel);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new SignUp();
            }
        });
    }
}
