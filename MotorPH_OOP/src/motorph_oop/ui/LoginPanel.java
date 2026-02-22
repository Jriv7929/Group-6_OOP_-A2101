package motorph_oop.ui;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import motorph_oop.dao.Constants;

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
        welcomeLabel.setBounds(50, 30, 300, 25);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(60, 0, -5, 0));

        JLabel imageLabel = new JLabel(new ImageIcon("src/motorph_oop/resources/motorphimage.png"));
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
        
        // 🔹 Show/Hide Password Checkbox for Login
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

        // 🔹 New Password Reset Button
        JButton resetButton = UIUtils.createButton("Forgot Password?", Color.white, Color.black);
        resetButton.setBounds(160, 220, 200, 30);
        rightPanel.add(resetButton);

        // Existing button actions
        exitButton.addActionListener(e -> System.exit(0));
        loginButton.addActionListener(e -> {
            if (empField.getText().isEmpty() || passField.getPassword().length == 0) {
                JOptionPane.showMessageDialog(this, "Please fill in your username or password.");
            } else {
                login();
            }
        });

        // 🔹 Add reset password action
        resetButton.addActionListener(e -> resetPassword());

        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);
        leftPanel.setPreferredSize(new Dimension(400, getHeight()));
        setVisible(true);
    }
private void login() {

    String inputUsername = empField.getText().trim();
    String inputPassword = new String(passField.getPassword()).trim();

    File file = new File(Constants.LOGIN_CSV);
    if (!file.exists()) {
        JOptionPane.showMessageDialog(this, "User credentials file not found.");
        return;
    }

    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
        String line;
        boolean header = true;
        while ((line = reader.readLine()) != null) {
            if (header) {
                header = false;
                continue;
            }
            String[] parts = line.split(",", -1);
            if (parts.length >= 6) {
                String employeeNum = parts[0].trim();
                String username = parts[1].trim();
                String lastName = parts[2].trim();
                String firstName = parts[3].trim();
                String password = parts[4].trim();
                String accessLevel = parts[5].trim();

                
                
         
                
           
              // Successful login
if (inputUsername.equalsIgnoreCase(username) && inputPassword.equals(password)) {

    // 🔹 Force password reset if using default password
    if (password.equals("1234")) {
        JOptionPane.showMessageDialog(this,
                "You are using the default password.\nYou must reset your password before continuing.");

        resetPassword();  // 🔹 Call your reset password method
        return;           // Prevent access until password is reset
    }

    // Normal login
    JOptionPane.showMessageDialog(this, "Welcome, " + firstName + "!");
    new DashboardPanel(employeeNum, accessLevel, employeeNum, lastName, firstName);
    dispose();
    return;
}
               
                
            }
        }

        //  If we reach here, login failed
        loginAttempts++; // increment failed attempt count
        if (loginAttempts >= 3) {
            JOptionPane.showMessageDialog(this, 
                "Too many failed login attempts. The program will now close.");
            System.exit(0); // close the program
        } else {
            JOptionPane.showMessageDialog(this, 
                "Invalid username or password.\nAttempts remaining: " + (3 - loginAttempts));
        }

    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "Error reading login file: " + e.getMessage());
    }
}
   
private void resetPassword() {
    String username = JOptionPane.showInputDialog(this, "Enter your username:");
    if (username == null || username.trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Username cannot be empty.");
        return;
    }

    File file = new File(Constants.LOGIN_CSV);
    if (!file.exists()) {
        JOptionPane.showMessageDialog(this, "User credentials file not found.");
        return;
    }

    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
        List<String> lines = new ArrayList<>();
        String line;
        boolean header = true;
        boolean found = false;

        while ((line = reader.readLine()) != null) {
            if (header) {
                lines.add(line);
                header = false;
                continue;
            }

            String[] parts = line.split(",", -1);
            if (parts.length >= 6 && parts[1].trim().equalsIgnoreCase(username.trim())) {
                found = true;

                // 🔹 Added: Ask for retrieval code first
                String employeeID = parts[0].trim();
                
                
                
                
                String birthYear = ""; 
                if (parts.length >= 7) { // assuming DOB or year is in column 6+
                    birthYear = parts[6].trim();
                } else {
                    // If your CSV doesn’t have birth year column, you may need to adjust this logic
                    birthYear = JOptionPane.showInputDialog(this, "Enter your year of birth (YYYY):");
                    if (birthYear == null || birthYear.trim().isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Year of birth is required.");
                        return;
                    }
                }
                
                String expectedCode = employeeID.substring(employeeID.length() - 3) + birthYear;
                String enteredCode = JOptionPane.showInputDialog(this, "Enter your retrieval code (last 3 digits of ID + birth year):");
                if (enteredCode == null || !enteredCode.equals(expectedCode)) {
                    JOptionPane.showMessageDialog(this, "Incorrect retrieval code. Password reset denied.");
                    return;
                }

          
                // 🔹 Password reset panel with strength indicator + show/hide option
                JPasswordField passwordField = new JPasswordField();
                JPasswordField confirmField = new JPasswordField(); // 🔹 Added confirm field
                JCheckBox showPasswordCheck = new JCheckBox("Show");
                showPasswordCheck.setBackground(Color.WHITE);
                JLabel strengthLabel = new JLabel(" ");
                strengthLabel.setFont(new Font("Arial", Font.BOLD, 12));

                JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5)); // changed 3→4 rows
                panel.add(new JLabel("New Password:"));
                panel.add(passwordField);
                panel.add(new JLabel("Confirm Password:")); // 🔹 Added confirm label
                panel.add(confirmField); // 🔹 Added confirm field
                panel.add(new JLabel("Show Password:"));
                panel.add(showPasswordCheck);
                panel.add(new JLabel("Strength:"));
                panel.add(strengthLabel);

                // 🔹 Show/Hide password checkbox logic
                showPasswordCheck.addActionListener(e -> {
                    if (showPasswordCheck.isSelected()) {
                        passwordField.setEchoChar((char) 0);
                        confirmField.setEchoChar((char) 0); // show confirm field too
                    } else {
                        passwordField.setEchoChar('•');
                        confirmField.setEchoChar('•');
                    }
                });

                // Live strength checker
                passwordField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
                    private void updateStrength() {
                        String password = new String(passwordField.getPassword());
                        String strength = getPasswordStrength(password);
                        strengthLabel.setText(strength);
                        switch (strength.toLowerCase()) {
                            case "weak":
                                strengthLabel.setForeground(Color.RED);
                                break;
                            case "normal":
                                strengthLabel.setForeground(Color.ORANGE);
                                break;
                            case "strong":
                                strengthLabel.setForeground(new Color(0, 153, 0));
                                break;
                            default:
                                strengthLabel.setForeground(Color.BLACK);
                        }
                    }
                    public void insertUpdate(javax.swing.event.DocumentEvent e) { updateStrength(); }
                    public void removeUpdate(javax.swing.event.DocumentEvent e) { updateStrength(); }
                    public void changedUpdate(javax.swing.event.DocumentEvent e) { updateStrength(); }
                });

                
                boolean valid = false;

                while (!valid) {
                int option = JOptionPane.showConfirmDialog(this, panel, "Reset Password",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (option != JOptionPane.OK_OPTION) {
                        return; // user cancelled
                    }

                String newPassword = new String(passwordField.getPassword()).trim();
                String confirmPassword = new String(confirmField.getPassword()).trim();

                if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Both password fields must be filled.");
                continue; // reopen dialog
                }

                if (!newPassword.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(this, "Passwords do not match. Please try again.");
                continue;
                }

                if (!isPasswordValid(newPassword)) {
                JOptionPane.showMessageDialog(this,
                "Password must be max 12 chars, include at least 1 uppercase, 1 number, and 1 special character.");
                continue;
                }

                // valid password
                valid = true;
                parts[4] = newPassword;
                line = String.join(",", parts);
                JOptionPane.showMessageDialog(this, "Password successfully reset.");
                }
                
                
                }
                lines.add(line);
                }

                if (!found) {
                JOptionPane.showMessageDialog(this, "Username not found.");
                return;
                }

                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                for (String l : lines) {
                writer.write(l);
                writer.newLine();
                }
            }

                } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error resetting password: " + e.getMessage());
    }
}
private boolean isPasswordValid(String password) {
    if (password.length() > 12) return false;
    boolean hasUpper = password.matches(".*[A-Z].*");
    boolean hasDigit = password.matches(".*\\d.*");
    boolean hasSpecial = password.matches(".*[!@#$%^&*(),.?\":{}|<>].*");
    return hasUpper && hasDigit && hasSpecial;
}

private String getPasswordStrength(String password) {
    if (password.isEmpty()) return "";
    int score = 0;
    if (password.matches(".*[A-Z].*")) score++;
    if (password.matches(".*\\d.*")) score++;
    if (password.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) score++;
    if (password.length() >= 8 && password.length() <= 12) score++;

    if (score <= 1) return "Weak";
    if (score == 2 || score == 3) return "Normal";
    else return "Strong";
}}
