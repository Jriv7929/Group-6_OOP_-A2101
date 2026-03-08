
package motorph_oop.model;

public class HR extends Employee {

    @Override
    public double computeSalary(double hoursWorked) {

        return getHourlyRate() * hoursWorked;
    }

    public String getDepartment() {
        return "Human Resources";
    }
}