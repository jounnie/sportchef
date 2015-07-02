package ch.sportchef.server.event;

import ch.sportchef.server.SportChefApp;
import ch.sportchef.server.SportChefConfiguration;
import ch.sportchef.server.utils.ServiceRegistry;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.assertj.core.api.Assertions.assertThat;


public class EventResourceShould {

    @ClassRule
    public static final DropwizardAppRule<SportChefConfiguration> RULE = new DropwizardAppRule<>(SportChefApp.class, "config-test.yaml");

    @BeforeClass
    public static void setUp() throws Exception {
        final EventService eventService = ServiceRegistry.getService(EventService.class);
    }

    @Test
    public void returnAllEvents() {
        final WebTarget target = ClientBuilder.newClient().target(
                String.format("http://localhost:%d/api/events", RULE.getLocalPort()));

        final Response response = target
                .request(MediaType.APPLICATION_JSON_TYPE)
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .get();

        assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());

    }
}
