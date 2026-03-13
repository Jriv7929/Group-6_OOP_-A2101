
package motorph_oop.ui;

import java.awt.*;
import javax.swing.*;

public class EmployeeMainDashboard extends JFrame {

    private CardLayout cardLayout;
    private JPanel cardPanel;

    private String userEmpNo;
    private String userLoggedIn;
    private String userFirstname;
    private String userLastname;

    public FullDetailsPanel fullEmpPanel = new FullDetailsPanel();
    public LeavePanel leaveApp = new LeavePanel();
    public TimePanel timeEmpPanel = new TimePanel();

    public EmployeeMainDashboard(String employeenum,
                                 String loginnum,
                                 String lastname,
                                 String firstname) {

        userEmpNo = employeenum;
        userLoggedIn = loginnum;
        userFirstname = firstname;
        userLastname = lastname;

        setTitle("MotorPH Employee Dashboard");
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

        JButton btnHome =
                UIUtils.createNavButton("Home", Color.white, Color.black);

        JButton btnTime =
                UIUtils.createNavButton("Time", Color.white, Color.black);

        JButton btnLeave =
                UIUtils.createNavButton("Leave Application", Color.white, Color.black);

        JButton btnLogout =
                UIUtils.createNavButton("Log out", Color.white, Color.black);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        fullEmpPanel.setEmployeeNo(userEmpNo);

        navPanel.add(Box.createVerticalStrut(20));
        navPanel.add(welcome);
        navPanel.add(Box.createVerticalStrut(20));
        navPanel.add(btnHome);
        navPanel.add(Box.createVerticalStrut(20));
        navPanel.add(btnTime);
        navPanel.add(Box.createVerticalStrut(20));
        navPanel.add(btnLeave);

        navPanel.add(Box.createVerticalStrut(450));
        navPanel.add(btnLogout);
        navPanel.add(Box.createVerticalStrut(20));

        cardPanel.add(fullEmpPanel, "Home");

        btnHome.addActionListener(e ->
                switchPanel(fullEmpPanel)
        );

        btnLeave.addActionListener(e ->
                switchPanel(leaveApp)
        );

        btnTime.addActionListener(e -> {

            timeEmpPanel.setLoggedIn(
                    userLoggedIn,
                    userLastname,
                    userFirstname
            );

            switchPanel(timeEmpPanel);
        });

        btnLogout.addActionListener(e -> {

            new LoginPanel();
            dispose();
        });

        add(navPanel, BorderLayout.WEST);
        add(cardPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private void switchPanel(JPanel panel) {

        cardPanel.removeAll();
        cardPanel.add(panel);
        cardPanel.revalidate();
        cardPanel.repaint();
    }
}