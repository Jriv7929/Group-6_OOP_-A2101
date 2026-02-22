/*
package motorph_oop.ui;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import motorph_oop.dao.Constants;
import motorph_oop.dao.Constants;
import motorph_oop.dao.CSVHandler;
import javax.swing.JOptionPane;
import motorph_oop.dao.CSVHandler;
import motorph_oop.model.EmployeeData;

public class AddEmployeePanel extends JPanel {
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
    private JTextField riceSubsidy = UIUtils.createTextField(true);
    private JTextField phoneAllowance = UIUtils.createTextField(true);
    private JTextField clothingAllowance = UIUtils.createTextField(true);
    private JTextField grossRate = UIUtils.createTextField(true);
    private JTextField hourlyRate = UIUtils.createTextField(true);

    private JPanel bottomPanel = new JPanel(new BorderLayout());
    private JPanel buttons = new JPanel();

    public AddEmployeePanel() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(1200, getHeight()));

        JLabel addHeading = UIUtils.createHeaderLabel("Add Employee");
        add(addHeading, BorderLayout.NORTH);

        JButton btnAddEmployee = UIUtils.createButton("Add New Employee", new Color(0, 180, 0), Color.WHITE);
        JButton btnClear = UIUtils.createButton("Clear", null, null);

        buttons.add(btnAddEmployee);
        buttons.add(btnClear);

        JPanel formPanel = new JPanel(new GridLayout(3, 1, 5, 2));
        formPanel.add(UIUtils.createEmployeeInfoPanel(empNo, lastName, firstName, status, position, supervisor));
        formPanel.add(UIUtils.createPersonalInfoPanel(birthday, address, phone, sss, philhealth, tin, pagibig));
        formPanel.add(UIUtils.createFinancialInfoPanel(basicSalary, riceSubsidy, phoneAllowance, clothingAllowance, grossRate, hourlyRate));

        bottomPanel.add(formPanel, BorderLayout.CENTER);
        add(buttons, BorderLayout.WEST);
        add(bottomPanel, BorderLayout.SOUTH);

        setNextEmployeeNumber();

        btnAddEmployee.addActionListener(e -> addEmployeeToCSV());
        btnClear.addActionListener(e -> clearFields());
    }

    private void clearFields() {
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
        riceSubsidy.setText("");
        phoneAllowance.setText("");
        clothingAllowance.setText("");
        grossRate.setText("");
        hourlyRate.setText("");
    }

    private void setNextEmployeeNumber() {
        empNo.setText(String.valueOf(getLastEmployeeNumber() + 1));
    }

    private int getLastEmployeeNumber() {
        int maxEmpNo = 10000;
        try (BufferedReader br = new BufferedReader(new FileReader(Constants.EMPLOYEE_DATA_CSV))) {
            br.readLine(); // Skip header
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 0) {
                    try {
                        int currentNo = Integer.parseInt(parts[0].replaceAll("[^\\d]", ""));
                        if (currentNo > maxEmpNo) {
                            maxEmpNo = currentNo;
                        }
                    } catch (NumberFormatException ignored) {}
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Failed to read employee numbers from CSV.");
        }
        return maxEmpNo;
    }

    private void addEmployeeToCSV() {
        EmployeeData data = new EmployeeData();
        data.empNo = empNo.getText().trim();
        data.lastName = lastName.getText().trim();
        data.firstName = firstName.getText().trim();
        data.birthday = birthday.getText().trim();
        data.address = address.getText().trim();
        data.phone = phone.getText().trim();
        data.sss = sss.getText().trim();
        data.philhealth = philhealth.getText().trim();
        data.tin = tin.getText().trim();
        data.pagibig = pagibig.getText().trim();
        data.status = status.getText().trim();
        data.position = position.getText().trim();
        data.supervisor = supervisor.getText().trim();
        data.basicSalary = basicSalary.getText().isEmpty() ? 0 : Double.parseDouble(basicSalary.getText().trim());
        data.riceSubsidy = riceSubsidy.getText().isEmpty() ? 0 : Double.parseDouble(riceSubsidy.getText().trim());
        data.phoneAllowance = phoneAllowance.getText().isEmpty() ? 0 : Double.parseDouble(phoneAllowance.getText().trim());
        data.clothingAllowance = clothingAllowance.getText().isEmpty() ? 0 : Double.parseDouble(clothingAllowance.getText().trim());
        data.grossRate = grossRate.getText().isEmpty() ? 0 : Double.parseDouble(grossRate.getText().trim());
        data.hourlyRate = hourlyRate.getText().isEmpty() ? 0 : Double.parseDouble(hourlyRate.getText().trim());

        if (data.lastName.isEmpty() || data.firstName.isEmpty() || data.status.isEmpty() || data.position.isEmpty() ||
            data.supervisor.isEmpty() || data.birthday.isEmpty() || data.address.isEmpty() || data.phone.isEmpty() ||
            data.sss.isEmpty() || data.philhealth.isEmpty() || data.tin.isEmpty() || data.pagibig.isEmpty() ||
            basicSalary.getText().isEmpty() || riceSubsidy.getText().isEmpty() || phoneAllowance.getText().isEmpty() ||
            clothingAllowance.getText().isEmpty() || grossRate.getText().isEmpty() || hourlyRate.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Missing Info", JOptionPane.WARNING_MESSAGE);
            return;
        }

        CSVHandler.appendToCSV(Constants.EMPLOYEE_DATA_CSV, data.toCSVArray());
        JOptionPane.showMessageDialog(this, "New employee added.");
        clearFields();
        setNextEmployeeNumber();
    }
} */

package motorph_oop.ui;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import motorph_oop.dao.Constants;
import motorph_oop.dao.CSVHandler;
import motorph_oop.model.EmployeeData;
import motorph_oop.model.RegularEmployee;
import motorph_oop.model.ProbationaryEmployee;

public class AddEmployeePanel extends JPanel {

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
    private JTextField riceSubsidy = UIUtils.createTextField(true);
    private JTextField phoneAllowance = UIUtils.createTextField(true);
    private JTextField clothingAllowance = UIUtils.createTextField(true);
    private JTextField grossRate = UIUtils.createTextField(true);
    private JTextField hourlyRate = UIUtils.createTextField(true);

    public AddEmployeePanel() {

        setLayout(new BorderLayout());

        JButton btnAddEmployee =
                UIUtils.createButton("Add New Employee", new Color(0, 180, 0), Color.WHITE);

        JButton btnClear =
                UIUtils.createButton("Clear", null, null);

        JPanel buttons = new JPanel();
        buttons.add(btnAddEmployee);
        buttons.add(btnClear);

        JPanel formPanel = new JPanel(new GridLayout(3, 1, 5, 2));
        formPanel.add(UIUtils.createEmployeeInfoPanel(empNo, lastName, firstName, status, position, supervisor));
        formPanel.add(UIUtils.createPersonalInfoPanel(birthday, address, phone, sss, philhealth, tin, pagibig));
        formPanel.add(UIUtils.createFinancialInfoPanel(basicSalary, riceSubsidy, phoneAllowance,
                clothingAllowance, grossRate, hourlyRate));

        add(buttons, BorderLayout.WEST);
        add(formPanel, BorderLayout.CENTER);

        setNextEmployeeNumber();

        btnAddEmployee.addActionListener(e -> addEmployeeToCSV());
        btnClear.addActionListener(e -> clearFields());
    }

    private void clearFields() {
        for (JTextField field : new JTextField[]{
                lastName, firstName, status, position, supervisor,
                birthday, address, phone, sss, philhealth,
                tin, pagibig, basicSalary, riceSubsidy,
                phoneAllowance, clothingAllowance, grossRate, hourlyRate}) {

            field.setText("");
        }
    }

    private void setNextEmployeeNumber() {
        empNo.setText(String.valueOf(getLastEmployeeNumber() + 1));
    }

    private int getLastEmployeeNumber() {
        int maxEmpNo = 10000;

        try (BufferedReader br =
                     new BufferedReader(new FileReader(Constants.EMPLOYEE_DATA_CSV))) {

            br.readLine();
            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                if (parts.length > 0) {
                    try {
                        int currentNo =
                                Integer.parseInt(parts[0].replaceAll("[^\\d]", ""));
                        maxEmpNo = Math.max(maxEmpNo, currentNo);
                    } catch (NumberFormatException ignored) {}
                }
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "Failed to read employee numbers from CSV.");
        }

        return maxEmpNo;
    }

    private void addEmployeeToCSV() {

        
        EmployeeData data;

        if (status.getText().trim().equalsIgnoreCase("Regular")) {
            data = new RegularEmployee();
        } else {
            data = new ProbationaryEmployee();
        }

        try {
            // Use setters (ENCAPSULATION)
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

            data.setBasicSalary(Double.parseDouble(basicSalary.getText().trim()));
            data.setRiceSubsidy(Double.parseDouble(riceSubsidy.getText().trim()));
            data.setPhoneAllowance(Double.parseDouble(phoneAllowance.getText().trim()));
            data.setClothingAllowance(Double.parseDouble(clothingAllowance.getText().trim()));
            data.setGrossRate(Double.parseDouble(grossRate.getText().trim()));
            data.setHourlyRate(Double.parseDouble(hourlyRate.getText().trim()));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Invalid numeric input.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validation using getters
        if (data.getLastName().isEmpty() ||
            data.getFirstName().isEmpty() ||
            data.getStatus().isEmpty() ||
            data.getPosition().isEmpty()) {

            JOptionPane.showMessageDialog(this,
                    "Please fill in all required fields.",
                    "Missing Info",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        CSVHandler.appendToCSV(Constants.EMPLOYEE_DATA_CSV,
                data.toCSVArray());

        JOptionPane.showMessageDialog(this,
                "New employee added.");

        clearFields();
        setNextEmployeeNumber();
    }
}