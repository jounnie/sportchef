package ch.sportchef.server.event;

import ch.sportchef.server.SportChefApp;
import ch.sportchef.server.SportChefConfiguration;
import ch.sportchef.server.utils.ServiceRegistry;
import io.dropwizard.testing.junit.DropwizardAppRule;
import liquibase.exception.LiquibaseException;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import javax.management.ServiceNotFoundException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class EventServiceHealthCheckShould {

    private EventService eventService;

    @ClassRule
    public static final DropwizardAppRule<SportChefConfiguration> RULE = new DropwizardAppRule<>(SportChefApp.class, "config-test.yaml");

    @Before
    public void setup() throws SQLException, LiquibaseException, ServiceNotFoundException {
        eventService = ServiceRegistry.getService(EventService.class);
        try {
            eventService.readEventById(1L);
        } catch (final NotFoundException e) {
            eventService.storeEvent(EventGenerator.getFootballWm(0L));
        }
    }

    @Test
    public void returnHealthy() throws IOException {
        eventService.storeEvent(EventGenerator.getFootballWm(1L));

        final WebTarget target = ClientBuilder.newClient().target(
                String.format("http://localhost:%d/healthcheck", RULE.getAdminPort()));

        final Response response = target
                .request(MediaType.APPLICATION_JSON_TYPE)
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .get();

        assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());

        final String body = response.readEntity(String.class);
        assertThat(body).contains("EventService is fine.");
    }

    @Test
    public void returnNotCorrect() throws IOException, ServiceNotFoundException {
        eventService.storeEvent(EventGenerator.getTennisWm(1L));

        final WebTarget target = ClientBuilder.newClient().target(
                String.format("http://localhost:%d/healthcheck", RULE.getAdminPort()));

        final Response response = target
                .request(MediaType.APPLICATION_JSON_TYPE)
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .get();

        assertThat(response.getStatus()).isEqualTo(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());

        final String body = response.readEntity(String.class);
        assertThat(body).contains("EventService could not find the correct user with id '1'!");
    }

    @Test
    public void returnNotFound() throws IOException, ServiceNotFoundException {
        final Event event = eventService.readEventById(1L);
        eventService.removeEvent(event);

        final WebTarget target = ClientBuilder.newClient().target(
                String.format("http://localhost:%d/healthcheck", RULE.getAdminPort()));

        final Response response = target
                .request(MediaType.APPLICATION_JSON_TYPE)
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .get();

        assertThat(response.getStatus()).isEqualTo(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());

        final String body = response.readEntity(String.class);
        assertThat(body).contains("EventService could not find any event with id '1'!");
    }
}
