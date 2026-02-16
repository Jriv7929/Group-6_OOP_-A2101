
package motorph_payroll.department;

public class SalesDepartment implements Department {

    @Override
    public String getDepartmentName() {
        return "Sales";
    }

    @Override
    public int getAccessLevel() {
        return 3;
    }
}