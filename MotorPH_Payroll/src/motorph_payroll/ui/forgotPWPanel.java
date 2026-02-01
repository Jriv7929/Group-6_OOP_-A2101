
package motorph_payroll.ui;

import java.awt.*;
import javax.swing.*;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import motorph_payroll.utils.UIUtils;

public class forgotPWPanel extends JFrame {
    
    private JTextField empEmail;
    private JPasswordField oldPassword;
    private JPasswordField passField1;
    private JPasswordField passField2;
    
    public forgotPWPanel(){
        setTitle("Forgot Password");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 450);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        //Left Panel
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(new Color(128, 0, 0));

        JLabel welcomeLabel = new JLabel("Welcome to MotorPH", SwingConstants.CENTER);
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setBounds(50, 30, 300, 25);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(60, 0, -5, 0));

        JLabel imageLabel = new JLabel(new ImageIcon("src/images/motorphimage.png"));
        leftPanel.add(welcomeLabel, BorderLayout.NORTH);
        leftPanel.add(imageLabel);

        // Right Panel (Login Form)
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(null);
        rightPanel.setBackground(Color.LIGHT_GRAY);

        JLabel loginLabel = new JLabel("Please enter your credentials");
        loginLabel.setFont(new Font("Arial", Font.BOLD, 14));
        loginLabel.setBounds(50, 30, 300, 25);
        rightPanel.add(loginLabel);

        JLabel empLabel = new JLabel("Email :");
        empLabel.setBounds(50, 80, 100, 25);
        rightPanel.add(empLabel);

        empEmail = new JTextField();
        empEmail.setBounds(170, 80, 200, 25);
        rightPanel.add(empEmail);

        JLabel oldpassLabel = new JLabel("Old Password :");
        oldpassLabel.setBounds(50, 130, 100, 25);
        rightPanel.add(oldpassLabel);

        oldPassword = new JPasswordField();
        oldPassword.setBounds(170, 130, 200, 25);
        rightPanel.add(oldPassword);

        JLabel newPassword = new JLabel("New Password :");
        newPassword.setBounds(50, 180, 100, 25);
        rightPanel.add(newPassword);

        passField1 = new JPasswordField();
        passField1.setBounds(170, 180, 200, 25);
        rightPanel.add(passField1);
        
        JLabel newPassword2 = new JLabel("Confirm Password :");
        newPassword2.setBounds(50, 230, 150, 25);
        rightPanel.add(newPassword2);

        passField2 = new JPasswordField();
        passField2.setBounds(170, 230, 200, 25);
        rightPanel.add(passField2);
        
        JButton resetpwButton = UIUtils.createButton("Reset Password", Color.white, Color.black);
        resetpwButton.setBounds(50, 280, 200, 30);
        rightPanel.add(resetpwButton);
        
        JButton backButton = UIUtils.createButton("Back to Log In", Color.white, Color.black);
        backButton.setBounds(260, 280, 200, 30);
        rightPanel.add(backButton);
         
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);
        leftPanel.setPreferredSize(new Dimension(400, getHeight()));
        setVisible(true);
    }
    
}