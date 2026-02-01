
package motorph_payroll.model;

public abstract class Employee implements PayrollCalculations {

protected String employeeId;
protected String name;
protected double hourlyRate;
protected int hoursWorked;

public Employee(String employeeId, String name, double hourlyRate, int hoursWorked) {
this.employeeId = employeeId;
this.name = name;
this.hourlyRate = hourlyRate;
this.hoursWorked = hoursWorked;
}

public String getEmployeeId() {
return employeeId;
}

public String getName() {
return name;
}

public double getHourlyRate() {
return hourlyRate;
}

public int getHoursWorked() {
return hoursWorked;
}
}
