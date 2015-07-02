package ch.sportchef.server.event;

import ch.sportchef.server.utils.ServiceRegistry;
import com.codahale.metrics.annotation.Timed;

import javax.management.ServiceNotFoundException;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Path("events")
public class EventResource {

    private final EventService eventService;

    public EventResource() throws ServiceNotFoundException {
        this.eventService = ServiceRegistry.getService(EventService.class);
    }

    @GET
    @Timed
    public Response readAllEvents() {
        final List<Event> events = eventService.readAllEvents();
        return Response.ok(new GenericEntity<List<Event>>(events) {
        }).build();
    }

    @GET
    @Path("{eventId}")
    @Timed
    public Response readUser(@PathParam("eventId") final long id) {
        final Event event = eventService.readEventById(id);
        return Response.ok(event).build();
    }

    @POST
    @Timed
    public Response createEvent(@Valid Event event) throws URISyntaxException {
        final Event newEvent = eventService.storeEvent(event);
        return Response.created(new URI(String.valueOf(newEvent.getEventId()))).build();
    }

    @PUT
    @Path("{eventId}")
    @Timed
    public Response updateEvent(@PathParam("eventId") final long eventId, @Valid Event event) throws URISyntaxException {
        if (eventId != event.getEventId()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        final Event updatedEvent = eventService.storeEvent(event);
        return Response.ok(new URI(String.valueOf(updatedEvent.getEventId()))).build();
    }
}
