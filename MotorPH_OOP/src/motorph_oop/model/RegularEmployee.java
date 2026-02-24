
package motorph_oop.model;

public class RegularEmployee extends Employee {

    @Override
    public double computeSalary(double hoursWorked) {
        return (getHourlyRate() * hoursWorked)
                + getRiceSubsidy()
                + getPhoneAllowance()
                + getClothingAllowance();
    }

    @Override
    public String toString() {
        return "[Regular] " + super.toString();
    
    }
}