package ch.sportchef.server.services;

import ch.sportchef.server.representations.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
        assertTrue(optionalUser.isPresent());

        final User user = optionalUser.get();
        assertEquals("wrong user id", 1L, user.getId());
        assertEquals("wrong first name", "John", user.getFirstName());
        assertEquals("wrong last name", "Doe", user.getLastName());
        assertEquals("wrong phone number", "+41 79 123 45 67", user.getPhone());
        assertEquals("wrong email address", "john.doe@sportchef.ch", user.getEmail());
    }

    @Test
    public void returnJaneDoe() {
        final Optional<User> optionalUser = userService.readUserById(2L);
        assertTrue(optionalUser.isPresent());

        final User user = optionalUser.get();
        assertEquals("wrong user id", 2L, user.getId());
        assertEquals("wrong first name", "Jane", user.getFirstName());
        assertEquals("wrong last name", "Doe", user.getLastName());
        assertEquals("wrong phone number", "+41 79 234 56 78", user.getPhone());
        assertEquals("wrong email address", "jane.doe@sportchef.ch", user.getEmail());
    }

    @Test
    public void returnNoUser() {
        final Optional<User> optionalUser = userService.readUserById(3L);
        assertFalse(optionalUser.isPresent());
    }
}
