package main.java.com.submit.assignmentsub;

public class Assignment {
    public static void main(String[] args) {
        // Prompt the user to the AuthenticationGUI
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new AuthenticationGUI();
            }
        });
    }
}
