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
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class Login extends JFrame {
    private JTextField RegNoField;
    private JPasswordField passwordField;
    private JButton loginButton;

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/AssignSub";
    private static final String USERNAME = "your_username";
    private static final String PASSWORD = "your_password";

    public boolean authenticateUser(String RegNo, String password) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            String query = "SELECT * FROM User WHERE RegNo = ? AND password = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, RegNo);
                statement.setString(2, password);
                ResultSet resultSet = statement.executeQuery();
                return resultSet.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Login() {
        setTitle("Login");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1));

        JLabel RegNoLabel = new JLabel("RegNo:");
        panel.add(RegNoLabel);

        RegNoField = new JTextField(20);
        panel.add(RegNoField);

        JLabel passwordLabel = new JLabel("Password:");
        panel.add(passwordLabel);

        passwordField = new JPasswordField(20);
        panel.add(passwordField);

        loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String RegNo = RegNoField.getText();
                String password = new String(passwordField.getPassword());
                boolean success = authenticateUser(RegNo, password);
                if (success) {
                    JOptionPane.showMessageDialog(Login.this, "Login successful");
                    // Open main application window or perform other actions
                } else {
                    JOptionPane.showMessageDialog(Login.this, "Error: Login failed");
                }
            }
        });
        panel.add(loginButton);

        add(panel);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Login();
            }
        });
    }
}
