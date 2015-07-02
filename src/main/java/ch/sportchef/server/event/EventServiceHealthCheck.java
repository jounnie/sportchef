package ch.sportchef.server.event;

import ch.sportchef.server.SportChefConfiguration;
import ch.sportchef.server.utils.ServiceRegistry;
import com.codahale.metrics.health.HealthCheck;

import javax.management.ServiceNotFoundException;
import javax.ws.rs.NotFoundException;

public class EventServiceHealthCheck extends HealthCheck {

    private final EventService eventService;
    private final Event referenceEvent;

    public EventServiceHealthCheck(final SportChefConfiguration configuration) throws ServiceNotFoundException {
        this.eventService = ServiceRegistry.getService(EventService.class);
        this.referenceEvent = configuration.getHealthCheckConfiguration()
                .getEventServiceConfiguration().getReferenceEvent();
    }

    @Override
    protected Result check() {
        final long eventId = referenceEvent.getEventId();

        try {
            final Event checkEvent = eventService.readEventById(eventId);

            if (checkEvent.equals(referenceEvent)) {
                return Result.healthy("EventService is fine.");
            }
            return Result.unhealthy("EventService could not find the correct event with id '%d'!", eventId);
        } catch (final NotFoundException e) {
            return Result.unhealthy("EventService could not find any event with id '%d'!", eventId);
        }
    }
}
