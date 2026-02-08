
package motorph_payroll.department;

public class ITDepartment implements Department {

    @Override
    public String getDepartmentName() {
        return "IT";
    }

    @Override
    public int getAccessLevel() {
        return 5; // highest access
    }
}