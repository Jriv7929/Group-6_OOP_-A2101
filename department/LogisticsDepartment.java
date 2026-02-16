
package motorph_payroll.department;

public class LogisticsDepartment implements Department {

    @Override
    public String getDepartmentName() {
        return "Logistics";
    }

    @Override
    public int getAccessLevel() {
        return 2;
    }
}