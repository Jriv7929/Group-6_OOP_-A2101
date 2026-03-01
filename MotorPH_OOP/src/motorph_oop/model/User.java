
package motorph_oop.model;

// Represents a system user.
// Stores authentication and identification details.

public class User {

    // User fields
    private String employeeNum;
    private String username;
    private String lastName;
    private String firstName;
    private String password;
    private String accessLevel;
    private String birthYear;

    // Constructor for User.
    public User(String employeeNum,
                String username,
                String lastName,
                String firstName,
                String password,
                String accessLevel,
                String birthYear) {

        this.employeeNum = employeeNum;
        this.username = username;
        this.lastName = lastName;
        this.firstName = firstName;
        this.password = password;
        this.accessLevel = accessLevel;
        this.birthYear = birthYear;
    }

    // Getters
    public String getEmployeeNum() {
        return employeeNum;
    }

    public String getUsername() {
        return username;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getPassword() {
        return password;
    }

    public String getAccessLevel() {
        return accessLevel;
    }

    public String getBirthYear() {
        return birthYear;
    }

    // Setters

    //Updates user password.
    public void setPassword(String password) {
        this.password = password;
    }
}