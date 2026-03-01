package motorph_oop.dao;

import motorph_oop.model.LeaveRequest;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

 // Data Access Object for handling LeaveRequest records.
 // Handles reading from and writing to the leave_requests.csv file.
 
public class LeaveDAO {

    // Path to the CSV file storing leave requests
    private static final String FILE_PATH =
            "src/motorph_oop/resources/leave_requests.csv";

    // Loads all leave requests from the CSV file.
     
    public List<LeaveRequest> loadAllLeaves() {

        List<LeaveRequest> leaves = new ArrayList<>();
        File file = new File(FILE_PATH);

        // If file does not exist, return empty list
        if (!file.exists()) {
            return leaves;
        }

        try (BufferedReader reader =
                     new BufferedReader(new FileReader(file))) {

            String line;

            // Read file line by line
            while ((line = reader.readLine()) != null) {

                String[] parts = line.split(",");

                // Create LeaveRequest object from CSV values
                LeaveRequest request = new LeaveRequest(
                        parts[0],
                        LocalDate.parse(parts[1]),
                        LocalDate.parse(parts[2]),
                        Long.parseLong(parts[3]),
                        parts[4]
                );

                // Set leave status
                request.setStatus(parts[5]);

                // Add to list
                leaves.add(request);
            }

        } catch (Exception e) {
            throw new RuntimeException("Error loading leave file", e);
        }

        return leaves;
    }

    // Saves a single leave request by appending it to the CSV file.
    
    public void saveLeave(LeaveRequest request) {

        try (PrintWriter writer =
                     new PrintWriter(new FileWriter(FILE_PATH, true))) {

            writer.println(
                    request.getLeaveType() + "," +
                    request.getStartDate() + "," +
                    request.getEndDate() + "," +
                    request.getTotalDays() + "," +
                    request.getReason().replace(",", " ") + "," +
                    request.getStatus()
            );

        } catch (Exception e) {
            throw new RuntimeException("Error saving leave", e);
        }
    }

    // Overwrites the entire CSV file with updated leave records.
   
    public void overwriteAll(List<LeaveRequest> leaves) {

        try (PrintWriter writer =
                     new PrintWriter(new FileWriter(FILE_PATH))) {

            for (LeaveRequest r : leaves) {

                writer.println(
                        r.getLeaveType() + "," +
                        r.getStartDate() + "," +
                        r.getEndDate() + "," +
                        r.getTotalDays() + "," +
                        r.getReason().replace(",", " ") + "," +
                        r.getStatus()
                );
            }

        } catch (Exception e) {
            throw new RuntimeException("Error updating leave file", e);
        }
    }
}