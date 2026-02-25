
package motorph_oop.model;

public class User {

    private String employeeNo;
    private String username;
    private String password;
    private Role role;

    public User(String employeeNo, String username, String password, Role role) {
        this.employeeNo = employeeNo;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getEmployeeNo() {
        return employeeNo;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }
}
