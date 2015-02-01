package ch.sportchef.server.services;

import ch.sportchef.server.representations.User;

import javax.annotation.Nullable;

public class UserService implements Service {

    public @Nullable
    User readUserById(final long id) {
        return new User(id, "Firstname " + id, "Lastname " + id, "user" + id + "@sportchef.ch");
    }
}
