
package motorph_oop.service;

import motorph_oop.dao.EmployeeDAO;
import motorph_oop.model.Employee;
import motorph_oop.model.RegularEmployee;
import motorph_oop.model.ProbationaryEmployee;

import java.util.List;

// Service layer for handling employee-related operations.
// Acts as a bridge between the UI layer and EmployeeDAO.
 
public class EmployeeService {

    // DAO instance for employee database operations
    private final EmployeeDAO employeeDAO = new EmployeeDAO();

    // Retrieves all employees.  
    public List<Employee> getEmployees() {
        return employeeDAO.getAllEmployees();
    }

    // Generates the next employee number based on the last saved record.   
    public String generateNextEmployeeNumber() {
        int last = employeeDAO.getLastEmployeeNumber();
        return String.valueOf(last + 1);
    }

    // Adds a new employee record.  
    public void addEmployee(
            String empNo,
            String lastName,
            String firstName,
            String status,
            String position,
            String supervisor,
            String birthday,
            String address,
            String phone,
            String sss,
            String philhealth,
            String tin,
            String pagibig,
            String basicSalary,
            String riceSubsidy,
            String phoneAllowance,
            String clothingAllowance,
            String grossRate,
            String hourlyRate
    ) {

        // Determine employee type based on status
        Employee employee = determineEmployeeType(status);

        try {

            // Set personal information
            employee.setEmpNo(empNo);
            employee.setLastName(lastName);
            employee.setFirstName(firstName);
            employee.setBirthday(birthday);
            employee.setAddress(address);
            employee.setPhone(phone);
            employee.setSss(sss);
            employee.setPhilhealth(philhealth);
            employee.setTin(tin);
            employee.setPagibig(pagibig);
            employee.setStatus(status);
            employee.setPosition(position);
            employee.setSupervisor(supervisor);

            // Parse and set salary-related fields
            employee.setBasicSalary(Double.parseDouble(basicSalary));
            employee.setRiceSubsidy(Double.parseDouble(riceSubsidy));
            employee.setPhoneAllowance(Double.parseDouble(phoneAllowance));
            employee.setClothingAllowance(Double.parseDouble(clothingAllowance));
            employee.setGrossRate(Double.parseDouble(grossRate));
            employee.setHourlyRate(Double.parseDouble(hourlyRate));

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid numeric input.");
        }

        // Validate required fields
        validateEmployee(employee);

        // Save employee record
        employeeDAO.saveEmployee(employee);
    }

    // Determines the employee type based on employment status.    
    private Employee determineEmployeeType(String status) {

        if (status.equalsIgnoreCase("Regular")) {
            return new RegularEmployee();
        } else {
            return new ProbationaryEmployee();
        }
    }

    // Validates required employee fields.  
    private void validateEmployee(Employee employee) {

        if (employee.getLastName().isEmpty() ||
            employee.getFirstName().isEmpty() ||
            employee.getStatus().isEmpty() ||
            employee.getPosition().isEmpty()) {

            throw new IllegalArgumentException("Please fill in all required fields.");
        }
    }
}