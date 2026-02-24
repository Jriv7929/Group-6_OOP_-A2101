/*
package motorph_oop.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import motorph_oop.model.LeaveRequest;

public class LeaveApproval extends JFrame {

    private JTable table;
    private DefaultTableModel model;
    private JTextArea rejectionArea;

    public LeaveApproval() {

        setTitle("Leave Requests");
        setSize(750, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        model = new DefaultTableModel(
                new String[]{"Type", "Start", "End", "Days", "Reason", "Status"},
                0
        );

        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        loadTableData();

        JPanel bottomPanel = new JPanel(new BorderLayout());

        rejectionArea = new JTextArea(3, 20);
        rejectionArea.setBorder(
                BorderFactory.createTitledBorder("Rejection Reason"));
        bottomPanel.add(rejectionArea, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();

        JButton approveBtn = new JButton("Approve");
        JButton rejectBtn = new JButton("Reject");

        buttonPanel.add(approveBtn);
        buttonPanel.add(rejectBtn);

        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // APPROVE
        approveBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this,
                        "Select a request first.");
                return;
            }

            LeaveRequest request =
                    LeavePanel.leaveRequests.get(row);

            request.setStatus("Approved");
            model.setValueAt("Approved", row, 5);

            // Refresh employee panel
            if (LeavePanel.instance != null) {
                LeavePanel.instance.refreshStatusTable();
            }

            JOptionPane.showMessageDialog(this,
                    "Request Approved.");
        });

        // REJECT
        rejectBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this,
                        "Select a request first.");
                return;
            }

            String reason = rejectionArea.getText().trim();
            if (reason.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Enter rejection reason.");
                return;
            }

            LeaveRequest request =
                    LeavePanel.leaveRequests.get(row);

            request.setStatus("Rejected");
            model.setValueAt("Rejected", row, 5);

            // Refresh employee panel
            if (LeavePanel.instance != null) {
                LeavePanel.instance.refreshStatusTable();
            }

            JOptionPane.showMessageDialog(this,
                    "Request Rejected.");
        });
    }

    private void loadTableData() {

        model.setRowCount(0);

        for (LeaveRequest request : LeavePanel.leaveRequests) {
            model.addRow(new Object[]{
                    request.getLeaveType(),
                    request.getStartDate(),
                    request.getEndDate(),
                    request.getTotalDays(),
                    request.getReason(),
                    request.getStatus()
            });
        }
    }
}
*/

package motorph_oop.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import motorph_oop.model.LeaveRequest;

public class LeaveApproval extends JFrame {

    private JTable table;
    private DefaultTableModel model;
    private JTextArea rejectionArea;

    public LeaveApproval() {

        setTitle("Leave Requests");
        setSize(750, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        model = new DefaultTableModel(
                new String[]{"Type", "Start", "End", "Days", "Reason", "Status"},
                0
        );

        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        loadTableData();

        JPanel bottomPanel = new JPanel(new BorderLayout());

        rejectionArea = new JTextArea(3, 20);
        rejectionArea.setBorder(
                BorderFactory.createTitledBorder("Rejection Reason"));
        bottomPanel.add(rejectionArea, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();

        JButton approveBtn = new JButton("Approve");
        JButton rejectBtn = new JButton("Reject");

        buttonPanel.add(approveBtn);
        buttonPanel.add(rejectBtn);

        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        approveBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this,
                        "Select a request first.");
                return;
            }

            LeaveRequest request = LeavePanel.leaveRequests.get(row);
            request.setStatus("Approved");
            model.setValueAt("Approved", row, 5);

            LeavePanel.saveToCSV();

            if (LeavePanel.instance != null) {
                LeavePanel.instance.refreshStatusTable();
            }

            JOptionPane.showMessageDialog(this,
                    "Request Approved.");
        });

        rejectBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this,
                        "Select a request first.");
                return;
            }

            String reason = rejectionArea.getText().trim();
            if (reason.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Enter rejection reason.");
                return;
            }

            LeaveRequest request = LeavePanel.leaveRequests.get(row);
            request.setStatus("Rejected");
            model.setValueAt("Rejected", row, 5);

            LeavePanel.saveToCSV();

            if (LeavePanel.instance != null) {
                LeavePanel.instance.refreshStatusTable();
            }

            JOptionPane.showMessageDialog(this,
                    "Request Rejected.");
        });
    }

    private void loadTableData() {

        model.setRowCount(0);

        for (LeaveRequest r : LeavePanel.leaveRequests) {
            model.addRow(new Object[]{
                    r.getLeaveType(),
                    r.getStartDate(),
                    r.getEndDate(),
                    r.getTotalDays(),
                    r.getReason(),
                    r.getStatus()
            });
        }
    }
}