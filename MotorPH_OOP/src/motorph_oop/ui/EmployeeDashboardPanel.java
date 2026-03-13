
package motorph_oop.ui;

import motorph_oop.model.Employee;
import motorph_oop.service.EmployeeService;
import motorph_oop.util.Constants;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

// Displays the Employee Database table and basic employee information form.

public class EmployeeDashboardPanel extends JPanel {

    // Service layer
    private final EmployeeService employeeService = new EmployeeService();

    // Table components
    private JTable table;
    private DefaultTableModel model;

    // Stores loaded employees
    private List<Employee> employeeList;

    // Bottom panel (form area)
    private JPanel bottomPanel = new JPanel(new BorderLayout());
    private JPanel formPanel = new JPanel(new GridLayout(3, 1, 5, 2));

    // Employee information fields
    private JTextField empNo = UIUtils.createTextField(false);
    private JTextField lastName = UIUtils.createTextField(true);
    private JTextField firstName = UIUtils.createTextField(true);
    private JTextField status = UIUtils.createTextField(true);
    private JTextField position = UIUtils.createTextField(true);
    private JTextField supervisor = UIUtils.createTextField(true);

    // Constructor: builds UI and loads employee data.
    public EmployeeDashboardPanel() {

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(1200, getHeight()));

        // Header
        JLabel heading =
                UIUtils.createHeaderLabel("Employee Database");

        add(heading, BorderLayout.NORTH);

        // Table setup
        model = new DefaultTableModel(Constants.EMPLOYEE_COLUMNS, 0);
        table = new JTable(model);

        JScrollPane tableScroll = new JScrollPane(table);
        tableScroll.setPreferredSize(new Dimension(1000, 300));

        add(tableScroll, BorderLayout.CENTER);

        // Form panel
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

        bottomPanel.add(formPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // Table selection listener
        table.getSelectionModel().addListSelectionListener(e -> {

            int selectedRow = table.getSelectedRow();

            if (selectedRow >= 0 &&
                employeeList != null) {

                Employee selectedEmployee =
                        employeeList.get(selectedRow);

                populateForm(selectedEmployee);
            }
        });

        // Load employee data
        loadEmployees();
    }

    // Loads employee data into table.
    private void loadEmployees() {

        model.setRowCount(0);

        employeeList = employeeService.getEmployees();

        for (Employee emp : employeeList) {

            model.addRow(new Object[] {
                    emp.getEmpNo(),
                    emp.getLastName(),
                    emp.getFirstName(),
                    emp.getBirthday(),
                    emp.getAddress(),
                    emp.getPhone(),
                    emp.getSss(),
                    emp.getPhilhealth(),
                    emp.getTin(),
                    emp.getPagibig(),
                    emp.getStatus(),
                    emp.getPosition(),
                    emp.getSupervisor(),
                    emp.getBasicSalary(),
                    emp.getRiceSubsidy(),
                    emp.getPhoneAllowance(),
                    emp.getClothingAllowance(),
                    emp.getGrossRate(),
                    emp.getHourlyRate()
            });
        }
    }

    // Populates form fields with selected employee data.
    private void populateForm(Employee employee) {

        empNo.setText(employee.getEmpNo());
        lastName.setText(employee.getLastName());
        firstName.setText(employee.getFirstName());
        status.setText(employee.getStatus());
        position.setText(employee.getPosition());
        supervisor.setText(employee.getSupervisor());
    }

    // Reloads employee list and clears form.
    public void reloadEmployees() {

        loadEmployees();
        clearFields();
    }

    // Returns selected employee number from table.
    public String getSelectedEmployeeNo() {

        int selectedRow = table.getSelectedRow();

        if (selectedRow >= 0 &&
            employeeList != null) {

            return employeeList
                    .get(selectedRow)
                    .getEmpNo();
        }

        return null;
    }

    // Clears form fields.
    public void clearFields() {

        empNo.setText("");
        lastName.setText("");
        firstName.setText("");
        status.setText("");
        position.setText("");
        supervisor.setText("");
    }
}