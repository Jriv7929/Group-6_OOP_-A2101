
package motorph_payroll.model;

import motorph_payroll.department.Department;

public class RegularEmployee extends Employee {

    public RegularEmployee(String employeeId, String name,
                           Department department, double baseSalary) {
        super(employeeId, name, department, baseSalary);
    }

    @Override
    public double calculatePayroll() {
        return baseSalary;
    }
}
