package ch.sportchef.server.services;

import ch.sportchef.server.dao.UserDAO;
import ch.sportchef.server.representations.User;

import javax.ws.rs.NotFoundException;
import java.util.List;

public class UserService implements Service {

    private final UserDAO userDAO;

    public UserService(final UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public List<User> readAllUsers() {
        return userDAO.readAllUsers();
    }

    public User readUserById(final long userId) {
        final User user = userDAO.readById(userId);
        if (user == null) {
            throw new NotFoundException(
                    String.format("user with id '%d'", userId));
        }
        return user;
    }

    public User storeUser(final User user) {
        if (user.getUserId() == 0) {
            final long newUserId = userDAO.create(user);
            return new User(newUserId, user.getFirstName(), user.getLastName(), user.getPhone(), user.getEmail());
        }

        userDAO.update(user);
        return user;
    }

    public void removeUser(final User user) {
        userDAO.delete(user);
    }
}
