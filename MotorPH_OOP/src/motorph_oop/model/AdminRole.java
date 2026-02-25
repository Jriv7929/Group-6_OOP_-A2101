package motorph_oop.model;

public class AdminRole implements Role {

    @Override
    public boolean canManageEmployees() { return true; }

    @Override
    public boolean canApproveLeave() { return true; }

    @Override
    public boolean canCalculateSalary() { return true; }

    @Override
    public boolean canViewAllEmployees() { return true; }

    @Override
    public boolean canModifyEmployeeDetails() { return true; }

    @Override
    public boolean canGeneratePayslip() { return true; }

    @Override
    public String getRoleName() { return "Admin"; }
}