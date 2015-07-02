package ch.sportchef.server.event;

import ch.sportchef.server.utils.Service;

import javax.ws.rs.NotFoundException;
import java.util.List;

public class EventService implements Service {

    private final EventDAO eventDAO;

    public EventService(EventDAO eventDAO) {
        this.eventDAO = eventDAO;
    }

    public List<Event> readAllEvents() {
        return eventDAO.readAllEvents();
    }

    public Event readEventById(long eventId) {
        Event event = eventDAO.readById(eventId);
        if (event == null) {
            throw new NotFoundException(
                    String.format("event con la eventId '%d'", eventId));
        }
        return event;
    }

    public Event storeEvent(Event event) {
        if (event.getEventId() == 0) {
            long eventId = eventDAO.create(event);
            return new Event(eventId, event.getTitle(), event.getLocation(), event.getDate());
        } else {
            readEventById(event.getEventId());
            eventDAO.update(event);
            return event;
        }
    }

    public void removeEvent(final Event event) {
        eventDAO.delete(event);
    }
}
