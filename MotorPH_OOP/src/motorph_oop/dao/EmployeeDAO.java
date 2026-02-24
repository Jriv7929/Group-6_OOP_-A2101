
package motorph_oop.dao;

import java.util.List;
import motorph_oop.model.Employee;

public interface EmployeeDAO {
    List<Employee> getAll();
    Employee getById(String id);
    void save(Employee employee);
    void update(Employee employee);
}
