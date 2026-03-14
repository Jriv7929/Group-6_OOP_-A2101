package motorph_oop.ui;

import motorph_oop.model.Employee;
import motorph_oop.service.EmployeeService;
import motorph_oop.util.Constants;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

// displays employee database table and basic employee information
public class EmployeeDashboardPanel extends JPanel {

    // service layer
    private final EmployeeService employeeService = new EmployeeService();

    // table
    private JTable table;
    private DefaultTableModel model;
    private List<Employee> employeeList;

    // form
    private JPanel formPanel = new JPanel(new GridLayout(3,1,5,2));

    // employee fields
    private JTextField empNo = UIUtils.createTextField(false);
    private JTextField lastName = UIUtils.createTextField(true);
    private JTextField firstName = UIUtils.createTextField(true);
    private JTextField status = UIUtils.createTextField(true);
    private JTextField position = UIUtils.createTextField(true);
    private JTextField supervisor = UIUtils.createTextField(true);

    public EmployeeDashboardPanel() {

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(1200,getHeight()));

        add(createHeader(),BorderLayout.NORTH);
        add(createTable(),BorderLayout.CENTER);
        add(createForm(),BorderLayout.SOUTH);

        loadEmployees();
    }

    // header section
    private JLabel createHeader(){
        return UIUtils.createHeaderLabel("Employee Database");
    }

    // table section
    private JScrollPane createTable(){

        model = new DefaultTableModel(Constants.EMPLOYEE_COLUMNS,0);
        table = new JTable(model);

        table.getSelectionModel().addListSelectionListener(e -> handleRowSelection());

        JScrollPane scroll = new JScrollPane(table);
        scroll.setPreferredSize(new Dimension(1000,300));

        return scroll;
    }

    // form section
    private JPanel createForm(){

        formPanel.add(
            UIUtils.createEmployeeInfoPanel(
                empNo,lastName,firstName,
                status,position,supervisor
            )
        );

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(formPanel,BorderLayout.CENTER);

        return bottomPanel;
    }

    // load employees into table
    private void loadEmployees(){

        model.setRowCount(0);
        employeeList = employeeService.getEmployees();

        for(Employee emp : employeeList){
            model.addRow(createRow(emp));
        }
    }

    // convert employee object to table row
    private Object[] createRow(Employee emp){

        return new Object[]{
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
        };
    }

    // handle table row selection
    private void handleRowSelection(){

        int row = table.getSelectedRow();

        if(row >= 0 && employeeList != null){
            populateForm(employeeList.get(row));
        }
    }

    // populate form fields
    private void populateForm(Employee emp){

        empNo.setText(emp.getEmpNo());
        lastName.setText(emp.getLastName());
        firstName.setText(emp.getFirstName());
        status.setText(emp.getStatus());
        position.setText(emp.getPosition());
        supervisor.setText(emp.getSupervisor());
    }

    // reload employees
    public void reloadEmployees(){
        loadEmployees();
        clearFields();
    }

    // return selected employee number
    public String getSelectedEmployeeNo(){

        int row = table.getSelectedRow();

        if(row >= 0 && employeeList != null){
            return employeeList.get(row).getEmpNo();
        }

        return null;
    }

    // clear form fields
    public void clearFields(){

        JTextField[] fields = {
            empNo,lastName,firstName,status,position,supervisor
        };

        for(JTextField f : fields){
            f.setText("");
        }
    }
}