package motorph_oop.service;

import motorph_oop.dao.LeaveDAO;
import motorph_oop.model.LeaveRequest;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

// Service layer for handling leave-related operations.
// Acts as a bridge between the UI layer and LeaveDAO.
 
public class LeaveService {

    // DAO instance for leave database operations
    private final LeaveDAO leaveDAO = new LeaveDAO();

    // Retrieves all leave requests.
    public List<LeaveRequest> getAllLeaves() {
        return leaveDAO.loadAllLeaves();
    }

    // Submits a new leave request.   
    public LeaveRequest submitLeave(
            String leaveType,
            LocalDate startDate,
            LocalDate endDate,
            String reason) {

        // Validate selected dates
        validateLeaveDates(startDate, endDate);

        // Calculate total leave days
        long days = calculateLeaveDays(startDate, endDate);

        // Create leave request object
        LeaveRequest request =
                new LeaveRequest(leaveType, startDate, endDate, days, reason);

        // Save to file
        leaveDAO.saveLeave(request);

        return request;
    }

    // Validates leave start and end dates. 
    public void validateLeaveDates(LocalDate start, LocalDate end) {

        if (start == null || end == null) {
            throw new IllegalArgumentException("Please select dates.");
        }

        if (end.isBefore(start)) {
            throw new IllegalArgumentException("End date cannot be before start date.");
        }
    }

    // Calculates the total number of leave days (inclusive).    
    public long calculateLeaveDays(LocalDate start, LocalDate end) {
        return ChronoUnit.DAYS.between(start, end) + 1;
    }

    // Approves a leave request.    
    public void approveLeave(LeaveRequest request) {

        request.setStatus("Approved");
        updateLeaveStatus(request);
    }

    // Rejects a leave request.   
    public void rejectLeave(LeaveRequest request, String rejectionReason) {

        request.setStatus("Rejected");
        updateLeaveStatus(request);
    }

    // Updates the leave status in the data source.  
    private void updateLeaveStatus(LeaveRequest updated) {

        List<LeaveRequest> all =
                leaveDAO.loadAllLeaves();

        for (LeaveRequest r : all) {

            if (r.getStartDate().equals(updated.getStartDate()) &&
                r.getEndDate().equals(updated.getEndDate()) &&
                r.getReason().equals(updated.getReason())) {

                r.setStatus(updated.getStatus());
            }
        }

        leaveDAO.overwriteAll(all);
    }
}