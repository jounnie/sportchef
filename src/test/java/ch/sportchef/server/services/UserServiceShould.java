package ch.sportchef.server.services;

import ch.sportchef.server.representations.User;
import ch.sportchef.server.utils.UserGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class UserServiceShould {

    private UserService userService;

    @Before
    public void setUp() {
        userService = new UserService();
    }

    @After
    public void tearDown() {
        userService = null;
    }

    @Test
    public void returnJohnDoe() {
        final Optional<User> optionalUser = userService.readUserById(1L);
        assertThat(optionalUser.isPresent()).isTrue();
        assertThat(optionalUser.get()).isEqualTo(UserGenerator.getUser(1L));
    }

    @Test
    public void returnJaneDoe() {
        final Optional<User> optionalUser = userService.readUserById(2L);
        assertThat(optionalUser.isPresent()).isTrue();
        assertThat(optionalUser.get()).isEqualTo(UserGenerator.getUser(2L));
    }

    @Test
    public void returnNoUser() {
        final Optional<User> optionalUser = userService.readUserById(3L);
        assertThat(optionalUser.isPresent()).isFalse();
    }

    @Test
    public void removeJaneDoe() {
        final Optional<User> janeDoe = userService.readUserById(2L);
        assertThat(janeDoe.isPresent()).isTrue();
        userService.removeUser(janeDoe.get());
        assertThat(userService.readUserById(2L).isPresent()).isFalse();
    }

    @Test
    public void removeNonExistingUser() {
        final User nonExistingUser = UserGenerator.getUser(3L);
        userService.removeUser(nonExistingUser);
        assertThat(userService.readUserById(3L).isPresent()).isFalse();
    }
}
