
package motorph_payroll.model;

public class ProbationaryEmployee extends Employee {

public ProbationaryEmployee(String employeeId, String name, double hourlyRate, int hoursWorked) {
super(employeeId, name, hourlyRate, hoursWorked);
}

@Override
public double calculateGrossPay() {
return hourlyRate * hoursWorked;
}

@Override
public double calculateDeductions() {
return calculateGrossPay() * 0.08; // lower deductions
}

@Override
public double calculateNetPay() {
return calculateGrossPay() - calculateDeductions();
}
}
