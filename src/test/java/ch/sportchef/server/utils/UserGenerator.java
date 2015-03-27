package ch.sportchef.server.utils;

import ch.sportchef.server.representations.User;

import java.util.HashMap;
import java.util.Map;

public class UserGenerator {

    private final static Map<Long, User> TEST_USERS = new HashMap<Long, User>() {{
        put(Long.valueOf(1L), new User(1L, "John", "Doe", "+41 79 123 45 67", "john.doe@sportchef.ch"));
        put(Long.valueOf(2L), new User(2L, "Jane", "Doe", "+41 79 234 56 78", "jane.doe@sportchef.ch"));
        put(Long.valueOf(3L), new User(3L, "Jim", "Doe", "+41 79 345 67 89", "jim.doe@sportchef.ch"));
    }};

    public static User getUser(final long id) {
        return TEST_USERS.get(Long.valueOf(id));
    }
}
