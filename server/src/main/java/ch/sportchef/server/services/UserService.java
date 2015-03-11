package ch.sportchef.server.services;

import ch.sportchef.server.representations.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UserService implements Service {

    private Map<Long, User> testUsers = new HashMap<Long, User>() {{
        put(Long.valueOf(1L), new User(1L, "John", "Doe", "+41 79 123 45 67", "john.doe@sportchef.ch"));
        put(Long.valueOf(2L), new User(2L, "Jane", "Doe", "+41 79 234 56 78", "jane.doe@sportchef.ch"));
    }};

    private long nextFreeUserId = testUsers.size() + 1;

    public Optional<User> readUserById(final long id) {
        return Optional.ofNullable(testUsers.get(Long.valueOf(id)));
    }

    public User storeUser(final User user) {
        final User userToStore = user.getId() == 0 ? new User(nextFreeUserId++, user.getFirstName(), user.getLastName(), user.getPhone(), user.getEmail()) : user;
        testUsers.put(Long.valueOf(userToStore.getId()), userToStore);
        return userToStore;
    }
}
