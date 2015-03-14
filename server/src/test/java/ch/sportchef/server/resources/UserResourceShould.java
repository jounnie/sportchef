package ch.sportchef.server.resources;

import ch.sportchef.server.App;
import ch.sportchef.server.SportChefConfiguration;
import ch.sportchef.server.representations.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

public class UserResourceShould {

    @ClassRule
    public static final DropwizardAppRule<SportChefConfiguration> RULE = new DropwizardAppRule<>(App.class, "config.yaml");

    @Test
    public void returnJohnDoe() {
        final WebTarget target = ClientBuilder.newClient().target(
                String.format("http://localhost:%d/api/user/1", RULE.getLocalPort()));

        final Response response = target
                .request(MediaType.APPLICATION_JSON_TYPE)
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .get();

        assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());

        final User user = response.readEntity(User.class);
        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getFirstName()).isEqualTo("John");
        assertThat(user.getLastName()).isEqualTo("Doe");
        assertThat(user.getPhone()).isEqualTo("+41 79 123 45 67");
        assertThat(user.getEmail()).isEqualTo("john.doe@sportchef.ch");
    }

    @Test
    public void returnJaneDoe() {
        final WebTarget target = ClientBuilder.newClient().target(
                String.format("http://localhost:%d/api/user/2", RULE.getLocalPort()));

        final Response response = target
                .request(MediaType.APPLICATION_JSON_TYPE)
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .get();

        assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());

        final User user = response.readEntity(User.class);
        assertThat(user.getId()).isEqualTo(2L);
        assertThat(user.getFirstName()).isEqualTo("Jane");
        assertThat(user.getLastName()).isEqualTo("Doe");
        assertThat(user.getPhone()).isEqualTo("+41 79 234 56 78");
        assertThat(user.getEmail()).isEqualTo("jane.doe@sportchef.ch");
    }

    @Test
    public void returnNotFound() {
        final WebTarget target = ClientBuilder.newClient().target(
                String.format("http://localhost:%d/api/user/3", RULE.getLocalPort()));

        final Response response = target
                .request(MediaType.APPLICATION_JSON_TYPE)
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .get();

        assertThat(response.getStatus()).isEqualTo(Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    public void createTheNewUserJimDoe() throws JsonProcessingException {
        final WebTarget target = ClientBuilder.newClient().target(
                String.format("http://localhost:%d/api/user", RULE.getLocalPort()));

        final User user = new User(0L, "Jim", "Doe", "+41 79 098 76 54", "jim.doe@sportchef.ch");

        final Response response = target
                .request(MediaType.APPLICATION_JSON_TYPE)
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.json(user));

        assertThat(response.getStatus()).isEqualTo(Response.Status.CREATED.getStatusCode());

        final URI location = response.getLocation();
        assertThat(location).isNotNull();

        final String path = location.getPath();
        final long newId = Long.parseLong(path.substring(path.lastIndexOf("/") + 1));
        assertThat(newId).isGreaterThan(0);
    }
}
