package ch.sportchef.server.services;

import ch.sportchef.server.representations.User;
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

        final User user = optionalUser.get();
        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getFirstName()).isEqualTo("John");
        assertThat(user.getLastName()).isEqualTo("Doe");
        assertThat(user.getPhone()).isEqualTo("+41 79 123 45 67");
        assertThat(user.getEmail()).isEqualTo("john.doe@sportchef.ch");
    }

    @Test
    public void returnJaneDoe() {
        final Optional<User> optionalUser = userService.readUserById(2L);
        assertThat(optionalUser.isPresent()).isTrue();

        final User user = optionalUser.get();
        assertThat(user.getId()).isEqualTo(2L);
        assertThat(user.getFirstName()).isEqualTo("Jane");
        assertThat(user.getLastName()).isEqualTo("Doe");
        assertThat(user.getPhone()).isEqualTo("+41 79 234 56 78");
        assertThat(user.getEmail()).isEqualTo("jane.doe@sportchef.ch");
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
        final User nonExistingUser = new User(3L, null, null, null, null);
        userService.removeUser(nonExistingUser);
        assertThat(userService.readUserById(3L).isPresent()).isFalse();
    }
}
