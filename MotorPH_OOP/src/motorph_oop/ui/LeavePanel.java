/*
package motorph_oop.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import com.github.lgooddatepicker.components.DatePicker;
import motorph_oop.model.LeaveRequest;

public class LeavePanel extends JPanel {

    public static List<LeaveRequest> leaveRequests = new ArrayList<>();
    public static LeavePanel instance;

    private JComboBox<String> leaveTypeBox;
    private DatePicker startDatePicker;
    private DatePicker endDatePicker;
    private JTextArea reasonArea;
    private JButton submitButton;
    private JButton clearButton;

    private JTable statusTable;
    private DefaultTableModel statusModel;

    public LeavePanel() {

        instance = this;

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(1200, 700));

        JLabel leaveHeading = new JLabel("Leave Application");
        leaveHeading.setFont(new Font("Arial", Font.BOLD, 20));
        leaveHeading.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 0));
        add(leaveHeading, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // FORM 
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        formPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Leave Type:"), gbc);

        gbc.gridx = 1;
        leaveTypeBox = new JComboBox<>(new String[]{
                "Sick Leave",
                "Vacation Leave",
                "Emergency Leave",
                "Maternity Leave",
                "Paternity Leave"
        });
        formPanel.add(leaveTypeBox, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Start Date:"), gbc);

        gbc.gridx = 1;
        startDatePicker = new DatePicker();
        formPanel.add(startDatePicker, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("End Date:"), gbc);

        gbc.gridx = 1;
        endDatePicker = new DatePicker();
        formPanel.add(endDatePicker, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Reason:"), gbc);

        gbc.gridx = 1;
        reasonArea = new JTextArea(4, 18);
        JScrollPane scrollPane = new JScrollPane(reasonArea);
        formPanel.add(scrollPane, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        submitButton = new JButton("Submit");
        clearButton = new JButton("Clear");
        buttonPanel.add(submitButton);
        buttonPanel.add(clearButton);

        gbc.gridx = 1; gbc.gridy = 4;
        formPanel.add(buttonPanel, gbc);

        mainPanel.add(formPanel);

        // STATUS TABLE
        JLabel statusLabel = new JLabel("My Leave Requests");
        statusLabel.setFont(new Font("Arial", Font.BOLD, 16));
        statusLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 5, 0));

        statusLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        mainPanel.add(statusLabel);

        statusModel = new DefaultTableModel(
                new String[]{"Type", "Start", "End", "Days", "Status"},
                0
        );

        statusTable = new JTable(statusModel);
        JScrollPane statusScroll = new JScrollPane(statusTable);
        statusScroll.setPreferredSize(new Dimension(900, 150));

        
        statusScroll.setAlignmentX(Component.LEFT_ALIGNMENT);

        mainPanel.add(statusScroll);

        add(mainPanel, BorderLayout.CENTER);

        submitButton.addActionListener(e -> submitLeave());
        clearButton.addActionListener(e -> clearFields());

        refreshStatusTable();
    }

    private void submitLeave() {

        String leaveType = (String) leaveTypeBox.getSelectedItem();
        LocalDate startDate = startDatePicker.getDate();
        LocalDate endDate = endDatePicker.getDate();
        String reason = reasonArea.getText().trim();

        if (startDate == null || endDate == null || reason.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please fill in all fields.");
            return;
        }

        if (endDate.isBefore(startDate)) {
            JOptionPane.showMessageDialog(this,
                    "End date cannot be before start date.");
            return;
        }

        long days = ChronoUnit.DAYS.between(startDate, endDate) + 1;

        LeaveRequest request = new LeaveRequest(
                leaveType, startDate, endDate, days, reason
        );

        leaveRequests.add(request);

        JOptionPane.showMessageDialog(this,
                "Leave Request Submitted!\nStatus: Pending");

        clearFields();
        refreshStatusTable();
    }

    public void refreshStatusTable() {

        statusModel.setRowCount(0);

        for (LeaveRequest request : leaveRequests) {
            statusModel.addRow(new Object[]{
                    request.getLeaveType(),
                    request.getStartDate(),
                    request.getEndDate(),
                    request.getTotalDays(),
                    request.getStatus()
            });
        }
    }

    private void clearFields() {
        startDatePicker.clear();
        endDatePicker.clear();
        reasonArea.setText("");
        leaveTypeBox.setSelectedIndex(0);
    }
}
*/

package motorph_oop.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import com.github.lgooddatepicker.components.DatePicker;
import motorph_oop.model.LeaveRequest;

public class LeavePanel extends JPanel {

    public static List<LeaveRequest> leaveRequests = new ArrayList<>();
    public static LeavePanel instance;

    private static final String FILE_PATH = "src/motorph_oop/resources/leave_requests.csv";

    private JComboBox<String> leaveTypeBox;
    private DatePicker startDatePicker;
    private DatePicker endDatePicker;
    private JTextArea reasonArea;
    private JTable statusTable;
    private DefaultTableModel statusModel;

    public LeavePanel() {

        instance = this;

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(1200, 700));

        JLabel leaveHeading = new JLabel("Leave Application");
        leaveHeading.setFont(new Font("Arial", Font.BOLD, 20));
        leaveHeading.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 0));
        add(leaveHeading, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // FORM
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 20));
        formPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Leave Type:"), gbc);

        gbc.gridx = 1;
        leaveTypeBox = new JComboBox<>(new String[]{
                "Sick Leave",
                "Vacation Leave",
                "Emergency Leave",
                "Maternity Leave",
                "Paternity Leave"
        });
        formPanel.add(leaveTypeBox, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Start Date:"), gbc);

        gbc.gridx = 1;
        startDatePicker = new DatePicker();
        formPanel.add(startDatePicker, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("End Date:"), gbc);

        gbc.gridx = 1;
        endDatePicker = new DatePicker();
        formPanel.add(endDatePicker, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Reason:"), gbc);

        gbc.gridx = 1;
        reasonArea = new JTextArea(4, 18);
        JScrollPane scrollPane = new JScrollPane(reasonArea);
        formPanel.add(scrollPane, gbc);

        JButton submitButton = new JButton("Submit");
        gbc.gridx = 1; gbc.gridy = 4;
        formPanel.add(submitButton, gbc);

        mainPanel.add(formPanel);

        // STATUS TABLE 
        JLabel statusLabel = new JLabel("My Leave Requests");
        statusLabel.setFont(new Font("Arial", Font.BOLD, 16));
        statusLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(statusLabel);

        statusModel = new DefaultTableModel(
                new String[]{"Type", "Start", "End", "Days", "Status"},
                0
        );

        statusTable = new JTable(statusModel);
        JScrollPane statusScroll = new JScrollPane(statusTable);
        statusScroll.setPreferredSize(new Dimension(900, 150));
        statusScroll.setAlignmentX(Component.LEFT_ALIGNMENT);

        mainPanel.add(statusScroll);

        add(mainPanel, BorderLayout.CENTER);

        loadFromCSV();
        refreshStatusTable();

        submitButton.addActionListener(e -> submitLeave());
    }

    private void submitLeave() {

        String leaveType = (String) leaveTypeBox.getSelectedItem();
        LocalDate startDate = startDatePicker.getDate();
        LocalDate endDate = endDatePicker.getDate();
        String reason = reasonArea.getText().trim();

        if (startDate == null || endDate == null || reason.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please fill in all fields.");
            return;
        }

        if (endDate.isBefore(startDate)) {
            JOptionPane.showMessageDialog(this,
                    "End date cannot be before start date.");
            return;
        }

        long days = ChronoUnit.DAYS.between(startDate, endDate) + 1;

        LeaveRequest request = new LeaveRequest(
                leaveType, startDate, endDate, days, reason
        );

        leaveRequests.add(request);
        saveToCSV();

        JOptionPane.showMessageDialog(this,
                "Leave Request Submitted!\nStatus: Pending");

        refreshStatusTable();
    }

    public void refreshStatusTable() {

        statusModel.setRowCount(0);

        for (LeaveRequest r : leaveRequests) {
            statusModel.addRow(new Object[]{
                    r.getLeaveType(),
                    r.getStartDate(),
                    r.getEndDate(),
                    r.getTotalDays(),
                    r.getStatus()
            });
        }
    }

    private void loadFromCSV() {

        leaveRequests.clear();

        File file = new File(FILE_PATH);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            String line;

            while ((line = reader.readLine()) != null) {

                String[] parts = line.split(",");

                LeaveRequest request = new LeaveRequest(
                        parts[0],
                        LocalDate.parse(parts[1]),
                        LocalDate.parse(parts[2]),
                        Long.parseLong(parts[3]),
                        parts[4]
                );

                request.setStatus(parts[5]);
                leaveRequests.add(request);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveToCSV() {

        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {

            for (LeaveRequest r : leaveRequests) {
                writer.println(
                        r.getLeaveType() + "," +
                        r.getStartDate() + "," +
                        r.getEndDate() + "," +
                        r.getTotalDays() + "," +
                        r.getReason().replace(",", " ") + "," +
                        r.getStatus()
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}