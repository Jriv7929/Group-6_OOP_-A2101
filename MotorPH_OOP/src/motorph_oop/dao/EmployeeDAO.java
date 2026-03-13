
package motorph_oop.dao;

import motorph_oop.model.Employee;
import motorph_oop.model.RegularEmployee;
import motorph_oop.model.ProbationaryEmployee;
import motorph_oop.util.Constants;
import motorph_oop.util.CSVHandler;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {

    // Get Last Employee Number

    public int getLastEmployeeNumber() {

        int maxEmpNo = 10000;

        try (BufferedReader br =
                     new BufferedReader(new FileReader(Constants.EMPLOYEE_DATA_CSV))) {

            br.readLine(); // skip header
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
            throw new RuntimeException("Failed to read employee CSV.", e);
        }

        return maxEmpNo;
    }

    // Save Employee

    public void saveEmployee(Employee employee) {

        CSVHandler.appendToCSV(
                Constants.EMPLOYEE_DATA_CSV,
                employee.toCSVArray()
        );
    }

    // Get All Employees

    public List<Employee> getAllEmployees() {

    List<Employee> employees = new ArrayList<>();

    try (BufferedReader br =
                 new BufferedReader(new FileReader(Constants.EMPLOYEE_DATA_CSV))) {

        br.readLine(); 
        String line;

        while ((line = br.readLine()) != null) {

            String[] parts = line.split(",");

            if (parts.length >= 19) {  

                Employee employee;

                if ("Regular".equalsIgnoreCase(parts[10])) {
                    employee = new RegularEmployee();
                } else {
                    employee = new ProbationaryEmployee();
                }

                employee.setEmpNo(parts[0]);
                employee.setLastName(parts[1]);
                employee.setFirstName(parts[2]);
                employee.setBirthday(parts[3]);
                employee.setAddress(parts[4]);
                employee.setPhone(parts[5]);
                employee.setSss(parts[6]);
                employee.setPhilhealth(parts[7]);
                employee.setTin(parts[8]);
                employee.setPagibig(parts[9]);
                employee.setStatus(parts[10]);
                employee.setPosition(parts[11]);
                employee.setSupervisor(parts[12]);
                employee.setBasicSalary(Double.parseDouble(parts[13]));
                employee.setRiceSubsidy(Double.parseDouble(parts[14]));
                employee.setPhoneAllowance(Double.parseDouble(parts[15]));
                employee.setClothingAllowance(Double.parseDouble(parts[16]));
                employee.setGrossRate(Double.parseDouble(parts[17]));
                employee.setHourlyRate(Double.parseDouble(parts[18]));

                employees.add(employee);
            }
        }

    } catch (IOException e) {
        throw new RuntimeException("Failed to load employees.", e);
    }

    return employees;
}
    
    public Employee findEmployeeById(String empNo) {

    try (BufferedReader reader =
                 new BufferedReader(new FileReader(Constants.EMPLOYEE_DATA_CSV))) {

        reader.readLine();
        String line;

        while ((line = reader.readLine()) != null) {

            String[] parts = line.split(",", -1);

            if (parts.length >= 19 && parts[0].trim().equals(empNo)) {

                Employee employee;

                if ("Regular".equalsIgnoreCase(parts[10])) {
                    employee = new RegularEmployee();
                } else {
                    employee = new ProbationaryEmployee();
                }

                employee.setEmpNo(parts[0]);
                employee.setLastName(parts[1]);
                employee.setFirstName(parts[2]);
                employee.setBirthday(parts[3]);
                employee.setAddress(parts[4]);
                employee.setPhone(parts[5]);
                employee.setSss(parts[6]);
                employee.setPhilhealth(parts[7]);
                employee.setTin(parts[8]);
                employee.setPagibig(parts[9]);
                employee.setStatus(parts[10]);
                employee.setPosition(parts[11]);
                employee.setSupervisor(parts[12]);
                employee.setBasicSalary(Double.parseDouble(parts[13]));
                employee.setRiceSubsidy(Double.parseDouble(parts[14]));
                employee.setPhoneAllowance(Double.parseDouble(parts[15]));
                employee.setClothingAllowance(Double.parseDouble(parts[16]));
                employee.setGrossRate(Double.parseDouble(parts[17]));
                employee.setHourlyRate(Double.parseDouble(parts[18]));

                return employee;
            }
        }

    } catch (IOException e) {
        throw new RuntimeException("Error reading employee file", e);
    }

    return null;
}
    public void updateEmployee(Employee employee) {
    CSVHandler.updateCSV(
            Constants.EMPLOYEE_DATA_CSV,
            employee.getEmpNo(),
            employee.toCSVArray());
}

    public void deleteEmployee(String empNo) {
    CSVHandler.deleteFromCSV(
            Constants.EMPLOYEE_DATA_CSV,
            empNo);
    }
    
    
}