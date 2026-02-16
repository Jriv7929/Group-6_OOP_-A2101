
package motorph_payroll.department;

public class CSDepartment implements Department {

    @Override
    public String getDepartmentName() {
        return "Customer Service";
    }

    @Override
    public int getAccessLevel() {
        return 2;
    }
}
