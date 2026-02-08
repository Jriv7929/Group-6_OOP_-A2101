
package motorph_payroll.model;

import motorph_payroll.department.Department;
import motorph_payroll.utils.PayrollCalculator;

public abstract class Employee extends PayrollCalculator {

    protected String employeeId;
    protected String name;
    protected Department department;
    protected double baseSalary;

    public Employee(String employeeId, String name, Department department, double baseSalary) {
        this.employeeId = employeeId;
        this.name = name;
        this.department = department;
        this.baseSalary = baseSalary;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String getName() {
        return name;
    }

    public Department getDepartment() {
        return department;
    }

    public double getBaseSalary() {
        return baseSalary;
    }

    // Force subclasses to define their payroll rules
    public abstract double calculatePayroll();
}