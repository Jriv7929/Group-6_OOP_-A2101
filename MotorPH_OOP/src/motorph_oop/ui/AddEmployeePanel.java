
package motorph_oop.ui;

import motorph_oop.service.EmployeeService;
import javax.swing.*;
import java.awt.*;

/// UI panel for adding a new employee.
// Handles form input and submission.
 
public class AddEmployeePanel extends JPanel {

    // Service layer instance
    private final EmployeeService employeeService = new EmployeeService();

    // Employee information fields
    private JTextField empNo = UIUtils.createTextField(false);
    private JTextField lastName = UIUtils.createTextField(true);
    private JTextField firstName = UIUtils.createTextField(true);
    private JTextField status = UIUtils.createTextField(true);
    private JTextField position = UIUtils.createTextField(true);
    private JTextField supervisor = UIUtils.createTextField(true);

    // Personal information fields
    private JTextField birthday = UIUtils.createTextField(true);
    private JTextField address = UIUtils.createTextField(true);
    private JTextField phone = UIUtils.createTextField(true);
    private JTextField sss = UIUtils.createTextField(true);
    private JTextField philhealth = UIUtils.createTextField(true);
    private JTextField tin = UIUtils.createTextField(true);
    private JTextField pagibig = UIUtils.createTextField(true);

    // Financial information fields
    private JTextField basicSalary = UIUtils.createTextField(true);
    private JTextField riceSubsidy = UIUtils.createTextField(true);
    private JTextField phoneAllowance = UIUtils.createTextField(true);
    private JTextField clothingAllowance = UIUtils.createTextField(true);
    private JTextField grossRate = UIUtils.createTextField(true);
    private JTextField hourlyRate = UIUtils.createTextField(true);

    // Constructor: builds the layout and initializes components.
    public AddEmployeePanel() {

        setLayout(new BorderLayout());

        // Buttons
        JButton btnAddEmployee =
                UIUtils.createButton(
                        "Add New Employee",
                        new Color(0, 180, 0),
                        Color.WHITE
                );

        JButton btnClear =
                UIUtils.createButton("Clear", null, null);

        JPanel buttons = new JPanel();
        buttons.add(btnAddEmployee);
        buttons.add(btnClear);

        // Form panels
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
                        riceSubsidy,
                        phoneAllowance,
                        clothingAllowance,
                        grossRate,
                        hourlyRate
                )
        );

        // Layout placement
        add(buttons, BorderLayout.WEST);
        add(formPanel, BorderLayout.CENTER);

        // Initialize employee number
        setNextEmployeeNumber();

        // Button actions
        btnAddEmployee.addActionListener(e -> addEmployee());
        btnClear.addActionListener(e -> clearFields());
    }

    // Sets the next available employee number.
    private void setNextEmployeeNumber() {
        empNo.setText(employeeService.generateNextEmployeeNumber());
    }

    // Clears all input fields except employee number.
    private void clearFields() {

        for (JTextField field : new JTextField[] {
                lastName,
                firstName,
                status,
                position,
                supervisor,
                birthday,
                address,
                phone,
                sss,
                philhealth,
                tin,
                pagibig,
                basicSalary,
                riceSubsidy,
                phoneAllowance,
                clothingAllowance,
                grossRate,
                hourlyRate
        }) {
            field.setText("");
        }
    }

    // Collects form data and submits new employee record.
    private void addEmployee() {

        try {

            employeeService.addEmployee(
                    empNo.getText().trim(),
                    lastName.getText().trim(),
                    firstName.getText().trim(),
                    status.getText().trim(),
                    position.getText().trim(),
                    supervisor.getText().trim(),
                    birthday.getText().trim(),
                    address.getText().trim(),
                    phone.getText().trim(),
                    sss.getText().trim(),
                    philhealth.getText().trim(),
                    tin.getText().trim(),
                    pagibig.getText().trim(),
                    basicSalary.getText().trim(),
                    riceSubsidy.getText().trim(),
                    phoneAllowance.getText().trim(),
                    clothingAllowance.getText().trim(),
                    grossRate.getText().trim(),
                    hourlyRate.getText().trim()
            );

            JOptionPane.showMessageDialog(
                    this,
                    "New employee added."
            );

            clearFields();
            setNextEmployeeNumber();

        } catch (IllegalArgumentException e) {

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage(),
                    "Error",
                    JOptionPane.WARNING_MESSAGE
            );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unexpected error occurred.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}