package main.java.com.submit.assignmentsub;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

public class AssignmentSubmissionGUI extends JFrame {
    private JTextField courseField;
    private JTextField courseIdField;
    private JLabel randomIdLabel;
    private JButton uploadButton;
    private JButton submitButton;
    private File selectedFile;

    public AssignmentSubmissionGUI() {
        setTitle("Assignment Submission");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));

        JLabel courseLabel = new JLabel("Course:");
        panel.add(courseLabel);

        courseField = new JTextField(20);
        panel.add(courseField);

        JLabel courseIdLabel = new JLabel("Course ID:");
        panel.add(courseIdLabel);

        courseIdField = new JTextField(20);
        panel.add(courseIdField);

        JLabel randomIdTextLabel = new JLabel("Unique Random ID:");
        panel.add(randomIdTextLabel);

        randomIdLabel = new JLabel(generateRandomId(8)); // Default length is 8
        panel.add(randomIdLabel);

        uploadButton = new JButton("Upload File");
        uploadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files", "txt");
                fileChooser.setFileFilter(filter);
                int returnValue = fileChooser.showOpenDialog(AssignmentSubmissionGUI.this);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    selectedFile = fileChooser.getSelectedFile();
                    // Generate unique random ID after file upload
                    randomIdLabel.setText(generateRandomId(8)); // Change the length as needed
                    JOptionPane.showMessageDialog(AssignmentSubmissionGUI.this,
                            "File selected: " + selectedFile.getName());
                }
            }
        });
        panel.add(uploadButton);

        submitButton = new JButton("Submit Assignment");
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (selectedFile == null) {
                    JOptionPane.showMessageDialog(AssignmentSubmissionGUI.this, "Please select a file to upload.");
                    return;
                }
                String course = courseField.getText();
                String courseId = courseIdField.getText();
                String uniqueId = randomIdLabel.getText();
                // Assuming you have a method to submit the assignment
                boolean submissionSuccessful = submitAssignment(course, courseId, uniqueId, selectedFile);
                if (submissionSuccessful) {
                    JOptionPane.showMessageDialog(AssignmentSubmissionGUI.this,
                            "Assignment submitted successfully:\nCourse: " + course +
                                    "\nCourse ID: " + courseId + "\nUnique Random ID: " + uniqueId);
                } else {
                    JOptionPane.showMessageDialog(AssignmentSubmissionGUI.this,
                            "Failed to submit assignment. Please try again.");
                }
            }
        });
        panel.add(submitButton);

        add(panel);
        setVisible(true);
    }

    private String generateRandomId(int length) {
        // Define the character set for the random ID
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder randomId = new StringBuilder();

        // Create an instance of Random class
        Random random = new Random();

        // Generate random characters from the defined character set
        for (int i = 0; i < length; i++) {
            randomId.append(characters.charAt(random.nextInt(characters.length())));
        }

        return randomId.toString();
    }

    private boolean submitAssignment(String course, String courseId, String uniqueId, File selectedFile) {
        // Add your logic to submit the assignment
        // Return true if the submission is successful, false otherwise
        return true; // Placeholder, replace with your implementation
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new AssignmentSubmissionGUI();
            }
        });
    }
}
