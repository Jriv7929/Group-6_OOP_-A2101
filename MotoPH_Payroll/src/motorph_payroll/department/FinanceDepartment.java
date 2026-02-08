
package motorph_payroll.department;

public class FinanceDepartment implements Department {

    @Override
    public String getDepartmentName() {
        return "Finance";
    }

    @Override
    public int getAccessLevel() {
        return 5;
    }
}
