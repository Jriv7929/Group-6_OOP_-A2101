
package motorph_payroll.department;

public class HRDepartment implements Department {

    @Override
    public String getDepartmentName() {
        return "HR";
    }

    @Override
    public int getAccessLevel() {
        return 4;
    }
}