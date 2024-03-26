package main.java.com.submit.assignmentsub;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class AuthenticationGUI extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton signUpButton;
    private JButton forgotPasswordButton;
    private JButton logoutButton;
    private JPanel panel;
    private boolean isLoggedIn;

    public AuthenticationGUI() {
        setTitle("Authentication");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1));

        JLabel usernameLabel = new JLabel("Username:");
        panel.add(usernameLabel);

        usernameField = new JTextField(0);
        panel.add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        panel.add(passwordLabel);

        passwordField = new JPasswordField(20);
        panel.add(passwordField);

        loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                char[] password = passwordField.getPassword();
                if (isLoggedIn) {
                    // Logout logic
                    isLoggedIn = false;
                    usernameField.setText("");
                    passwordField.setText("");
                    panel.remove(logoutButton);
                    panel.add(signUpButton);
                    panel.add(forgotPasswordButton);
                    loginButton.setText("Login");
                    JOptionPane.showMessageDialog(AuthenticationGUI.this, "Logged out successfully.");
                } else {
                    // Authentication logic here
                    // If authentication successful, display message and change UI
                    // Else display error message
                    isLoggedIn = true;
                    panel.remove(signUpButton);
                    panel.remove(forgotPasswordButton);
                    panel.add(logoutButton);
                    loginButton.setText("Logged in");
                    JOptionPane.showMessageDialog(AuthenticationGUI.this, "Logged in successfully.");

                    // Open AssignmentSubmissionGUI upon successful login
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            new AssignmentSubmissionGUI();
                        }
                    });

                }
            }
        });
        panel.add(loginButton);

        signUpButton = new JButton("Sign Up");
        signUpButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open SignUp window
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        new SignUp();
                    }
                });
            }
        });
        panel.add(signUpButton);

        forgotPasswordButton = new JButton("Forgot Password");
        forgotPasswordButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open ForgotPassword window
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        new ForgotPassword();
                    }
                });
            }
        });
        panel.add(forgotPasswordButton);

        logoutButton = new JButton("Logout");
        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                isLoggedIn = false;
                usernameField.setText("");
                passwordField.setText("");
                panel.remove(logoutButton);
                panel.add(signUpButton);
                panel.add(forgotPasswordButton);
                loginButton.setText("Login");
                JOptionPane.showMessageDialog(AuthenticationGUI.this, "Logged out successfully.");
            }
        });

        add(panel);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new AuthenticationGUI();
            }
        });
    }
}
