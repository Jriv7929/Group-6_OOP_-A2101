
package motorph_oop.ui;

import motorph_oop.service.EmployeeService;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.regex.Pattern;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;

public class AddEmployeePanel extends JPanel {

    private final EmployeeService employeeService = new EmployeeService();

    // Employee Information
    private JTextField empNo = UIUtils.createTextField(false);
    private JTextField lastName = UIUtils.createTextField(true);
    private JTextField firstName = UIUtils.createTextField(true);
    private JTextField status = UIUtils.createTextField(true);
    private JComboBox<String> position;
    private JTextField supervisor = UIUtils.createTextField(true);

    // Date settings for MM/DD/YYYY
    private DatePicker birthday;

    // Personal Information
    private JTextField address = UIUtils.createTextField(true);
    private JTextField phone = UIUtils.createTextField(true);
    private JTextField sss = UIUtils.createTextField(true);
    private JTextField philhealth = UIUtils.createTextField(true);
    private JTextField tin = UIUtils.createTextField(true);
    private JTextField pagibig = UIUtils.createTextField(true);

    // Financial Information
    private JTextField basicSalary = UIUtils.createTextField(true);
    private JTextField riceSubsidy = UIUtils.createTextField(true);
    private JTextField phoneAllowance = UIUtils.createTextField(true);
    private JTextField clothingAllowance = UIUtils.createTextField(true);
    private JTextField grossRate = UIUtils.createTextField(true);
    private JTextField hourlyRate = UIUtils.createTextField(false);

    public AddEmployeePanel() {

        setLayout(new BorderLayout());

        DatePickerSettings dateSettings = new DatePickerSettings();
        dateSettings.setFormatForDatesCommonEra("MM/dd/yyyy");

        birthday = new DatePicker(dateSettings);
        birthday.getComponentDateTextField().setToolTipText("MM/DD/YYYY");

        position = new JComboBox<>(new String[]{
                "Chief Executive Officer",
                "Chief Operating Officer",
                "Chief Finance Officer",
                "Chief Marketing Officer",
                "IT Operations and Systems",
                "HR Manager",
                "HR Team Leader",
                "HR Rank and File",
                "Accounting Head",
                "Payroll Manager",
                "Payroll Team Leader",
                "Payroll Rank and File",
                "Account Manager",
                "Account Team Leader",
                "Account Rank and File",
                "Sales & Marketing",
                "Supply Chain and Logistics",
                "Customer Service and Relations"
        });

        position.addActionListener(e -> updateSupervisor());

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

        add(buttons, BorderLayout.WEST);
        add(formPanel, BorderLayout.CENTER);

        setNextEmployeeNumber();

        updateSupervisor();
        status.setText("Probationary");

        restrictName(firstName);
        restrictName(lastName);

        allowNumbersDash(phone);
        allowNumbersDash(sss);
        allowNumbersDash(tin);
        allowNumbersDash(pagibig);

        allowDecimal(basicSalary);
        allowDecimal(riceSubsidy);
        allowDecimal(phoneAllowance);
        allowDecimal(clothingAllowance);
        allowDecimal(grossRate);

        hourlyRate.setEditable(false);
        grossRate.setEditable(false);

        basicSalary.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { calculateHourly(); }
            public void removeUpdate(DocumentEvent e) { calculateHourly(); }
            public void changedUpdate(DocumentEvent e) { calculateHourly(); }
        });

        DocumentListener grossListener = new DocumentListener() {

            public void insertUpdate(DocumentEvent e) { calculateGrossRate(); }
            public void removeUpdate(DocumentEvent e) { calculateGrossRate(); }
            public void changedUpdate(DocumentEvent e) { calculateGrossRate(); }

        };

        basicSalary.getDocument().addDocumentListener(grossListener);
        riceSubsidy.getDocument().addDocumentListener(grossListener);
        phoneAllowance.getDocument().addDocumentListener(grossListener);
        clothingAllowance.getDocument().addDocumentListener(grossListener);

        btnAddEmployee.addActionListener(e -> addEmployee());
        btnClear.addActionListener(e -> clearFields());
    }

    private void updateSupervisor() {

        String pos = position.getSelectedItem().toString();

        switch (pos) {

            case "Chief Executive Officer":
                supervisor.setText("N/A");
                break;

            case "Chief Operating Officer":
            case "Chief Finance Officer":
            case "Chief Marketing Officer":
                supervisor.setText("Garcia, Manuel III");
                break;

            case "IT Operations and Systems":
            case "HR Manager":
            case "Account Manager":
                supervisor.setText("Lim, Antonio");
                break;

            case "HR Team Leader":
                supervisor.setText("Villanueva, Andrea Mae");
                break;

            case "HR Rank and File":
                supervisor.setText("San, Jose Brad");
                break;

            case "Accounting Head":
                supervisor.setText("Aquino, Bianca Sofia");
                break;

            case "Payroll Manager":
                supervisor.setText("Alvaro, Roderick");
                break;

            case "Payroll Team Leader":
            case "Payroll Rank and File":
                supervisor.setText("Salcedo, Anthony");
                break;

            case "Account Team Leader":
                supervisor.setText("Romualdez, Fredrick");
                break;

            case "Account Rank and File":
                supervisor.setText("Mata, Christian");
                break;

            case "Sales & Marketing":
            case "Supply Chain and Logistics":
            case "Customer Service and Relations":
                supervisor.setText("Reyes, Isabella");
                break;

            default:
                supervisor.setText("");
        }
    }

    private void calculateGrossRate() {

        try {

            double basic = basicSalary.getText().isEmpty() ? 0 : Double.parseDouble(basicSalary.getText());
            double rice = riceSubsidy.getText().isEmpty() ? 0 : Double.parseDouble(riceSubsidy.getText());
            double phone = phoneAllowance.getText().isEmpty() ? 0 : Double.parseDouble(phoneAllowance.getText());
            double clothing = clothingAllowance.getText().isEmpty() ? 0 : Double.parseDouble(clothingAllowance.getText());

            double gross = basic + rice + phone + clothing;

            grossRate.setText(String.format("%.2f", gross));

        } catch (Exception e) {

            grossRate.setText("");
        }
    }

    private void setNextEmployeeNumber() {
        empNo.setText(employeeService.generateNextEmployeeNumber());
    }

    private void clearFields() {

        lastName.setText("");
        firstName.setText("");
        address.setText("");
        phone.setText("");
        sss.setText("");
        philhealth.setText("");
        tin.setText("");
        pagibig.setText("");

        basicSalary.setText("");
        riceSubsidy.setText("");
        phoneAllowance.setText("");
        clothingAllowance.setText("");
        grossRate.setText("");
        hourlyRate.setText("");

        birthday.clear();

        updateSupervisor();
        status.setText("Probationary");

        position.setSelectedIndex(0);
    }

    private void addEmployee() {

        try {

            validateNames();
            validateSalary();

            String birthDate = birthday.getText();

            employeeService.addEmployee(
                    empNo.getText().trim(),
                    lastName.getText().trim(),
                    firstName.getText().trim(),
                    status.getText().trim(),
                    position.getSelectedItem().toString(),
                    supervisor.getText().trim(),
                    birthDate,
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

            JOptionPane.showMessageDialog(this, "New employee added.");

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

    private void validateNames() {

        if (!Pattern.matches("^[A-Za-z-]+$", firstName.getText()))
            throw new IllegalArgumentException("First name cannot contain numbers or special characters.");

        if (!Pattern.matches("^[A-Za-z-]+$", lastName.getText()))
            throw new IllegalArgumentException("Last name cannot contain numbers or special characters.");
    }

    private void validateSalary() {

        double basic = parseNumber(basicSalary.getText(), "Basic Salary");

        if (basic <= 0)
            throw new IllegalArgumentException("Basic Salary must be greater than zero.");

        parseNumber(riceSubsidy.getText(), "Rice Subsidy");
        parseNumber(phoneAllowance.getText(), "Phone Allowance");
        parseNumber(clothingAllowance.getText(), "Clothing Allowance");

        double gross = parseNumber(grossRate.getText(), "Gross Rate");

        if (gross <= 0)
            throw new IllegalArgumentException("Gross Rate must be greater than zero.");
    }

    private double parseNumber(String value, String field) {

        try {

            double v = Double.parseDouble(value);

            if (v < 0)
                throw new IllegalArgumentException(field + " cannot be negative.");

            return v;

        } catch (NumberFormatException e) {

            throw new IllegalArgumentException(field + " must be numeric.");
        }
    }

    private void calculateHourly() {

        try {

            double salary = Double.parseDouble(basicSalary.getText());
            double hourly = salary / 22 / 8;

            hourlyRate.setText(String.format("%.2f", hourly));

        } catch (Exception e) {

            hourlyRate.setText("");
        }
    }

    private void allowNumbersDash(JTextField field) {

        field.addKeyListener(new KeyAdapter() {

            public void keyTyped(KeyEvent e) {

                char c = e.getKeyChar();

                if (!(Character.isDigit(c) || c == '-'))
                    e.consume();
            }
        });
    }

    private void allowDecimal(JTextField field) {

        field.addKeyListener(new KeyAdapter() {

            public void keyTyped(KeyEvent e) {

                char c = e.getKeyChar();

                if (!(Character.isDigit(c) || c == '.'))
                    e.consume();
            }
        });
    }

    private void restrictName(JTextField field) {

        field.addKeyListener(new KeyAdapter() {

            public void keyTyped(KeyEvent e) {

                char c = e.getKeyChar();

                if (!(Character.isLetter(c) || c == '-'))
                    e.consume();
            }
        });
    }
}