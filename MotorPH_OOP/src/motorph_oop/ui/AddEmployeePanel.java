
package motorph_oop.ui;

import motorph_oop.service.EmployeeService;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;

public class AddEmployeePanel extends JPanel {

    private final EmployeeService employeeService = new EmployeeService();

    private JTextField empNo = UIUtils.createTextField(false);
    private JTextField lastName = UIUtils.createTextField(true);
    private JTextField firstName = UIUtils.createTextField(true);
    private JTextField status = UIUtils.createTextField(true);
    private JComboBox<String> position;
    private JTextField supervisor = UIUtils.createTextField(true);

    private DatePicker birthday;

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
    private JTextField hourlyRate = UIUtils.createTextField(false);

    public AddEmployeePanel() {

        setLayout(new BorderLayout());

        DatePickerSettings settings = new DatePickerSettings();
        settings.setFormatForDatesCommonEra("MM/dd/yyyy");
        birthday = new DatePicker(settings);

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

        JButton addButton = UIUtils.createButton("Add New Employee",
                new Color(0,180,0), Color.WHITE);

        JButton clearButton = UIUtils.createButton("Clear", null, null);

        JPanel buttons = new JPanel();
        buttons.add(addButton);
        buttons.add(clearButton);

        JPanel formPanel = new JPanel(new GridLayout(3,1,5,2));

        formPanel.add(UIUtils.createEmployeeInfoPanel(
                empNo,lastName,firstName,status,position,supervisor));

        formPanel.add(UIUtils.createPersonalInfoPanel(
                birthday,address,phone,sss,philhealth,tin,pagibig));

        formPanel.add(UIUtils.createFinancialInfoPanel(
                basicSalary,riceSubsidy,phoneAllowance,
                clothingAllowance,grossRate,hourlyRate));

        add(buttons,BorderLayout.WEST);
        add(formPanel,BorderLayout.CENTER);

        setNextEmployeeNumber();

        updateSupervisor();
        status.setText("Probationary");

        basicSalary.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e){calculateHourly();}
            public void removeUpdate(DocumentEvent e){calculateHourly();}
            public void changedUpdate(DocumentEvent e){calculateHourly();}
        });

        DocumentListener grossListener = new DocumentListener() {
            public void insertUpdate(DocumentEvent e){calculateGross();}
            public void removeUpdate(DocumentEvent e){calculateGross();}
            public void changedUpdate(DocumentEvent e){calculateGross();}
        };

        basicSalary.getDocument().addDocumentListener(grossListener);
        riceSubsidy.getDocument().addDocumentListener(grossListener);
        phoneAllowance.getDocument().addDocumentListener(grossListener);
        clothingAllowance.getDocument().addDocumentListener(grossListener);

        addButton.addActionListener(e -> addEmployee());
        clearButton.addActionListener(e -> clearFields());
    }

    private void updateSupervisor() {

        String pos = position.getSelectedItem().toString();

        supervisor.setText(
                employeeService.determineSupervisor(pos));
    }

    private void calculateGross() {

        try {

            double basic = basicSalary.getText().isEmpty()?0:Double.parseDouble(basicSalary.getText());
            double rice = riceSubsidy.getText().isEmpty()?0:Double.parseDouble(riceSubsidy.getText());
            double phone = phoneAllowance.getText().isEmpty()?0:Double.parseDouble(phoneAllowance.getText());
            double clothing = clothingAllowance.getText().isEmpty()?0:Double.parseDouble(clothingAllowance.getText());

            double gross = employeeService.calculateGrossRate(basic,rice,phone,clothing);

            grossRate.setText(String.format("%.2f",gross));

        } catch(Exception e){
            grossRate.setText("");
        }
    }

    private void calculateHourly(){

        try{

            double salary = Double.parseDouble(basicSalary.getText());

            double hourly = employeeService.calculateHourlyRate(salary);

            hourlyRate.setText(String.format("%.2f",hourly));

        }catch(Exception e){
            hourlyRate.setText("");
        }
    }

    private void setNextEmployeeNumber(){
        empNo.setText(employeeService.generateNextEmployeeNumber());
    }

    private void clearFields(){

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

    private void addEmployee(){

        try{

            employeeService.addEmployee(
                    empNo.getText().trim(),
                    lastName.getText().trim(),
                    firstName.getText().trim(),
                    status.getText().trim(),
                    position.getSelectedItem().toString(),
                    supervisor.getText().trim(),
                    birthday.getText(),
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

            JOptionPane.showMessageDialog(this,"New employee added.");

            clearFields();
            setNextEmployeeNumber();

        }catch(IllegalArgumentException e){

            JOptionPane.showMessageDialog(
                    this,e.getMessage(),"Error",
                    JOptionPane.WARNING_MESSAGE);

        }catch(Exception e){

            JOptionPane.showMessageDialog(
                    this,"Unexpected error occurred.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}