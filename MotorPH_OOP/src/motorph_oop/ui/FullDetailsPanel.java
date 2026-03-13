
package motorph_oop.ui;

import motorph_oop.dao.AttendanceDAO;
import motorph_oop.dao.EmployeeDAO;
import motorph_oop.model.Employee;
import motorph_oop.service.EmployeeService;
import motorph_oop.util.Constants;
import motorph_oop.utils.PayrollService;
import motorph_oop.utils.PayrollService.PayrollResult;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.List;

// Displays full employee details,attendance records, and payroll computation.
 
public class FullDetailsPanel extends JPanel {

    // Selected employee number
    private String employeeNum;

    // Services and DAOs
    private final EmployeeService employeeService = new EmployeeService();
    private final EmployeeDAO employeeDAO = new EmployeeDAO();
    private final AttendanceDAO attendanceDAO = new AttendanceDAO();

    // Attendance table
    private JTable attendanceTable;
    private DefaultTableModel attendanceModel;

    // UI components
    private JLabel totalSalary;
    private JComboBox<String> comboMonth;

    // Employee fields
    private JTextField empNo = UIUtils.createTextField(false);
    private JTextField lastName = UIUtils.createTextField(true);
    private JTextField firstName = UIUtils.createTextField(true);
    private JTextField status = UIUtils.createTextField(true);
    private JTextField position = UIUtils.createTextField(true);
    private JTextField supervisor = UIUtils.createTextField(true);
    private JTextField birthday = UIUtils.createTextField(true);
    private JTextField address = UIUtils.createTextField(true);
    private JTextField phone = UIUtils.createTextField(true);
    private JTextField sss = UIUtils.createTextField(true);
    private JTextField philhealth = UIUtils.createTextField(true);
    private JTextField tin = UIUtils.createTextField(true);
    private JTextField pagibig = UIUtils.createTextField(true);
    private JTextField basicSalary = UIUtils.createTextField(true);
    private JTextField riceSubsidyField = UIUtils.createTextField(true);
    private JTextField phoneAllowanceField = UIUtils.createTextField(true);
    private JTextField clothingAllowanceField = UIUtils.createTextField(true);
    private JTextField grossRate = UIUtils.createTextField(true);
    private JTextField hourlyRateField = UIUtils.createTextField(true);

    // Panels
    private JPanel bottomPanel = new JPanel(new BorderLayout());
    private JPanel tablePanel = new JPanel();
    private JPanel centerContainer = new JPanel(new BorderLayout());
    private JPanel buttons = new JPanel(new GridLayout(4, 1, 20, 20));

    // Constructor: builds layout and components.
    public FullDetailsPanel() {

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(1200, getHeight()));

        // Attendance Table
        attendanceModel =
                new DefaultTableModel(Constants.ATTENDANCE_COLUMNS, 0);

        attendanceTable = new JTable(attendanceModel);

        JScrollPane tableScroll =
                new JScrollPane(attendanceTable);

        tableScroll.setPreferredSize(
                new Dimension(950, 150)
        );

        tablePanel.add(tableScroll);

        // Top Panel (Month Filter + Salary)
        totalSalary = new JLabel("Total Salary: ₱0.00");
        totalSalary.setFont(new Font("Arial", Font.BOLD, 16));

        comboMonth = new JComboBox<>();
        comboMonth.addItem("All");
        comboMonth.addActionListener(e -> reloadTable());

        JPanel topPanel =
                new JPanel(new GridLayout(1, 3, 5, 5));

        topPanel.add(new JLabel("Filter by Month:"));
        topPanel.add(comboMonth);
        topPanel.add(totalSalary);

        add(topPanel, BorderLayout.NORTH);

        // Form Panels
        JPanel formPanel =
                new JPanel(new GridLayout(3, 1, 5, 2));

        formPanel.add(
                UIUtils.createEmployeeInfoPanel(
                        empNo,
                        lastName,
                        firstName,
                        status,
                        position,
                        supervisor
                )
        );

        formPanel.add(
                UIUtils.createPersonalInfoPanel(
                        birthday,
                        address,
                        phone,
                        sss,
                        philhealth,
                        tin,
                        pagibig
                )
        );

        formPanel.add(
                UIUtils.createFinancialInfoPanel(
                        basicSalary,
                        riceSubsidyField,
                        phoneAllowanceField,
                        clothingAllowanceField,
                        grossRate,
                        hourlyRateField
                )
        );

        // Buttons   
        JButton btnCalculate =
                UIUtils.createButton(
                        "Calculate Salary",
                        new Color(52, 58, 235),
                        Color.WHITE
                );

        JButton setPasswordBtn =
                UIUtils.createButton(
                        "Set Password",
                        new Color(235, 122, 52),
                        Color.WHITE
                );

        JButton updateBtn =
                UIUtils.createButton(
                        "Update Employee",
                        new Color(0, 180, 0),
                        Color.WHITE
                );

        JButton deleteBtn =
                UIUtils.createButton(
                        "Delete Employee",
                        new Color(168, 0, 0),
                        Color.WHITE
                );

        btnCalculate.addActionListener(e -> calculateSalary());
        updateBtn.addActionListener(e -> updateEmployee());
        deleteBtn.addActionListener(e -> deleteSelectedRow());

        buttons.add(btnCalculate);
        buttons.add(setPasswordBtn);
        buttons.add(updateBtn);
        buttons.add(deleteBtn);

        bottomPanel.add(formPanel, BorderLayout.CENTER);
        bottomPanel.add(buttons, BorderLayout.EAST);

        centerContainer.add(tablePanel, BorderLayout.CENTER);

        add(bottomPanel, BorderLayout.SOUTH);
        add(centerContainer, BorderLayout.CENTER);
    }

    // Sets employee number and reloads data.  
    public void setEmployeeNo(String employeeNo) {

        this.employeeNum = employeeNo;

        loadEmployee();
        loadAvailableMonths();
        reloadTable();
    }

    //Loads employee details into form fields.
    private void loadEmployee() {

        Employee employee =
                employeeDAO.findEmployeeById(employeeNum);

        if (employee == null) {
            return;
        }

        empNo.setText(employee.getEmpNo());
        lastName.setText(employee.getLastName());
        firstName.setText(employee.getFirstName());
        birthday.setText(employee.getBirthday());
        address.setText(employee.getAddress());
        phone.setText(employee.getPhone());
        sss.setText(employee.getSss());
        philhealth.setText(employee.getPhilhealth());
        tin.setText(employee.getTin());
        pagibig.setText(employee.getPagibig());
        status.setText(employee.getStatus());
        position.setText(employee.getPosition());
        supervisor.setText(employee.getSupervisor());
        basicSalary.setText(String.valueOf(employee.getBasicSalary()));
        riceSubsidyField.setText(String.valueOf(employee.getRiceSubsidy()));
        phoneAllowanceField.setText(String.valueOf(employee.getPhoneAllowance()));
        clothingAllowanceField.setText(String.valueOf(employee.getClothingAllowance()));
        grossRate.setText(String.valueOf(employee.getGrossRate()));
        hourlyRateField.setText(String.valueOf(employee.getHourlyRate()));
    }

    // Loads available attendance months.
    private void loadAvailableMonths() {

        comboMonth.removeAllItems();
        comboMonth.addItem("All");

        attendanceDAO.getAvailableMonths(employeeNum)
                .forEach(comboMonth::addItem);
    }

    // Reloads attendance table and recalculates total salary.
    private void reloadTable() {

        attendanceModel.setRowCount(0);

        List<Object[]> rows =
                attendanceDAO.getAttendanceByEmployeeAndMonth(
                        employeeNum,
                        (String) comboMonth.getSelectedItem(),
                        Double.parseDouble(hourlyRateField.getText())
                );

        double total = 0;

        for (Object[] row : rows) {

            total += (double) row[4];

            attendanceModel.addRow(new Object[] {
                    row[0],
                    row[1],
                    row[2],
                    String.format("%.2f", row[3]),
                    "₱" + String.format("%.2f", row[4])
            });
        }

        totalSalary.setText(
                "Total Salary: ₱" +
                new DecimalFormat("#,##0.00").format(total)
        );
    }

    // Calculates payroll and displays payslip.
    private void calculateSalary() {

        Employee employee =
                employeeDAO.findEmployeeById(employeeNum);

        if (employee == null) {
            return;
        }

        PayrollResult result =
                PayrollService.calculatePayroll(
                        employee,
                        Constants.ATTENDANCE_CSV,
                        (String) comboMonth.getSelectedItem()
                );

        String salaryDetails =
                String.format(
                        "========================================\n" +
                        "              MOTORPH PAYSLIP\n" +
                        "========================================\n" +
                        " Employee No : %-20s\n" +
                        " Month       : %-20s\n" +
                        "----------------------------------------\n" +
                        " Gross Salary        : ₱%,12.2f\n" +
                        " SSS                 : ₱%,12.2f\n" +
                        " PhilHealth          : ₱%,12.2f\n" +
                        " Pag-IBIG            : ₱%,12.2f\n" +
                        " Withholding Tax     : ₱%,12.2f\n" +
                        "----------------------------------------\n" +
                        " NET PAY             : ₱%,12.2f\n" +
                        "========================================\n",
                        employee.getEmpNo(),
                        comboMonth.getSelectedItem(),
                        result.getGrossSalary(),
                        result.getSssDeduction(),
                        result.getPhilhealthDeduction(),
                        result.getPagibigDeduction(),
                        result.getWithholdingTax(),
                        result.getNetSalary()
                );

        JTextArea textArea = new JTextArea(salaryDetails);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        textArea.setEditable(false);

        JOptionPane.showMessageDialog(
                this,
                textArea,
                "Payroll Summary",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    // Updates employee details.
    private void updateEmployee() {

        Employee updated =
                employeeDAO.findEmployeeById(employeeNum);

        if (updated == null) {
            return;
        }

        updated.setLastName(lastName.getText());
        updated.setFirstName(firstName.getText());
        updated.setBirthday(birthday.getText());
        updated.setAddress(address.getText());
        updated.setPhone(phone.getText());
        updated.setSss(sss.getText());
        updated.setPhilhealth(philhealth.getText());
        updated.setTin(tin.getText());
        updated.setPagibig(pagibig.getText());
        updated.setStatus(status.getText());
        updated.setPosition(position.getText());
        updated.setSupervisor(supervisor.getText());

        updated.setBasicSalary(Double.parseDouble(basicSalary.getText()));
        updated.setRiceSubsidy(Double.parseDouble(riceSubsidyField.getText()));
        updated.setPhoneAllowance(Double.parseDouble(phoneAllowanceField.getText()));
        updated.setClothingAllowance(Double.parseDouble(clothingAllowanceField.getText()));
        updated.setGrossRate(Double.parseDouble(grossRate.getText()));
        updated.setHourlyRate(Double.parseDouble(hourlyRateField.getText()));

        employeeDAO.updateEmployee(updated);

        JOptionPane.showMessageDialog(this, "Employee updated.");
    }

    // Deletes selected employee.
    private void deleteSelectedRow() {

        int confirm =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to delete this?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION
                );

        if (confirm == JOptionPane.YES_OPTION) {

            employeeDAO.deleteEmployee(employeeNum);

            JOptionPane.showMessageDialog(this, "Employee deleted.");
            clearFields();
        }
    }

    // Clears all form fields.
    private void clearFields() {

        empNo.setText("");
        lastName.setText("");
        firstName.setText("");
        status.setText("");
        position.setText("");
        supervisor.setText("");
        birthday.setText("");
        address.setText("");
        phone.setText("");
        sss.setText("");
        philhealth.setText("");
        tin.setText("");
        pagibig.setText("");
        basicSalary.setText("");
        riceSubsidyField.setText("");
        phoneAllowanceField.setText("");
        clothingAllowanceField.setText("");
        grossRate.setText("");
        hourlyRateField.setText("");
    }
}