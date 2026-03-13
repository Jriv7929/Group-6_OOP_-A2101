
package motorph_oop.dao;

import motorph_oop.util.Constants;
import motorph_oop.ui.LoginPanel;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

 // Data Access Object for handling login-related operations.
 // Reads and updates user credentials from the LOGIN_CSV file.

public class LoginDAO {

    // Finds a user by username.
     
    public String[] findUserByUsername(String username) {

        File file = new File(Constants.LOGIN_CSV);

        // If file does not exist, return null
        if (!file.exists()) {
            return null;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            String line;
            boolean header = true;

            // Read file line by line
            while ((line = reader.readLine()) != null) {

                // Skip header row
                if (header) {
                    header = false;
                    continue;
                }

                String[] parts = line.split(",", -1);

                // Check if username matches (case-insensitive)
                if (parts.length >= 6 &&
                        parts[1].trim().equalsIgnoreCase(username.trim())) {

                    return parts;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    // Updates a user's password.
  
    public boolean updatePassword(String username, String newPassword) {

        File file = new File(Constants.LOGIN_CSV);

        // If file does not exist, return false
        if (!file.exists()) {
            return false;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            List<String> lines = new ArrayList<>();
            String line;
            boolean header = true;
            boolean updated = false;

            // Read all lines and modify matching user
            while ((line = reader.readLine()) != null) {

                // Preserve header row
                if (header) {
                    lines.add(line);
                    header = false;
                    continue;
                }

                String[] parts = line.split(",", -1);

                // Check if username matches
                if (parts.length >= 6 &&
                        parts[1].trim().equalsIgnoreCase(username.trim())) {

                    parts[4] = newPassword;   // Password column
                    line = String.join(",", parts);
                    updated = true;
                }

                lines.add(line);
            }

            // If no matching user was found
            if (!updated) {
                return false;
            }

            // Rewrite the file with updated content
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {

                for (String l : lines) {
                    writer.write(l);
                    writer.newLine();
                }
            }

            return true;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
}