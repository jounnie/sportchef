package ch.sportchef.server.utils;

import ch.sportchef.server.representations.User;

public class UserGenerator {

    public static User getJohnDoe(final long id) {
        return new User(id, "John", "Doe", "+41 79 555 00 01", "john.doe@sportchef.ch");
    }

    public static User getJaneDoe(final long id) {
        return new User(id, "Jane", "Doe", "+41 79 555 00 02", "jane.doe@sportchef.ch");
    }

    public static User getJimDoe(final long id) {
        return new User(id, "Jim", "Doe", "+41 79 555 00 03", "jim.doe@sportchef.ch");
    }

    public static User getJoyDoe(final long id) {
        return new User(id, "Joy", "Doe", "+41 79 555 00 04", "joy.doe@sportchef.ch");
    }
}
