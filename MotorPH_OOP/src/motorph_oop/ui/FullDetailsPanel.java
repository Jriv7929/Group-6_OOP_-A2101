
package motorph_oop.ui;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Scanner;
import javax.swing.table.DefaultTableModel;
import motorph_oop.util.CSVHandler;
import motorph_oop.util.Constants;
import motorph_oop.model.Employee;
import motorph_oop.model.ProbationaryEmployee;
import motorph_oop.model.RegularEmployee;
import motorph_oop.utils.PayrollService;
import motorph_oop.utils.PayrollService.PayrollResult;

public class FullDetailsPanel extends JPanel {
    private String employeeNum;
    private double hourlyRate;
    private double riceSubsidy;
    private double phoneAllowance;
    private double clothingAllowance;

    private JTable attendanceTable;
    private DefaultTableModel attendanceModel;
    private JLabel totalSalary;
    private JComboBox<String> comboMonth;

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

    private JPanel bottomPanel = new JPanel(new BorderLayout());
    private JPanel tablePanel = new JPanel();
    private JPanel centerContainer = new JPanel(new BorderLayout());
    private JPanel buttons = new JPanel(new GridLayout(4, 1, 20, 20));

    public FullDetailsPanel() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(1200, getHeight()));

        // Attendance table
        attendanceModel = new DefaultTableModel(Constants.ATTENDANCE_COLUMNS, 0);
        attendanceTable = new JTable(attendanceModel);
        JScrollPane tableScroll = new JScrollPane(attendanceTable);
        tableScroll.setPreferredSize(new Dimension(950, 150));
        tablePanel.add(tableScroll);

        totalSalary = new JLabel("Total Salary: ₱0.00");
        totalSalary.setFont(new Font("Arial", Font.BOLD, 16));

        comboMonth = new JComboBox<>();
        comboMonth.addItem("All");
        comboMonth.addActionListener(e -> reloadTable());

        JPanel topPanel = new JPanel(new GridLayout(1, 3, 5, 5));
        topPanel.add(new JLabel("Filter by Month:"));
        topPanel.add(comboMonth);
        topPanel.add(totalSalary);
        add(topPanel, BorderLayout.NORTH);

        // Form panels
        JPanel formPanel = new JPanel(new GridLayout(3, 1, 5, 2));
        formPanel.add(UIUtils.createEmployeeInfoPanel(empNo, lastName, firstName, status, position, supervisor));
        formPanel.add(UIUtils.createPersonalInfoPanel(birthday, address, phone, sss, philhealth, tin, pagibig));
        formPanel.add(UIUtils.createFinancialInfoPanel(basicSalary, riceSubsidyField, phoneAllowanceField, clothingAllowanceField, grossRate, hourlyRateField));

        // Buttons
        JButton btnCalculate = UIUtils.createButton("Calculate Salary", new Color(52, 58, 235), Color.WHITE);
        JButton setPasswordBtn = UIUtils.createButton("Set Password", new Color(235, 122, 52), Color.WHITE);
        JButton updateBtn = UIUtils.createButton("Update Employee", new Color(0, 180, 0), Color.WHITE);
        JButton deleteBtn = UIUtils.createButton("Delete Employee", new Color(168, 0, 0), Color.WHITE);

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

    public void setEmployeeNo(String employeeNo) {
        this.employeeNum = employeeNo;
        searchEmployee();
        loadAvailableMonths();
        reloadTable();
    }

    private void searchEmployee() {
        try (BufferedReader reader = new BufferedReader(new FileReader(Constants.EMPLOYEE_DATA_CSV))) {
            String line;
            boolean skipHeader = true;

            while ((line = reader.readLine()) != null) {
                if (skipHeader) {
                    skipHeader = false;
                    continue;
                }

                String[] parts = line.split(",", -1);
                if (parts.length >= 19 && parts[0].trim().equals(employeeNum)) {
                    empNo.setText(parts[0].trim());
                    lastName.setText(parts[1].trim());
                    firstName.setText(parts[2].trim());
                    birthday.setText(parts[3].trim());
                    address.setText(parts[4].trim());
                    phone.setText(parts[5].trim());
                    sss.setText(parts[6].trim());
                    philhealth.setText(new BigDecimal(parts[7].trim()).toPlainString());
                    tin.setText(parts[8].trim());
                    pagibig.setText(new BigDecimal(parts[9].trim()).toPlainString());
                    status.setText(parts[10].trim());
                    position.setText(parts[11].trim());
                    supervisor.setText(parts[12].trim());
                    basicSalary.setText(parts[13].trim());
                    riceSubsidyField.setText(parts[14].trim());
                    phoneAllowanceField.setText(parts[15].trim());
                    clothingAllowanceField.setText(parts[16].trim());
                    grossRate.setText(parts[17].trim());
                    hourlyRateField.setText(parts[18].trim());

                    hourlyRate = Double.parseDouble(hourlyRateField.getText());
                    riceSubsidy = Double.parseDouble(riceSubsidyField.getText());
                    phoneAllowance = Double.parseDouble(phoneAllowanceField.getText());
                    clothingAllowance = Double.parseDouble(clothingAllowanceField.getText());
                    break;
                }
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error reading file: " + ex.getMessage());
        }
    }

    private void loadAvailableMonths() {
        comboMonth.removeAllItems();
        comboMonth.addItem("All");
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(Constants.DATE_FORMAT);
        CSVHandler.loadAvailableMonths(Constants.ATTENDANCE_CSV, employeeNum, dateFormat)
                .forEach(comboMonth::addItem);
    }

    private void reloadTable() {
        attendanceModel.setRowCount(0);
        double totalDailySalary = 0.0;
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(Constants.DATE_FORMAT);
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern(Constants.TIME_FORMAT);
        String selectedMonth = (String) comboMonth.getSelectedItem();

        try (Scanner scanner = new Scanner(new File(Constants.ATTENDANCE_CSV), "UTF-8")) {
            boolean skipHeader = true;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (skipHeader) {
                    skipHeader = false;
                    continue;
                }

                String[] parts = line.split(",", -1);
                if (parts.length >= 6 && parts[0].trim().equals(employeeNum)) {
                    try {
                        LocalDate date = LocalDate.parse(parts[3].trim(), dateFormat);
                        String monthYear = date.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH) + " " + date.getYear();

                        if (!"All".equals(selectedMonth) && !monthYear.equals(selectedMonth)) continue;

                        LocalTime logIn = LocalTime.parse(parts[4].trim(), timeFormat);
                        LocalTime logOut = parts[5].trim().isEmpty() ? LocalTime.now() : LocalTime.parse(parts[5].trim(), timeFormat);
                        double hoursWorked = Duration.between(logIn, logOut).toMinutes() / 60.0;
                        double dailySalary = hoursWorked * hourlyRate;
                        totalDailySalary += dailySalary;

                        attendanceModel.addRow(new Object[]{
                            parts[3].trim(), parts[4].trim(), parts[5].trim(),
                            String.format("%.2f", hoursWorked),
                            String.format("₱%.2f", dailySalary)
                        });
                    } catch (Exception e) {
                        // Skip malformed entries
                    }
                }
            }
            totalSalary.setText("Total Salary: ₱" + String.format("%.2f", totalDailySalary));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading CSV: " + e.getMessage());
        }
    }

   
    private void calculateSalary() {

    Employee employee;

    // Choose correct subclass based on status
    if ("Regular".equalsIgnoreCase(status.getText().trim())) {
        employee = new RegularEmployee();
    } else {
        employee = new ProbationaryEmployee();
    }

    // Set basic fields
    employee.setEmpNo(empNo.getText().trim());

    try {
        employee.setBasicSalary(
                validateNumber(basicSalary.getText().trim(), "Basic Salary"));

        employee.setRiceSubsidy(
                validateNumber(riceSubsidyField.getText().trim(), "Rice Subsidy"));

        employee.setPhoneAllowance(
                validateNumber(phoneAllowanceField.getText().trim(), "Phone Allowance"));

        employee.setClothingAllowance(
                validateNumber(clothingAllowanceField.getText().trim(), "Clothing Allowance"));

        employee.setGrossRate(
                validateNumber(grossRate.getText().trim(), "Gross Rate"));

        employee.setHourlyRate(
                validateNumber(hourlyRateField.getText().trim(), "Hourly Rate"));

    } catch (IllegalArgumentException ex) {
        JOptionPane.showMessageDialog(this,
                ex.getMessage(),
                "Input Error",
                JOptionPane.ERROR_MESSAGE);
        return; // stop calculation
    }

    String selectedMonth = (String) comboMonth.getSelectedItem();

    PayrollResult result =
            PayrollService.calculatePayroll(
                    employee,
                    Constants.ATTENDANCE_CSV,
                    selectedMonth);

    // Use GETTERS (not direct field access)
    // Format numbers with commas
        String salaryDetails = String.format(
        "========================================\n" +
        "              MOTORPH PAYSLIP\n" +
        "========================================\n" +
        " Employee No : %-20s\n" +
        " Month       : %-20s\n" +
        "----------------------------------------\n" +
        " GROSS PAY\n" +
        "----------------------------------------\n" +
        " Gross Salary        : ₱%,12.2f\n" +
        "----------------------------------------\n" +
        " DEDUCTIONS\n" +
        "----------------------------------------\n" +
        " SSS                 : ₱%,12.2f\n" +
        " PhilHealth          : ₱%,12.2f\n" +
        " Pag-IBIG            : ₱%,12.2f\n" +
        " Withholding Tax     : ₱%,12.2f\n" +
        "----------------------------------------\n" +
        " NET PAY             : ₱%,12.2f\n" +
        "========================================\n",
        employee.getEmpNo(),
        selectedMonth,
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

    private void rewriteField(String fieldName, String cleaned) {
    switch (fieldName) {
        case "Basic Salary": basicSalary.setText(cleaned); break;
        case "Rice Subsidy": riceSubsidyField.setText(cleaned); break;
        case "Phone Allowance": phoneAllowanceField.setText(cleaned); break;
        case "Clothing Allowance": clothingAllowanceField.setText(cleaned); break;
        case "Gross Rate": grossRate.setText(cleaned); break;
        case "Hourly Rate": hourlyRateField.setText(cleaned); break;
    }
}
        private double validateNumber(String value, String fieldName) {
    if (value.isEmpty())
        throw new IllegalArgumentException(fieldName + " cannot be empty.");

    // allow digits and optional decimal, but we will remove decimal later
    if (!value.matches("\\d+(\\.\\d+)?"))
        throw new IllegalArgumentException(fieldName + " Only numbers allowed, no letters, no negative numbers.");

    double num = Double.parseDouble(value);

    if (num < 0)
        throw new IllegalArgumentException(fieldName + " cannot be negative.");

    // Remove decimals (round to nearest whole number)
    long wholeNumber = Math.round(num);

    // Update the text field to the cleaned whole number
    rewriteField(fieldName, String.valueOf(wholeNumber));

    return wholeNumber; // store as whole number
}
        
    private void updateEmployee() {

    Employee data;

    // Choose subclass based on status
    if ("Regular".equalsIgnoreCase(status.getText().trim())) {
        data = new RegularEmployee();
    } else {
        data = new ProbationaryEmployee();
    }

    // Set common fields using setters
    data.setEmpNo(empNo.getText().trim());
    data.setLastName(lastName.getText().trim());
    data.setFirstName(firstName.getText().trim());
    data.setBirthday(birthday.getText().trim());
    data.setAddress(address.getText().trim());
    data.setPhone(phone.getText().trim());
    data.setSss(sss.getText().trim());
    data.setPhilhealth(philhealth.getText().trim());
    data.setTin(tin.getText().trim());
    data.setPagibig(pagibig.getText().trim());
    data.setStatus(status.getText().trim());
    data.setPosition(position.getText().trim());
    data.setSupervisor(supervisor.getText().trim());

    try {
        data.setBasicSalary(validateNumber(basicSalary.getText().trim(), "Basic Salary"));
        data.setRiceSubsidy(validateNumber(riceSubsidyField.getText().trim(), "Rice Subsidy"));
        data.setPhoneAllowance(validateNumber(phoneAllowanceField.getText().trim(), "Phone Allowance"));
        data.setClothingAllowance(validateNumber(clothingAllowanceField.getText().trim(), "Clothing Allowance"));
        data.setGrossRate(validateNumber(grossRate.getText().trim(), "Gross Rate"));
        data.setHourlyRate(validateNumber(hourlyRateField.getText().trim(), "Hourly Rate"));
    } catch (IllegalArgumentException ex) {
        JOptionPane.showMessageDialog(this,
                ex.getMessage(),
                "Invalid Input",
                JOptionPane.ERROR_MESSAGE);
        return;
    }

    int confirm = JOptionPane.showConfirmDialog(
            null,
            "Confirm updated details?",
            "Confirm Update",
            JOptionPane.YES_NO_OPTION);

    if (confirm == JOptionPane.YES_OPTION) {

        // Validation using GETTERS instead of direct field access
        if (data.getLastName().isEmpty() ||
            data.getFirstName().isEmpty() ||
            data.getStatus().isEmpty() ||
            data.getPosition().isEmpty() ||
            data.getSupervisor().isEmpty() ||
            data.getBirthday().isEmpty() ||
            data.getAddress().isEmpty() ||
            data.getPhone().isEmpty() ||
            data.getSss().isEmpty() ||
            data.getPhilhealth().isEmpty() ||
            data.getTin().isEmpty() ||
            data.getPagibig().isEmpty() ||
            basicSalary.getText().isEmpty() ||
            riceSubsidyField.getText().isEmpty() ||
            phoneAllowanceField.getText().isEmpty() ||
            clothingAllowanceField.getText().isEmpty() ||
            grossRate.getText().isEmpty() ||
            hourlyRateField.getText().isEmpty()) {

            JOptionPane.showMessageDialog(this,
                    "Please fill in all fields when updating.",
                    "Missing Data",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        CSVHandler.updateCSV(
                Constants.EMPLOYEE_DATA_CSV,
                data.getEmpNo(),
                data.toCSVArray());

        JOptionPane.showMessageDialog(this,
                "Employee details updated.");
    }
}

    private void deleteSelectedRow() {
        int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            CSVHandler.deleteFromCSV(Constants.EMPLOYEE_DATA_CSV, employeeNum);
            JOptionPane.showMessageDialog(this, "Employee deleted.");
            clearFields();
        }
    }

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
