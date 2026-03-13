package motorph_oop.ui;

import javax.swing.*;
import java.awt.*;
import motorph_oop.ui.AdminDashboardPanel;

public class LoginManagementPanel extends JFrame {

    public LoginManagementPanel() {

        setTitle("Login Management");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3,1,10,10));

        JButton btnCreateLogin = new JButton("Create Login");
        JButton btnResetPassword = new JButton("Reset Password");
        JButton btnDeleteCredentials = new JButton("Delete Credentials");

        add(btnCreateLogin);
        add(btnResetPassword);
        add(btnDeleteCredentials);
    }
}