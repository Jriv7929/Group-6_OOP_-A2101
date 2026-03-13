
package motorph_oop.model;

// Logistics / Customer Service / Sales department

public class CSLogistics extends Employee {

    @Override
    public double computeSalary(double hoursWorked) {

        return getHourlyRate() * hoursWorked;
    }

    public String getDepartment() {
        return "Customer Service / Logistics";
    }
}