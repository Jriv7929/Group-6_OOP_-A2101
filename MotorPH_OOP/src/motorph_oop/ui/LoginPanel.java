
package motorph_oop.ui;

import javax.swing.*;
import java.awt.*;
import motorph_oop.service.LoginService;

// Login window for MotorPH system.
// Handles authentication and password reset.
 
public class LoginPanel extends JFrame {

    // Login fields
    private JTextField empField;
    private JPasswordField passField;

    // Service layer
    private LoginService loginService = new LoginService();

    // Constructor: builds login UI.
    public LoginPanel() {

        setTitle("MotorPH Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 450);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // LEFT PANEL

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(new Color(128, 0, 0));
        leftPanel.setPreferredSize(new Dimension(400, 450));

        JLabel welcomeLabel =
                new JLabel("Welcome to MotorPH", SwingConstants.CENTER);

        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        welcomeLabel.setBorder(
                BorderFactory.createEmptyBorder(60, 0, 10, 0)
        );

        JLabel imageLabel =
                new JLabel(
                        new ImageIcon(
                                "src/motorph_oop/resources/motorphimage.png"
                        )
                );

        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        leftPanel.add(welcomeLabel, BorderLayout.NORTH);
        leftPanel.add(imageLabel, BorderLayout.CENTER);

        // RIGHT PANEL
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(null);
        rightPanel.setBackground(Color.LIGHT_GRAY);

        JLabel loginLabel =
                new JLabel("Please log in with your credentials");

        loginLabel.setFont(new Font("Arial", Font.BOLD, 14));
        loginLabel.setBounds(50, 30, 300, 25);
        rightPanel.add(loginLabel);

        JLabel empLabel = new JLabel("Username:");
        empLabel.setBounds(50, 90, 100, 25);
        rightPanel.add(empLabel);

        empField = new JTextField();
        empField.setBounds(160, 90, 200, 25);
        rightPanel.add(empField);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(50, 140, 100, 25);
        rightPanel.add(passLabel);

        passField = new JPasswordField();
        passField.setBounds(160, 140, 200, 25);
        rightPanel.add(passField);

        JCheckBox showPassword = new JCheckBox("Show");
        showPassword.setBounds(370, 140, 70, 25);
        showPassword.setBackground(Color.LIGHT_GRAY);
        rightPanel.add(showPassword);

        showPassword.addActionListener(e -> {

            if (showPassword.isSelected()) {
                passField.setEchoChar((char) 0);
            } else {
                passField.setEchoChar('•');
            }
        });

        JButton loginButton =
                UIUtils.createButton("Log in", Color.WHITE, Color.BLACK);

        loginButton.setBounds(160, 190, 90, 30);
        rightPanel.add(loginButton);

        JButton exitButton =
                UIUtils.createButton("Exit", Color.WHITE, Color.BLACK);

        exitButton.setBounds(270, 190, 90, 30);
        rightPanel.add(exitButton);

        JButton resetButton =
                UIUtils.createButton(
                        "Forgot Password?",
                        Color.WHITE,
                        Color.BLACK
                );

        resetButton.setBounds(160, 240, 200, 30);
        rightPanel.add(resetButton);

        // BUTTON ACTIONS
        exitButton.addActionListener(e -> System.exit(0));
        loginButton.addActionListener(e -> login());
        resetButton.addActionListener(e -> openResetDialog());

        // ADD PANELS
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    // LOGIN
    //Handles login validation.
    private void login() {

        String username = empField.getText().trim();
        String password = new String(passField.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill in your username and password."
            );

            return;
        }

        String[] user =
                loginService.authenticate(username, password);

        if (user == null) {

            if (loginService.isLockedOut()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Too many failed login attempts. Program will close."
                );

                System.exit(0);
            }

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid username or password.\nAttempts remaining: "
                            + loginService.getRemainingAttempts()
            );

            return;
        }

        if (loginService.isDefaultPassword(password)) {

            JOptionPane.showMessageDialog(
                    this,
                    "You are using the default password.\nPlease reset it."
            );

            openResetDialog();
            return;
        }

        JOptionPane.showMessageDialog(
                this,
                "Welcome, " + user[3] + "!"
        );

        new DashboardPanel(
                user[0],
                user[5],
                user[0],
                user[2],
                user[3]
        );

        dispose();
    }

    // RESET PASSWORD
    @FunctionalInterface
    private interface SimpleDocumentListener
            extends javax.swing.event.DocumentListener {

        void update(javax.swing.event.DocumentEvent e);

        default void insertUpdate(javax.swing.event.DocumentEvent e) {
            update(e);
        }

        default void removeUpdate(javax.swing.event.DocumentEvent e) {
            update(e);
        }

        default void changedUpdate(javax.swing.event.DocumentEvent e) {
            update(e);
        }
    }

    // Opens password reset dialog.
    private void openResetDialog() {

        String username =
                JOptionPane.showInputDialog(
                        this,
                        "Enter your username:"
                );

        if (username == null ||
            username.trim().isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Username cannot be empty."
            );

            return;
        }

        JPasswordField newPass = new JPasswordField(12);
        JPasswordField confirmPass = new JPasswordField(12);

        JLabel strengthLabel = new JLabel(" ");
        strengthLabel.setFont(
                new Font("Arial", Font.BOLD, 12)
        );

        JCheckBox showBox =
                new JCheckBox("Show Password");

        showBox.addActionListener(e -> {

            char echo =
                    showBox.isSelected() ? (char) 0 : '•';

            newPass.setEchoChar(echo);
            confirmPass.setEchoChar(echo);
        });

        // Password strength indicator
        newPass.getDocument().addDocumentListener(
                (SimpleDocumentListener) e -> {

                    String strength =
                            loginService.getPasswordStrength(
                                    new String(newPass.getPassword())
                            );

                    strengthLabel.setText("Strength: " + strength);

                    strengthLabel.setForeground(
                            strength.equals("Strong")
                                    ? new Color(0, 153, 0)
                                    : strength.equals("Normal")
                                    ? Color.ORANGE
                                    : Color.RED
                    );
                }
        );

        JPanel panel =
                new JPanel(new GridBagLayout());

        panel.setPreferredSize(
                new Dimension(340, 170)
        );

        panel.setBorder(
                BorderFactory.createEmptyBorder(
                        10, 10, 10, 10
                )
        );

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("New Password:"), gbc);

        gbc.gridx = 1;
        panel.add(newPass, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Confirm Password:"), gbc);

        gbc.gridx = 1;
        panel.add(confirmPass, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(showBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(strengthLabel, gbc);

        int option =
                JOptionPane.showConfirmDialog(
                        this,
                        panel,
                        "Reset Password",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );

        if (option != JOptionPane.OK_OPTION) {
            return;
        }

        String newPassword =
                new String(newPass.getPassword()).trim();

        String confirmPassword =
                new String(confirmPass.getPassword()).trim();

        if (!newPassword.equals(confirmPassword)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Passwords do not match."
            );

            return;
        }

        if (!loginService.validatePasswordRules(newPassword)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Password must be max 12 chars, include 1 uppercase, 1 number, and 1 special character."
            );

            return;
        }

        boolean success =
                loginService.resetPassword(username, newPassword);

        JOptionPane.showMessageDialog(
                this,
                success
                        ? "Password successfully reset."
                        : "Reset failed. User not found."
        );
    }
}