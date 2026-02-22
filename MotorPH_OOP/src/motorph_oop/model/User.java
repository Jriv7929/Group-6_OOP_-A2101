
package motorph_oop.model;

public class User {

    private String username;
    private String password;
    private String role; // HR, IT, Finance, Non-Admin

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getRole() { return role; }

    public boolean isAdmin() {
        return role.equalsIgnoreCase("HR") ||
               role.equalsIgnoreCase("IT") ||
               role.equalsIgnoreCase("Finance");
        
    }

    @Override
    public String toString() {
        return "User: " + username + " | Role: " + role;
    }
}
