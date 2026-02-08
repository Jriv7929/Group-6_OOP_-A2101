
package motorph_payroll.dao;

import java.util.List;
import motorph_payroll.model.Employee;

public interface EmployeeDAO {

    List<Employee> getAllEmployees();

    void saveAllEmployees(List<Employee> employees);
}