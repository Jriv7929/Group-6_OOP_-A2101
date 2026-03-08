
package motorph_oop.model;

public class IT extends Employee {

    @Override
    public double computeSalary(double hoursWorked) {

        return getHourlyRate() * hoursWorked;
    }

    public String getDepartment() {
        return "IT Department";
    }
}