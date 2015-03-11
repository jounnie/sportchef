package ch.sportchef.server.healthcheck;

import ch.sportchef.server.App;
import ch.sportchef.server.SportChefConfiguration;
import ch.sportchef.server.representations.User;
import ch.sportchef.server.services.UserService;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.apache.commons.io.IOUtils;
import org.junit.ClassRule;
import org.junit.Test;

import java.io.IOException;

import static org.fest.assertions.api.Assertions.assertThat;

public class UserServiceHealthCheckShould {

    @ClassRule
    public static final DropwizardAppRule<SportChefConfiguration> RULE = new DropwizardAppRule<>(App.class, "config.yaml");

    @Test
    public void returnHealthy() throws IOException {
        final Client client = new Client();

        final ClientResponse response = client.resource(
                String.format("http://localhost:%d/healthcheck", RULE.getAdminPort()))
                .get(ClientResponse.class);

        assertThat(response.getStatus()).isEqualTo(ClientResponse.Status.OK.getStatusCode());

        final String body = IOUtils.toString(response.getEntityInputStream());
        assertThat(body).contains("UserService is fine.");
    }

    @Test
    public void returnUnhealthy() throws IOException {
        final UserService userService = App.getService(UserService.class);
        final User user = userService.readUserById(1L).get();
        userService.removeUser(user);

        final Client client = new Client();

        final ClientResponse response = client.resource(
                String.format("http://localhost:%d/healthcheck", RULE.getAdminPort()))
                .get(ClientResponse.class);

        assertThat(response.getStatus()).isEqualTo(ClientResponse.Status.INTERNAL_SERVER_ERROR.getStatusCode());

        final String body = IOUtils.toString(response.getEntityInputStream());
        assertThat(body).contains("UserService has problems returning the correct reference user!");
    }
}
