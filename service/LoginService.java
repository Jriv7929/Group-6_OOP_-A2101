
package motorph_payroll.service;

import motorph_payroll.dao.LoginDAO;
import motorph_payroll.model.User;

public class LoginService {

    private LoginDAO loginDAO = new LoginDAO();

    public User verifyResetCode(String code) {
        return loginDAO.findUserByVerificationCode(code);
    }

    public boolean isValidPassword(String password) {

        return password.length() >= 8
                && password.matches(".*[A-Z].*")
                && password.matches(".*\\d.*")
                && password.matches(".*[!@#$%^&*()].*");
    }

    public boolean resetPassword(User user, String newPassword) {
        return loginDAO.updatePassword(
                user.getUsername(),
                newPassword
        );
    }

    public boolean isDefaultPassword(User user) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public User login(String inputUsername, String inputPassword) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public boolean resetPassword(String username, String newPassword) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
    


