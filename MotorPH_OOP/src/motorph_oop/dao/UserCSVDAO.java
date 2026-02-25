
package motorph_oop.dao;

import motorph_oop.model.*;
import java.io.*;
import java.util.*;

public class UserCSVDAO implements UserDAO {

    private static final String FILE_PATH = "resources/MotorPH_EmployeeLogin.csv";

    @Override
    public User findByUsername(String username) {

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {

            String line;
            br.readLine(); // skip header

            while ((line = br.readLine()) != null) {

                String[] data = line.split(",");

                String empNo = data[0];
                String user = data[1];
                String pass = data[2];
                String roleStr = data[3];

                if (user.equals(username)) {

                    Role role = createRole(roleStr);

                    return new User(empNo, user, pass, role);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    // Convert String role from CSV into Role object
    private Role createRole(String roleName) {

        switch (roleName.trim()) {

            case "Admin":
                return new AdminRole();

            case "HR Staff":
                return new HRRole();

            case "Finance Staff":
                return new FinanceRole();

            case "IT Department":
                return new ITRole();

            case "Logistics Department":
                return new LogisticsRole();

            case "Sales Department":
                return new LogisticsRole();

            case "Customer Service Department":
                return new LogisticsRole();

            default:
                return null;
        }
    }
}
