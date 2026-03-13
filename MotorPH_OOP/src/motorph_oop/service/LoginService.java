
package motorph_oop.service;

import motorph_oop.dao.LoginDAO;
import motorph_oop.model.*;

public class LoginService {

    private LoginDAO loginDAO = new LoginDAO();

    // LOGIN AUTHENTICATION
    public Employee authenticate(String username, String password) {

        String[] user = loginDAO.findUserByUsername(username);

        if (user == null) {
            return null;
        }

        String storedPassword = user[4];

        if (!storedPassword.equals(password)) {
            return null;
        }

        String empNo = user[0];
        String lastName = user[2];
        String firstName = user[3];
        String role = user[5];

        Employee employee;

        switch(role.toUpperCase()) {

            case "ADMIN":
                employee = new AdminAccess();
                break;

            case "IT":
                employee = new ITAccess();
                break;

            case "HR":
                employee = new HRAccess();
                break;

            case "FINANCE":
                employee = new FinanceAccess();
                break;

            default:
                employee = new EmployeeAccess();
        }

        employee.setEmpNo(empNo);
        employee.setLastName(lastName);
        employee.setFirstName(firstName);

        return employee;
    }


    // PASSWORD VALIDATION
    public boolean validatePasswordRules(String password) {

        if (password.length() > 12) {
            return false;
        }

        boolean hasUpper = password.matches(".*[A-Z].*");
        boolean hasNumber = password.matches(".*[0-9].*");
        boolean hasSpecial = password.matches(".*[!@#$%^&*()].*");

        return hasUpper && hasNumber && hasSpecial;
    }


    // PASSWORD STRENGTH CHECK
    public String getPasswordStrength(String password) {

        int score = 0;

        if (password.length() >= 8) score++;
        if (password.matches(".*[A-Z].*")) score++;
        if (password.matches(".*[0-9].*")) score++;
        if (password.matches(".*[!@#$%^&*()].*")) score++;

        if (score >= 4) {
            return "Strong";
        } else if (score >= 2) {
            return "Normal";
        } else {
            return "Weak";
        }
    }


    // RESET PASSWORD
    public boolean resetPassword(String username, String newPassword) {

        return loginDAO.updatePassword(username, newPassword);
    }
}