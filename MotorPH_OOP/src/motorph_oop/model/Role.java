
package motorph_oop.model;

public interface Role {

    boolean canManageEmployees();
    boolean canApproveLeave();
    boolean canCalculateSalary();
    boolean canViewAllEmployees();
    boolean canModifyEmployeeDetails();
    boolean canGeneratePayslip();

    String getRoleName();
}