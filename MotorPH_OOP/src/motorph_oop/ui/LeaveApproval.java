package motorph_oop.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import motorph_oop.model.LeaveRequest;
import motorph_oop.service.LeaveService;

// Leave approval window for Admin.
// Displays leave requests and allows approval or rejection.

public class LeaveApproval extends JFrame {

    // Table components
    private JTable table;
    private DefaultTableModel model;

    // Rejection reason input
    private JTextArea rejectionArea;

    // Service layer
    private final LeaveService leaveService = new LeaveService();

    // Cached leave list
    private List<LeaveRequest> currentLeaves;

    // Constructor: builds UI and loads leave data.
    public LeaveApproval() {

        setTitle("Leave Requests");
        setSize(750, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Table Setup
        model = new DefaultTableModel(
                new String[] {
                        "Type",
                        "Start",
                        "End",
                        "Days",
                        "Reason",
                        "Status"
                },
                0
        );

        table = new JTable(model);

        JScrollPane scrollPane =
                new JScrollPane(table);

        loadTableData();

        // Bottom Panel (Rejection + Buttons
        JPanel bottomPanel =
                new JPanel(new BorderLayout());

        rejectionArea = new JTextArea(3, 20);
        rejectionArea.setBorder(
                BorderFactory.createTitledBorder(
                        "Rejection Reason"
                )
        );

        bottomPanel.add(rejectionArea, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();

        JButton approveBtn = new JButton("Approve");
        JButton rejectBtn = new JButton("Reject");

        buttonPanel.add(approveBtn);
        buttonPanel.add(rejectBtn);

        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // Button Actions
        approveBtn.addActionListener(e -> approveSelected());
        rejectBtn.addActionListener(e -> rejectSelected());
    }

    // Loads leave requests into table.
    private void loadTableData() {

        model.setRowCount(0);

        currentLeaves = leaveService.getAllLeaves();

        for (LeaveRequest r : currentLeaves) {

            model.addRow(new Object[] {
                    r.getLeaveType(),
                    r.getStartDate(),
                    r.getEndDate(),
                    r.getTotalDays(),
                    r.getReason(),
                    r.getStatus()
            });
        }
    }

    // Approves selected leave request.
    private void approveSelected() {

        int row = table.getSelectedRow();

        if (row == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Select a request first."
            );

            return;
        }

        LeaveRequest request =
                currentLeaves.get(row);

        leaveService.approveLeave(request);

        loadTableData();

        JOptionPane.showMessageDialog(
                this,
                "Request Approved."
        );
    }

    // Rejects selected leave request.  
    private void rejectSelected() {

        int row = table.getSelectedRow();

        if (row == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Select a request first."
            );

            return;
        }

        String reason =
                rejectionArea.getText().trim();

        if (reason.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Enter rejection reason."
            );

            return;
        }

        LeaveRequest request =
                currentLeaves.get(row);

        leaveService.rejectLeave(request, reason);

        loadTableData();

        JOptionPane.showMessageDialog(
                this,
                "Request Rejected."
        );
    }
}