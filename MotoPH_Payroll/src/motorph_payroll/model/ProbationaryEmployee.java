
package motorph_payroll.model;

import motorph_payroll.department.Department;

public class ProbationaryEmployee extends Employee {

    public ProbationaryEmployee(String employeeId, String name,
                                Department department, double baseSalary) {
        super(employeeId, name, department, baseSalary);
    }

    @Override
    public double calculatePayroll() {
        return baseSalary;
    }
}