package motorph_oop.util;

public class PasswordUtil {

    public static boolean isValid(String password) {

        if (password.length() > 12) return false;

        boolean hasUpper = password.matches(".*[A-Z].*");
        boolean hasDigit = password.matches(".*\\d.*");
        boolean hasSpecial = password.matches(".*[!@#$%^&*(),.?\":{}|<>].*");

        return hasUpper && hasDigit && hasSpecial;
    }

    public static String strength(String password) {

        if (password.isEmpty()) return "";

        int score = 0;
        if (password.matches(".*[A-Z].*")) score++;
        if (password.matches(".*\\d.*")) score++;
        if (password.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) score++;
        if (password.length() >= 8 && password.length() <= 12) score++;

        if (score <= 1) return "Weak";
        if (score <= 3) return "Normal";
        return "Strong";
    }
}