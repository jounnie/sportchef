package ch.sportchef.server.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDate;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.assertj.core.api.Assertions.assertThat;

public class EventShould {

    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

    @Test
    public void serializeToJSON() throws IOException {
        final Event event = new Event(1L, "WM", "Japan", LocalDate.of(2015, 6, 4));
        assertThat(MAPPER.writeValueAsString(event)).isEqualTo(fixture("fixtures/event.json"));
    }

    @Test
    public void deserializeFromJSON() throws IOException {
        final Event event = MAPPER.readValue(fixture("fixtures/event.json"), Event.class);
        assertThat(event).isNotNull();
        assertThat(event.getEventId()).isEqualTo(1L);
        assertThat(event.getTitle()).isEqualTo("WM");
        assertThat(event.getLocation()).isEqualTo("Japan");
        assertThat(event.getDate()).isEqualTo(LocalDate.of(2015, 6, 4));
    }

    @Test
    public void haveUsefulToStringImplementation() {
        final Event event = new Event(1L, "WM", "Japan", LocalDate.of(2015, 6, 4));
        final String toString = event.toString();
        assertThat(toString).contains("ch.sportchef.server.event.Event");
        assertThat(toString).contains("eventId=1");
        assertThat(toString).contains("title=WM");
        assertThat(toString).contains("location=Japan");
        assertThat(toString).contains("date=2015-06-04");
    }
}
