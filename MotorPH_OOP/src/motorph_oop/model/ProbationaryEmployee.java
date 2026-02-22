
package motorph_oop.model;

public class ProbationaryEmployee extends EmployeeData {

    @Override
    public double computeSalary(double hoursWorked) {
        return (getHourlyRate() * hoursWorked)
                + getRiceSubsidy();  // limited benefits
    }

    @Override
    public String toString() {
        return "[Probationary] " + super.toString();
    }
}
