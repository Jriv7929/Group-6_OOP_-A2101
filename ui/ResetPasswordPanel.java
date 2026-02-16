
package motorph_payroll.ui;

import motorph_payroll.service.LoginService;
import javax.swing.*;
import motorph_payroll.model.User;
import motorph_payroll.ui.LoginPanel;

public class ResetPasswordPanel extends JDialog {

    private LoginService loginService = new LoginService();

    public ResetPasswordPanel(JFrame parent) {
        super(parent, "Forgot Password", true);
        startReset();
    }
    private void startReset() {

        // STEP 1 — Verification code
        JTextField codeField = new JTextField();

        int verify = JOptionPane.showConfirmDialog(
                this,
                new Object[]{"Verification Code:", codeField},
                "Verify Identity",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (verify != JOptionPane.OK_OPTION) return;

        User user = loginService.verifyResetCode(
                codeField.getText().trim()
        );

        if (user == null) {
            JOptionPane.showMessageDialog(this,
                    "Invalid verification code.");
            return;
        }

        // STEP 2 — Password reset
        JPasswordField newPass = new JPasswordField();
        JPasswordField confirmPass = new JPasswordField();

        int reset = JOptionPane.showConfirmDialog(
                this,
                new Object[]{
                        "New Password:", newPass,
                        "Confirm Password:", confirmPass
                },
                "Reset Password",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (reset != JOptionPane.OK_OPTION) return;

        String pwd = String.valueOf(newPass.getPassword());

        if (!pwd.equals(String.valueOf(confirmPass.getPassword()))) {
            JOptionPane.showMessageDialog(this,
                    "Passwords do not match.");
            return;
        }

        if (!loginService.isValidPassword(pwd)) {
            JOptionPane.showMessageDialog(this,
                    "Password must contain:\n"
                            + "• 8 characters\n"
                            + "• 1 uppercase\n"
                            + "• 1 number\n"
                            + "• 1 special character");
            return;
        }

        loginService.resetPassword(user, pwd);

        JOptionPane.showMessageDialog(this,
                "Password reset successful.");
    }
}