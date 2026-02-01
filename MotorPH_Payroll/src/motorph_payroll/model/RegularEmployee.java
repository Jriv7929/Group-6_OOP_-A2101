
package motorph_payroll.model;

public class RegularEmployee extends Employee {


public RegularEmployee(String employeeId, String name, double hourlyRate, int hoursWorked) {
super(employeeId, name, hourlyRate, hoursWorked);
}


@Override
public double calculateGrossPay() {
return hourlyRate * hoursWorked;
}


@Override
public double calculateDeductions() {
return calculateGrossPay() * 0.12; // tax + benefits
}


@Override
public double calculateNetPay() {
return calculateGrossPay() - calculateDeductions();
}
}