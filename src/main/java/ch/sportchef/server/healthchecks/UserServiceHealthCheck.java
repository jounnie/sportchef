package ch.sportchef.server.healthchecks;

import ch.sportchef.server.App;
import ch.sportchef.server.representations.User;
import ch.sportchef.server.services.UserService;
import com.codahale.metrics.health.HealthCheck;

import java.util.Optional;

public class UserServiceHealthCheck extends HealthCheck {

    private final UserService userService;
    private final User referenceUser;

    public UserServiceHealthCheck() {
        this.userService = App.getService(UserService.class);
        this.referenceUser = new User(1L, "John", "Doe", "+41 79 555 00 01", "john.doe@sportchef.ch");
    }

    @Override
    protected Result check() throws Exception {
        final long userId = referenceUser.getId();
        final Optional<User> checkUser = userService.readUserById(userId);

        if (checkUser.isPresent() && checkUser.get().equals(referenceUser)) {
            return Result.healthy("UserService is fine.");
        }

        return Result.unhealthy("UserService has problems returning the correct reference user!");
    }
}
