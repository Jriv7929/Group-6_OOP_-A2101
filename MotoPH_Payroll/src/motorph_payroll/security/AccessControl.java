
package motorph_payroll.security;

import motorph_payroll.model.Employee;

public class AccessControl {

    private static final int PAYROLL_MODIFY_LEVEL = 4;

    public static boolean canModifyPayroll(Employee employee) {
        return employee.getDepartment().getAccessLevel() >= PAYROLL_MODIFY_LEVEL;
    }
}