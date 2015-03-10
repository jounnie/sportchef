package ch.sportchef.server.resources;

import ch.sportchef.server.App;
import ch.sportchef.server.SportChefConfiguration;
import ch.sportchef.server.representations.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.core.MediaType;
import java.net.URI;

import static org.fest.assertions.api.Assertions.assertThat;

public class UserResourceShould {

    @ClassRule
    public static final DropwizardAppRule<SportChefConfiguration> RULE = new DropwizardAppRule<>(App.class, "config.yaml");

    @Test
    public void returnJohnDoe() {
        final Client client = new Client();

        final ClientResponse response = client.resource(
                String.format("http://localhost:%d/api/user/1", RULE.getLocalPort()))
                .get(ClientResponse.class);

        assertThat(response.getStatus()).isEqualTo(ClientResponse.Status.OK.getStatusCode());

        final User user = response.getEntity(User.class);
        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getFirstName()).isEqualTo("John");
        assertThat(user.getLastName()).isEqualTo("Doe");
        assertThat(user.getPhone()).isEqualTo("+41 79 123 45 67");
        assertThat(user.getEmail()).isEqualTo("john.doe@sportchef.ch");
    }

    @Test
    public void returnJaneDoe() {
        final Client client = new Client();

        final ClientResponse response = client.resource(
                String.format("http://localhost:%d/api/user/2", RULE.getLocalPort()))
                .get(ClientResponse.class);

        assertThat(response.getStatus()).isEqualTo(ClientResponse.Status.OK.getStatusCode());

        final User user = response.getEntity(User.class);
        assertThat(user.getId()).isEqualTo(2L);
        assertThat(user.getFirstName()).isEqualTo("Jane");
        assertThat(user.getLastName()).isEqualTo("Doe");
        assertThat(user.getPhone()).isEqualTo("+41 79 234 56 78");
        assertThat(user.getEmail()).isEqualTo("jane.doe@sportchef.ch");
    }

    @Test
    public void returnNotFound() {
        final Client client = new Client();

        final ClientResponse response = client.resource(
                String.format("http://localhost:%d/api/user/3", RULE.getLocalPort()))
                .get(ClientResponse.class);

        assertThat(response.getStatus()).isEqualTo(ClientResponse.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    public void createTheNewUserJimDoe() throws JsonProcessingException {
        final Client client = new Client();
        final ObjectMapper mapper = Jackson.newObjectMapper();

        final User user = new User(0L, "Jim", "Doe", "+41 79 098 76 54", "jim.doe@sportchef.ch");
        final String userJSON = mapper.writeValueAsString(user);

        final ClientResponse response = client.resource(
                String.format("http://localhost:%d/api/user", RULE.getLocalPort()))
                .accept(MediaType.APPLICATION_JSON)
                .type(MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, userJSON);

        assertThat(response.getStatus()).isEqualTo(ClientResponse.Status.CREATED.getStatusCode());

        final URI location = response.getLocation();
        assertThat(location).isNotNull();

        final String path = location.getPath();
        final long newId = Long.parseLong(path.substring(path.lastIndexOf("/") + 1));
        assertThat(newId).isGreaterThan(0);
    }
}
