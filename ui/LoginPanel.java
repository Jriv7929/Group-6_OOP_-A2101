package motorph_payroll.ui;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import motorph_payroll.service.LoginService;
import motorph_payroll.model.User;


import motorph_payroll.utils.Constants;
import motorph_payroll.utils.UIUtils;

public class LoginPanel extends JFrame {

    private JTextField empField;
    private JPasswordField passField;
    private int loginAttempts = 0; // Track number of failed login attempts

    public LoginPanel() {

        setTitle("MotorPH Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 450);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Left Panel
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(new Color(128, 0, 0));

        JLabel welcomeLabel = new JLabel("Welcome to MotorPH", SwingConstants.CENTER);
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(60, 0, -5, 0));

        JLabel imageLabel = new JLabel(new ImageIcon("src/motorph_payroll/resources/motorphimage.png"));

        leftPanel.add(welcomeLabel, BorderLayout.NORTH);
        leftPanel.add(imageLabel);

        // Right Panel (Login Form)
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(null);
        rightPanel.setBackground(Color.LIGHT_GRAY);

        JLabel loginLabel = new JLabel("Please log in with your credentials");
        loginLabel.setFont(new Font("Arial", Font.BOLD, 14));
        loginLabel.setBounds(50, 30, 300, 25);
        rightPanel.add(loginLabel);

        JLabel empLabel = new JLabel("Username :");
        empLabel.setBounds(50, 80, 100, 25);
        rightPanel.add(empLabel);

        empField = new JTextField();
        empField.setBounds(160, 80, 200, 25);
        rightPanel.add(empField);

        JLabel passLabel = new JLabel("Password :");
        passLabel.setBounds(50, 120, 100, 25);
        rightPanel.add(passLabel);

        passField = new JPasswordField();
        passField.setBounds(160, 120, 200, 25);
        rightPanel.add(passField);

        // Show/Hide Password Checkbox for Login
        JCheckBox showLoginPassword = new JCheckBox("Show");
        showLoginPassword.setBounds(370, 120, 70, 25);
        showLoginPassword.setBackground(Color.LIGHT_GRAY);
        rightPanel.add(showLoginPassword);

        showLoginPassword.addActionListener(e -> {
            if (showLoginPassword.isSelected()) {
                passField.setEchoChar((char) 0); // show
            } else {
                passField.setEchoChar('•'); // hide
            }
        });

        JButton loginButton = UIUtils.createButton("Log in", Color.white, Color.black);
        loginButton.setBounds(160, 170, 90, 30);
        rightPanel.add(loginButton);

        JButton exitButton = UIUtils.createButton("Exit", Color.white, Color.black);
        exitButton.setBounds(270, 170, 90, 30);
        rightPanel.add(exitButton);

        // New Password Reset Button
        JButton resetButton = UIUtils.createButton("Forgot Password?", Color.white, Color.black);
        resetButton.setBounds(160, 220, 200, 30);
        rightPanel.add(resetButton);
        
        exitButton.addActionListener(e -> System.exit(0));

        loginButton.addActionListener(e -> {
            if (empField.getText().isEmpty() || passField.getPassword().length == 0) {
                JOptionPane.showMessageDialog(this, "Please fill in your username or password.");
            } else {
                login();
            }
        });

        // Add reset password action
        resetButton.addActionListener(e -> {

    if (empField.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(this,
                "Enter username before using Forgot Password.");
        return;
    }

    new ResetPasswordPanel(
        (JFrame) SwingUtilities.getWindowAncestor(this)
    );
});

        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);

        leftPanel.setPreferredSize(new Dimension(400, getHeight()));
        setVisible(true);
    }

  
   private LoginService loginService = new LoginService();
   
  
    private void login() {

    String inputUsername = empField.getText().trim();
    String inputPassword = new String(passField.getPassword()).trim();

    User user = loginService.login(inputUsername, inputPassword);
    
   
    if (user == null) {
        loginAttempts++;
        JOptionPane.showMessageDialog(this,
                "Invalid username or password.\nAttempts remaining: "
                        + (3 - loginAttempts));
        return;
    }
    
        if (++loginAttempts >= 3) {
        JOptionPane.showMessageDialog(this,
                "Maximum login attempts reached.");
        System.exit(0);
    }
    

    if (loginService.isDefaultPassword(user)) {
        JOptionPane.showMessageDialog(
                this,
                "You are using the default password.\nPlease reset it now."
        );
        forcePasswordReset(user.getUsername());
        return;
    }

    JOptionPane.showMessageDialog(this,
            "Welcome, " + user.getFirstName() + "!");

    new DashboardPanel(
            user.getEmployeeNum(),
            user.getAccessLevel(),
            user.getEmployeeNum(),
            user.getLastName(),
            user.getFirstName()
    );

    dispose();
}
    private boolean isPasswordValid(String password) {

        if (password.length() > 12) {
            return false;
        }
        boolean hasUpper = password.matches(".*[A-Z].*");
        boolean hasDigit = password.matches(".*\\d.*");
        boolean hasSpecial =
                password.matches(".*[!@#$%^&*(),.?\":{}|<>].*");
        return hasUpper && hasDigit && hasSpecial;
    }
    
    private String getPasswordStrength(String password) {

        if (password.isEmpty()) {
            return "";
        }
        int score = 0;
        if (password.matches(".*[A-Z].*")) score++;
        if (password.matches(".*\\d.*")) score++;
        if (password.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) score++;
        if (password.length() >= 8 && password.length() <= 12) score++;
        if (score <= 1) return "Weak";
        if (score == 2 || score == 3) return "Normal";
        return "Strong";
    }

    // Forced reset when default password is used
private void forcePasswordReset(String username) {

    JPasswordField newPassField = new JPasswordField();
    JPasswordField confirmPassField = new JPasswordField();

    Object[] message = {
            "New Password:", newPassField,
            "Confirm Password:", confirmPassField
    };

    int option = JOptionPane.showConfirmDialog(
            this,
            message,
            "Reset Password",
            JOptionPane.OK_CANCEL_OPTION
    );

    if (option != JOptionPane.OK_OPTION) return;

    String newPassword = new String(newPassField.getPassword());
    String confirmPassword = new String(confirmPassField.getPassword());

    if (!newPassword.equals(confirmPassword)) {
        JOptionPane.showMessageDialog(this, "Passwords do not match.");
        return;
    }

    boolean success = loginService.resetPassword(username, newPassword);

    if (success) {
        JOptionPane.showMessageDialog(this,
                "Password successfully updated. Please log in again.");
    } else {
        JOptionPane.showMessageDialog(this,
                "Password reset failed.");
    }
}
}