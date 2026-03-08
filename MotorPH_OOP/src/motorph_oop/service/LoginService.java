
package motorph_oop.service;

import motorph_oop.dao.LoginDAO;

// Service layer for handling login authentication,
// password validation, and account security rules.
 
public class LoginService {

    // DAO instance for login database operations
    private final LoginDAO loginDAO = new LoginDAO();

    // Login attempt tracking
    private int loginAttempts = 0;
    private static final int MAX_ATTEMPTS = 3;

    // Authenticates a user based on username and password.  
    public String[] authenticate(String username, String password) {

        if (username == null || username.trim().isEmpty()) {
            return null;
        }

        String[] user = loginDAO.findUserByUsername(username);

        if (user == null) {
            incrementAttempts();
            return null;
        }

        String storedPassword = user[4].trim();

        if (!storedPassword.equals(password)) {
            incrementAttempts();
            return null;
        }

        // Successful login → reset attempts
        loginAttempts = 0;
        return user;
    }

    // Increments failed login attempts.
    private void incrementAttempts() {
        loginAttempts++;
    }

    // Checks if account is locked due to too many failed attempts.  
    public boolean isLockedOut() {
        return loginAttempts >= MAX_ATTEMPTS;
    }

    // Returns remaining login attempts.   
    public int getRemainingAttempts() {
        return Math.max(0, MAX_ATTEMPTS - loginAttempts);
    }

    // Checks if password is the default password.  
    public boolean isDefaultPassword(String password) {
        return "1234".equals(password);
    }

    // Validates password against security rules.
    public boolean validatePasswordRules(String password) {

        if (password == null) {
            return false;
        }

        if (password.length() > 12) {
            return false;
        }

        boolean hasUpper = password.matches(".*[A-Z].*");
        boolean hasNumber = password.matches(".*[0-9].*");
        boolean hasSpecial = password.matches(".*[^a-zA-Z0-9].*");

        return hasUpper && hasNumber && hasSpecial;
    }

    // Evaluates password strength.
    public String getPasswordStrength(String password) {

        if (password == null || password.isEmpty()) {
            return "Weak";
        }

        int score = 0;

        if (password.length() >= 8) {
            score++;
        }

        if (password.matches(".*[A-Z].*")) {
            score++;
        }

        if (password.matches(".*[0-9].*")) {
            score++;
        }

        if (password.matches(".*[^a-zA-Z0-9].*")) {
            score++;
        }

        if (score <= 1) {
            return "Weak";
        }

        if (score <= 3) {
            return "Normal";
        }

        return "Strong";
    }

    // Resets user password after validation. 
    public boolean resetPassword(String username, String newPassword) {

        if (username == null || username.trim().isEmpty()) {
            return false;
        }

        if (!validatePasswordRules(newPassword)) {
            return false;
        }

        return loginDAO.updatePassword(username.trim(), newPassword);
    }

    // Generates retrieval code based on employee ID and birth year. 
    public String generateRetrievalCode(String employeeID, String birthYear) {

        if (employeeID == null || employeeID.length() < 3 || birthYear == null) {
            return null;
        }

        return employeeID.substring(employeeID.length() - 3) + birthYear;
    }

    // Validates retrieval code.  
    public boolean validateRetrievalCode(String expected, String entered) {

        if (expected == null || entered == null) {
            return false;
        }

        return expected.equals(entered);
    }
}