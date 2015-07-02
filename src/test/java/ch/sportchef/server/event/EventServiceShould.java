package ch.sportchef.server.event;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class EventServiceShould {

    private EventDAO eventDAO;
    private EventService eventService;

    @Before
    public void setUp() {
        final Event footballWm = EventGenerator.getFootballWm(1L);
        final Event tennisWm = EventGenerator.getTennisWm(2L);
        final Event icehockeyWm = EventGenerator.getIcehokeyWm(3L);

        eventDAO = mock(EventDAO.class);
        when(eventDAO.readAllEvents()).thenReturn(new ArrayList<Event>() {{
            add(footballWm);
            add(tennisWm);
            add(icehockeyWm);
        }});
        when(eventDAO.readById(1L)).thenReturn(footballWm);

        // initialize service
        eventService = new EventService(eventDAO);
    }

    @After
    public void tearDown() throws Exception {
        eventService = null;
        eventDAO = null;
    }

    @Test
    public void returnAllEvents() {
        final List<Event> events = eventService.readAllEvents();
        assertThat(events.size()).isEqualTo(3);
        assertThat(events.get(0)).isEqualTo(EventGenerator.getFootballWm(1L));
        assertThat(events.get(1)).isEqualTo(EventGenerator.getTennisWm(2L));
        assertThat(events.get(2)).isEqualTo(EventGenerator.getIcehokeyWm(3L));
    }

    @Test
    public void returnFootballWm() {
        final Event event = eventService.readEventById(1L);
        assertThat(event).isEqualTo(EventGenerator.getFootballWm(1L));
    }

    @Test(expected = NotFoundException.class)
    public void returnNoEvent() {
        eventService.readEventById(4L);
    }

    @Test
    public void removeTennisWm() {
        final Event tennisWm = EventGenerator.getTennisWm(2L);
        eventService.removeEvent(tennisWm);
        verify(eventDAO, times(1)).delete(tennisWm);
    }
}
