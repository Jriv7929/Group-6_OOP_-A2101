
package motorph_payroll.model;

public class User {

    private String employeeNum;
    private String username;
    private String lastName;
    private String firstName;
    private String password;
    private String accessLevel;

    public User(String employeeNum, String username, String lastName,
                String firstName, String password, String accessLevel) {
        this.employeeNum = employeeNum;
        this.username = username;
        this.lastName = lastName;
        this.firstName = firstName;
        this.password = password;
        this.accessLevel = accessLevel;
    }

    public User(String employeeNo, String trim, String trim0) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public String getEmployeeNum() { return employeeNum; }
    public String getUsername() { return username; }
    public String getLastName() { return lastName; }
    public String getFirstName() { return firstName; }
    public String getPassword() { return password; }
    public String getAccessLevel() { return accessLevel; }

    public void setPassword(String password) {
        this.password = password;
    }
}


