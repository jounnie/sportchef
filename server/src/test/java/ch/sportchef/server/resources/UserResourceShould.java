package ch.sportchef.server.resources;

import ch.sportchef.server.App;
import ch.sportchef.server.SportChefConfiguration;
import ch.sportchef.server.representations.User;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.ClassRule;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class UserResourceShould {

    @ClassRule
    public static final DropwizardAppRule<SportChefConfiguration> RULE = new DropwizardAppRule<>(App.class, "config.yaml");

    @Test
    public void returnJohnDoe() {
        Client client = new Client();

        ClientResponse response = client.resource(
                String.format("http://localhost:%d/api/user/1", RULE.getLocalPort()))
                .get(ClientResponse.class);

        assertThat(response.getStatus()).isEqualTo(200);

        final User user = response.getEntity(User.class);
        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getFirstName()).isEqualTo("John");
        assertThat(user.getLastName()).isEqualTo("Doe");
        assertThat(user.getPhone()).isEqualTo("+41 79 123 45 67");
        assertThat(user.getEmail()).isEqualTo("john.doe@sportchef.ch");
    }

    @Test
    public void returnJaneDoe() {
        Client client = new Client();

        ClientResponse response = client.resource(
                String.format("http://localhost:%d/api/user/2", RULE.getLocalPort()))
                .get(ClientResponse.class);

        assertThat(response.getStatus()).isEqualTo(200);

        final User user = response.getEntity(User.class);
        assertThat(user.getId()).isEqualTo(2L);
        assertThat(user.getFirstName()).isEqualTo("Jane");
        assertThat(user.getLastName()).isEqualTo("Doe");
        assertThat(user.getPhone()).isEqualTo("+41 79 234 56 78");
        assertThat(user.getEmail()).isEqualTo("jane.doe@sportchef.ch");
    }

    @Test
    public void returnNotFound() {
        Client client = new Client();

        ClientResponse response = client.resource(
                String.format("http://localhost:%d/api/user/3", RULE.getLocalPort()))
                .get(ClientResponse.class);

        assertThat(response.getStatus()).isEqualTo(404);
    }
}
