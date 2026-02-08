
package motorph_payroll.dao;

import motorph_payroll.model.User;
import motorph_payroll.utils.Constants;

import java.io.*;

public class LoginDAO {

    public User authenticate(String username, String password) {

        File file = new File(Constants.LOGIN_CSV);

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            String line;
            boolean header = true;

            while ((line = reader.readLine()) != null) {

                if (header) {
                    header = false;
                    continue;
                }

                String[] parts = line.split(",", -1);

                if (parts.length >= 6) {
                    if (parts[1].equalsIgnoreCase(username)
                            && parts[4].equals(password)) {

                        return new User(
                                parts[0].trim(),
                                parts[1].trim(),
                                parts[2].trim(),
                                parts[3].trim(),
                                parts[4].trim(),
                                parts[5].trim()
                        );
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null; // login failed
    }
    
public boolean updatePassword(String username, String newPassword) {

    File original = new File(Constants.LOGIN_CSV);
    File temp = new File("temp_login.csv");

    boolean updated = false;

    try (
        BufferedReader br = new BufferedReader(new FileReader(original));
        PrintWriter pw = new PrintWriter(new FileWriter(temp))
    ) {
        String line;
        boolean header = true;

        while ((line = br.readLine()) != null) {

            if (header) {
                pw.println(line);
                header = false;
                continue;
            }

            String[] data = line.split(",", -1);

            if (data[1].equals(username)) {
                data[4] = newPassword;
                updated = true;
            }

            pw.println(String.join(",", data));
        }

    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }

    if (updated) {
        original.delete();
        temp.renameTo(original);
    }

    return updated;
}
    
    public User findUserByVerificationCode(String verificationCode) {

    try (BufferedReader br = new BufferedReader(
            new FileReader(Constants.EMPLOYEE_CSV))) {

        String line;
        boolean header = true;

        while ((line = br.readLine()) != null) {

            if (header) {
                header = false;
                continue;
            }

            String[] data = line.split(",", -1);

            String employeeNo = data[0].trim();
            String birthDate  = data[6].trim(); // YYYY-MM-DD
            String birthYear  = birthDate.substring(0, 4);

            String expectedCode =
                    employeeNo.substring(employeeNo.length() - 3)
                    + birthYear;

            if (expectedCode.equals(verificationCode)) {

                return new User(
                        employeeNo,
                        data[1].trim(),   // username
                        data[4].trim()    // password
                );
            }
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return null;
}
    
    
    
}