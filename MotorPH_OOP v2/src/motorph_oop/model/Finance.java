
package motorph_oop.model;

public class Finance extends Employee {

    @Override
    public double computeSalary(double hoursWorked) {

        return getHourlyRate() * hoursWorked;
    }

    public String getDepartment() {
        return "Finance";
    }
}