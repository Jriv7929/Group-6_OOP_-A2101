
package motorph_oop.ui;

import java.awt.*;
import javax.swing.*;

public class AdminDashboardPanel extends JFrame {

    private CardLayout cardLayout;
    private JPanel cardPanel;

    private String userRole;
    private String userEmpNo;
    private String userLoggedIn;
    private String userFirstname;
    private String userLastname;
    private String selectedEmpNo;

    // PANELS
    public EmployeeDashboardPanel homePanel = new EmployeeDashboardPanel();
    public AddEmployeePanel addEmpPanel = new AddEmployeePanel();
    public FullDetailsPanel fullEmpPanel = new FullDetailsPanel();
    public LeavePanel leaveApp = new LeavePanel();
    public TimePanel timeEmpPanel = new TimePanel();

    // BUTTONS
    private JButton btnDatabase;
    private JButton btnAdd;
    private JButton btnFullDetails;
    private JButton btnTime;
    private JButton btnLeaveRequests;
    private JButton btnManageLogin;
    private JButton btnLogout;

    public AdminDashboardPanel(String employeenum,
                               String accesslevel,
                               String loginnum,
                               String lastname,
                               String firstname) {

        userRole = accesslevel;
        userEmpNo = employeenum;
        userLoggedIn = loginnum;
        userFirstname = firstname;
        userLastname = lastname;

        setTitle("MotorPH Admin Dashboard");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel navPanel = new JPanel();
        navPanel.setBackground(new Color(128, 0, 0));
        navPanel.setPreferredSize(new Dimension(200, getHeight()));
        navPanel.setLayout(new BoxLayout(navPanel, BoxLayout.Y_AXIS));

        JLabel welcome = new JLabel("Welcome to MOTORPH");
        welcome.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcome.setForeground(Color.WHITE);

        JLabel admin = new JLabel("Admin Dashboard");
        admin.setFont(new Font("Arial", Font.BOLD, 20));
        admin.setAlignmentX(Component.CENTER_ALIGNMENT);
        admin.setForeground(Color.WHITE);

        // BUTTON CREATION
        btnDatabase =
                UIUtils.createNavButton("Employee Database", Color.white, Color.black);

        btnAdd =
                UIUtils.createNavButton("Add Employee", Color.white, Color.black);

        btnFullDetails =
                UIUtils.createNavButton("View Full Details", Color.white, Color.black);

        btnTime =
                UIUtils.createNavButton("Time", Color.white, Color.black);

        btnLeaveRequests =
                UIUtils.createNavButton("Leave Requests", Color.white, Color.black);

        btnManageLogin =
                UIUtils.createNavButton("Login Management", Color.white, Color.black);

        btnLogout =
                UIUtils.createNavButton("Log out", Color.white, Color.black);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // NAVIGATION PANEL
        navPanel.add(Box.createVerticalStrut(20));
        navPanel.add(welcome);
        navPanel.add(Box.createVerticalStrut(5));
        navPanel.add(admin);
        navPanel.add(Box.createVerticalStrut(20));
        navPanel.add(btnDatabase);
        navPanel.add(Box.createVerticalStrut(10));
        navPanel.add(btnFullDetails);
        navPanel.add(Box.createVerticalStrut(10));
        navPanel.add(btnAdd);
        navPanel.add(Box.createVerticalStrut(10));
        navPanel.add(btnTime);
        navPanel.add(Box.createVerticalStrut(10));
        navPanel.add(btnLeaveRequests);
        navPanel.add(Box.createVerticalStrut(10));
        navPanel.add(btnManageLogin);

        navPanel.add(Box.createVerticalStrut(450));
        navPanel.add(btnLogout);
        navPanel.add(Box.createVerticalStrut(20));

        cardPanel.add(homePanel, "HOME");
        cardPanel.add(fullEmpPanel, "DETAILS");
        cardPanel.add(addEmpPanel, "ADD");
        cardPanel.add(timeEmpPanel, "TIME");

        // ACTION LISTENERS

        btnDatabase.addActionListener(e -> {

            if (!btnDatabase.isEnabled()) {
                showUnauthorized();
                return;
            }

            selectedEmpNo = null;
            homePanel.reloadEmployees();
            homePanel.clearFields();
            switchPanel("HOME");
        });

        btnAdd.addActionListener(e -> {

            if (!btnAdd.isEnabled()) {
                showUnauthorized();
                return;
            }

            switchPanel("ADD");
        });

        btnFullDetails.addActionListener(e -> {

            if (!btnFullDetails.isEnabled()) {
                showUnauthorized();
                return;
            }

            selectedEmpNo = homePanel.getSelectedEmployeeNo();

            if (selectedEmpNo == null ||
                    selectedEmpNo.trim().isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please select an employee.",
                        "No Selection",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            fullEmpPanel.setUserRole(userRole);   // PASS ADMIN ROLE
            fullEmpPanel.setEmployeeNo(selectedEmpNo);

            switchPanel("DETAILS");
        });

        btnLeaveRequests.addActionListener(e -> {

            if (!btnLeaveRequests.isEnabled()) {
                showUnauthorized();
                return;
            }

            new LeaveApprovalPanel().setVisible(true);
        });

        btnManageLogin.addActionListener(e -> {

    if (!btnManageLogin.isEnabled()) {
        showUnauthorized();
        return;
    }

        new LoginManagementPanel();
        });

        btnTime.addActionListener(e -> {

        timeEmpPanel.setLoggedIn(
            userLoggedIn,
            userLastname,
            userFirstname
         );

        switchPanel("TIME");
        });

        btnLogout.addActionListener(e -> {

            new LoginPanel();
            dispose();
        });

        add(navPanel, BorderLayout.WEST);
        add(cardPanel, BorderLayout.CENTER);

        // APPLY ROLE PERMISSIONS
        configurePermissions();

        setVisible(true);
    }

    // SWITCH PANELS
    private void switchPanel(String name) {
    cardLayout.show(cardPanel, name);
    }

    // SHOW UNAUTHORIZED MESSAGE
    private void showUnauthorized() {

        JOptionPane.showMessageDialog(
                this,
                "Your login is not authorized."
        );
    }

    // ROLE PERMISSIONS
    private void configurePermissions() {

        switch (userRole.toUpperCase()) {

            case "ADMIN":

                btnManageLogin.setEnabled(true);
                btnDatabase.setEnabled(true);
                btnAdd.setEnabled(true);
                btnLeaveRequests.setEnabled(true);
                btnTime.setVisible(false); 
                btnLogout.setEnabled(true);

                break;

            case "IT":

                btnManageLogin.setEnabled(true);
                btnDatabase.setEnabled(true);
                btnAdd.setVisible(false);
                btnLeaveRequests.setVisible(false);
                btnTime.setVisible(false);
                btnLogout.setEnabled(true);
                
                break;

            case "HR":

                btnManageLogin.setVisible(false);
                btnDatabase.setEnabled(true);
                btnAdd.setEnabled(true);
                btnLeaveRequests.setVisible(false);
                btnTime.setVisible(false);
                btnLogout.setEnabled(true);

                break;

            case "FINANCE":

                btnManageLogin.setVisible(false);
                btnDatabase.setEnabled(true);
                btnAdd.setVisible(false);
                btnLeaveRequests.setVisible(false);
                btnTime.setVisible(false);
                btnLogout.setEnabled(true);
               
                break;

            default:

                JOptionPane.showMessageDialog(
                        this,
                        "Your login is not authorized."
                );

                btnDatabase.setVisible(false);
                btnAdd.setVisible(false);
                btnLeaveRequests.setVisible(false);
                btnTime.setVisible(false);
                btnManageLogin.setVisible(false);
                             
        }
    }
    
    public class Session {

    public static String role;
    public static String empNo;

}
}