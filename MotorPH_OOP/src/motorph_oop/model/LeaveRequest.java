
package motorph_oop.model;

import java.time.LocalDate;

// Represents a leave request submitted by an employee.

public class LeaveRequest {

    // Leave details
    private String leaveType;
    private LocalDate startDate;
    private LocalDate endDate;
    private long totalDays;
    private String reason;
    private String status;

    // Constructor for LeaveRequest.
    // Default status is set to "Pending".   
    public LeaveRequest(String leaveType,
                        LocalDate startDate,
                        LocalDate endDate,
                        long totalDays,
                        String reason) {

        this.leaveType = leaveType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalDays = totalDays;
        this.reason = reason;
        this.status = "Pending";
    }

    // Getters
    public String getLeaveType() {
        return leaveType;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public long getTotalDays() {
        return totalDays;
    }

    public String getReason() {
        return reason;
    }

    public String getStatus() {
        return status;
    }

    // Setters
    
    //Updates leave request status.
    public void setStatus(String status) {
        this.status = status;
    }
}