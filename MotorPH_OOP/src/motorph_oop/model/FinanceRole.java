
package motorph_oop.model;

public class FinanceRole implements Role {

    @Override
    public boolean canManageEmployees() { return false; }

    @Override
    public boolean canApproveLeave() { return false; }

    @Override
    public boolean canCalculateSalary() { return true; }

    @Override
    public boolean canViewAllEmployees() { return false; }

    @Override
    public boolean canModifyEmployeeDetails() { return false; }

    @Override
    public boolean canGeneratePayslip() { return true; }

    @Override
    public String getRoleName() { return "Finance Staff"; }
}
