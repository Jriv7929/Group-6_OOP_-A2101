package motorph_oop.service;

import motorph_oop.dao.*;
import motorph_oop.model.User;

public class AuthService {

    private UserDAO userDAO;

    public AuthService() {
        userDAO = new UserCSVDAO();
    }

    public User login(String username, String password) {

        User user = userDAO.findByUsername(username);

        if (user == null) {
            return null;
        }

        if (user.getPassword().equals(password)) {
            return user;
        }

        return null;
    }
}