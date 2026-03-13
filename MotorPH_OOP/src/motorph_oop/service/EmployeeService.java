
package motorph_oop.service;

import motorph_oop.dao.EmployeeDAO;
import motorph_oop.model.Employee;
import motorph_oop.model.RegularEmployee;
import motorph_oop.model.ProbationaryEmployee;

import java.util.List;

public class EmployeeService {

    private final EmployeeDAO employeeDAO = new EmployeeDAO();

    public List<Employee> getEmployees() {
        return employeeDAO.getAllEmployees();
    }

    public String generateNextEmployeeNumber() {
        int last = employeeDAO.getLastEmployeeNumber();
        return String.valueOf(last + 1);
    }

    public double calculateGrossRate(
            double basic,
            double rice,
            double phone,
            double clothing) {

        return basic + rice + phone + clothing;
    }

    public double calculateHourlyRate(double basicSalary) {
        return basicSalary / 22 / 8;
    }

    public String determineSupervisor(String position) {

        switch (position) {

            case "Chief Executive Officer":
                return "N/A";

            case "Chief Operating Officer":
            case "Chief Finance Officer":
            case "Chief Marketing Officer":
                return "Garcia, Manuel III";

            case "IT Operations and Systems":
            case "HR Manager":
            case "Account Manager":
                return "Lim, Antonio";

            case "HR Team Leader":
                return "Villanueva, Andrea Mae";

            case "HR Rank and File":
                return "San, Jose Brad";

            case "Accounting Head":
                return "Aquino, Bianca Sofia";

            case "Payroll Manager":
                return "Alvaro, Roderick";

            case "Payroll Team Leader":
            case "Payroll Rank and File":
                return "Salcedo, Anthony";

            case "Account Team Leader":
                return "Romualdez, Fredrick";

            case "Account Rank and File":
                return "Mata, Christian";

            case "Sales & Marketing":
            case "Supply Chain and Logistics":
            case "Customer Service and Relations":
                return "Reyes, Isabella";

            default:
                return "";
        }
    }

    public void validateNames(String firstName, String lastName) {

        if (!firstName.matches("^[A-Za-z-]+$"))
            throw new IllegalArgumentException(
                    "First name cannot contain numbers or special characters.");

        if (!lastName.matches("^[A-Za-z-]+$"))
            throw new IllegalArgumentException(
                    "Last name cannot contain numbers or special characters.");
    }

    public double parseNumber(String value, String field) {

        try {

            double v = Double.parseDouble(value);

            if (v < 0)
                throw new IllegalArgumentException(field + " cannot be negative.");

            return v;

        } catch (NumberFormatException e) {

            throw new IllegalArgumentException(field + " must be numeric.");
        }
    }

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

        validateNames(firstName, lastName);

        Employee employee = determineEmployeeType(status);

        try {

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

            employee.setBasicSalary(Double.parseDouble(basicSalary));
            employee.setRiceSubsidy(Double.parseDouble(riceSubsidy));
            employee.setPhoneAllowance(Double.parseDouble(phoneAllowance));
            employee.setClothingAllowance(Double.parseDouble(clothingAllowance));
            employee.setGrossRate(Double.parseDouble(grossRate));
            employee.setHourlyRate(Double.parseDouble(hourlyRate));

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid numeric input.");
        }

        validateEmployee(employee);

        employeeDAO.saveEmployee(employee);
    }

    private Employee determineEmployeeType(String status) {

        if (status.equalsIgnoreCase("Regular")) {
            return new RegularEmployee();
        } else {
            return new ProbationaryEmployee();
        }
    }

    private void validateEmployee(Employee employee) {

        if (employee.getLastName().isEmpty() ||
            employee.getFirstName().isEmpty() ||
            employee.getStatus().isEmpty() ||
            employee.getPosition().isEmpty()) {

            throw new IllegalArgumentException("Please fill in all required fields.");
        }
    }
}