
package motorph_oop.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import motorph_oop.service.AttendanceService;
import motorph_oop.util.Constants;

// Time tracking panel.
// Allows employee to check in, check out, and view attendance records.
 
public class TimePanel extends JPanel {

    // Logged-in employee details
    private String loggedInEmployee;
    private String loggedInLastName;
    private String loggedInFirstName;

    // Table components
    private JTable table;
    private DefaultTableModel model;

    // Month filter
    private JComboBox<String> comboMonth;

    // Top panel
    private JPanel topPanel =
            new JPanel(new GridLayout(1, 1, 5, 5));

    // Buttons
    private JButton btnTimeIn =
            UIUtils.createButton("Check In", null, null);

    private JButton btnTimeOut =
            UIUtils.createButton("Check Out", null, null);

    // Service layer
    private final AttendanceService attendanceService =
            new AttendanceService();

    //Constructor: builds UI layout.
    public TimePanel() {

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(1200, getHeight()));


        // Header Section
        JLabel heading =
                UIUtils.createHeaderLabel("Time");

        topPanel.add(heading);
        topPanel.add(btnTimeIn);

        add(topPanel, BorderLayout.NORTH);

    
        // Attendance Table
        model = new DefaultTableModel(
                new String[] { "Date", "Log In", "Log Out" },
                0
        );

        table = new JTable(model);

        JScrollPane scroll =
                new JScrollPane(table);

        scroll.setPreferredSize(
                new Dimension(950, 650)
        );

        // Month Filter
        comboMonth = new JComboBox<>();
        comboMonth.addItem("All");

        comboMonth.addActionListener(e -> reloadTable());

        JPanel center =
                new JPanel(new BorderLayout());

        center.add(comboMonth, BorderLayout.NORTH);
        center.add(scroll);

        add(center, BorderLayout.SOUTH);


        // Button Actions
        btnTimeIn.addActionListener(e -> checkIn());
        btnTimeOut.addActionListener(e -> checkOut());
    }

    // Sets logged-in employee details.
    public void setLoggedIn(String empId,
                            String lastName,
                            String firstName) {

        this.loggedInEmployee = empId;
        this.loggedInLastName = lastName;
        this.loggedInFirstName = firstName;

        loadMonths();
        reloadTable();
    }

    //Loads available months for attendance.
    private void loadMonths() {

        comboMonth.removeAllItems();
        comboMonth.addItem("All");

        attendanceService
                .getAvailableMonths(loggedInEmployee)
                .forEach(comboMonth::addItem);
    }

    // Reloads attendance table.
    private void reloadTable() {

        model.setRowCount(0);

        List<Object[]> rows =
                attendanceService.getAttendance(loggedInEmployee);

        for (Object[] row : rows) {
            model.addRow(row);
        }
    }

    // Handles employee check-in.
    private void checkIn() {

        int confirm =
                JOptionPane.showConfirmDialog(
                        this,
                        "Confirm Check In?",
                        "Check In",
                        JOptionPane.YES_NO_OPTION
                );

        if (confirm == JOptionPane.YES_OPTION) {

            attendanceService.checkIn(
                    loggedInEmployee,
                    loggedInLastName,
                    loggedInFirstName
            );

            loadMonths();
            reloadTable();

            JOptionPane.showMessageDialog(
                    this,
                    "Checked In Successfully!"
            );

            topPanel.remove(btnTimeIn);
            topPanel.add(btnTimeOut);
            topPanel.revalidate();
            topPanel.repaint();
        }
    }

    // Handles employee check-out.
    private void checkOut() {

        int confirm =
                JOptionPane.showConfirmDialog(
                        this,
                        "Confirm Check Out?",
                        "Check Out",
                        JOptionPane.YES_NO_OPTION
                );

        if (confirm == JOptionPane.YES_OPTION) {

            attendanceService.checkOut(loggedInEmployee);

            loadMonths();
            reloadTable();

            JOptionPane.showMessageDialog(
                    this,
                    "Checked Out Successfully!"
            );

            topPanel.remove(btnTimeOut);
            topPanel.add(btnTimeIn);
            topPanel.revalidate();
            topPanel.repaint();
        }
    }
}