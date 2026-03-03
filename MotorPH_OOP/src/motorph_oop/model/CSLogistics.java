
package motorph_oop.model;

// Logistics / Customer Service / Sales department

public class CSLogistics implements Role {

    @Override
    public boolean canManageEmployees() { return false; }

    @Override
    public boolean canApproveLeave() { return false; }

    @Override
    public boolean canCalculateSalary() { return false; }

    @Override
    public boolean canViewAllEmployees() { return false; }

    @Override
    public boolean canModifyEmployeeDetails() { return false; }

    @Override
    public boolean canGeneratePayslip() { return false; }

    @Override
    public String getRoleName() { return "Logistics Department"; }
}