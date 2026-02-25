
package motorph_oop.dao;

import motorph_oop.model.User;

public interface UserDAO {
    User findByUsername(String username);
}