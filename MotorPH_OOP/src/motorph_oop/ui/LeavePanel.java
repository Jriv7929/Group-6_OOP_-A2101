
package motorph_oop.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;
import com.github.lgooddatepicker.components.DatePicker;
import motorph_oop.model.LeaveRequest;
import motorph_oop.service.LeaveService;

// Leave application panel for employees.
// Allows submission of leave requests and viewing request status.
 
public class LeavePanel extends JPanel {

    // Service layer
    private final LeaveService leaveService = new LeaveService();

    // Form components
    private JComboBox<String> leaveTypeBox;
    private DatePicker startDatePicker;
    private DatePicker endDatePicker;
    private JTextArea reasonArea;

    // Status table components
    private JTable statusTable;
    private DefaultTableModel statusModel;

    // Constructor: builds UI layout and loads leave status table.
    public LeavePanel() {

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(1200, 700));

        // Heading

        JLabel leaveHeading =
                new JLabel("Leave Application");

        leaveHeading.setFont(
                new Font("Arial", Font.BOLD, 20)
        );

        leaveHeading.setBorder(
                BorderFactory.createEmptyBorder(20, 20, 10, 0)
        );

        add(leaveHeading, BorderLayout.NORTH);

        // Main Container
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(
                new BoxLayout(mainPanel, BoxLayout.Y_AXIS)
        );

        // Form Panel
        JPanel formPanel =
                new JPanel(new GridBagLayout());

        formPanel.setBorder(
                BorderFactory.createEmptyBorder(10, 5, 10, 20)
        );

        formPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // Leave Type
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Leave Type:"), gbc);

        gbc.gridx = 1;

        leaveTypeBox = new JComboBox<>(
                new String[] {
                        "Sick Leave",
                        "Vacation Leave",
                        "Emergency Leave",
                        "Maternity Leave",
                        "Paternity Leave"
                }
        );

        formPanel.add(leaveTypeBox, gbc);

        // Start Date
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Start Date:"), gbc);

        gbc.gridx = 1;
        startDatePicker = new DatePicker();
        formPanel.add(startDatePicker, gbc);

        // End Date
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("End Date:"), gbc);

        gbc.gridx = 1;
        endDatePicker = new DatePicker();
        formPanel.add(endDatePicker, gbc);

        // Reason
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(new JLabel("Reason:"), gbc);

        gbc.gridx = 1;

        reasonArea = new JTextArea(4, 18);
        formPanel.add(new JScrollPane(reasonArea), gbc);

        // Submit Button
        JButton submitButton = new JButton("Submit");

        gbc.gridx = 1;
        gbc.gridy = 4;
        formPanel.add(submitButton, gbc);

        mainPanel.add(formPanel);

        // Status Section
        JLabel statusLabel =
                new JLabel("My Leave Requests");

        statusLabel.setFont(
                new Font("Arial", Font.BOLD, 16)
        );

        statusLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        mainPanel.add(statusLabel);

        statusModel = new DefaultTableModel(
                new String[] {
                        "Type",
                        "Start",
                        "End",
                        "Days",
                        "Status"
                },
                0
        );

        statusTable = new JTable(statusModel);

        JScrollPane statusScroll =
                new JScrollPane(statusTable);

        statusScroll.setPreferredSize(
                new Dimension(900, 150)
        );

        statusScroll.setAlignmentX(Component.LEFT_ALIGNMENT);

        mainPanel.add(statusScroll);

        add(mainPanel, BorderLayout.CENTER);

        // Button Action
        submitButton.addActionListener(e -> submitLeave());

        refreshStatusTable();
    }

    // Submits leave request.
    private void submitLeave() {

        try {

            LeaveRequest request =
                    leaveService.submitLeave(
                            (String) leaveTypeBox.getSelectedItem(),
                            startDatePicker.getDate(),
                            endDatePicker.getDate(),
                            reasonArea.getText().trim()
                    );

            JOptionPane.showMessageDialog(
                    this,
                    "Leave Request Submitted!\nStatus: " +
                            request.getStatus()
            );

            refreshStatusTable();
            clearFields();

        } catch (IllegalArgumentException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage()
            );
        }
    }

    // Reloads leave status table.    
    private void refreshStatusTable() {

        statusModel.setRowCount(0);

        List<LeaveRequest> leaves =
                leaveService.getAllLeaves();

        for (LeaveRequest r : leaves) {

            statusModel.addRow(new Object[] {
                    r.getLeaveType(),
                    r.getStartDate(),
                    r.getEndDate(),
                    r.getTotalDays(),
                    r.getStatus()
            });
        }
    }

    // Clears input fields.
    private void clearFields() {

        startDatePicker.clear();
        endDatePicker.clear();
        reasonArea.setText("");
        leaveTypeBox.setSelectedIndex(0);
    }
}