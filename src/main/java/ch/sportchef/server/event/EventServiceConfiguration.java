package ch.sportchef.server.event;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class EventServiceConfiguration {

    @Valid
    @NotNull
    @JsonProperty("referenceEvent")
    private Event referenceEvent = null;

    public Event getReferenceEvent() {
        return referenceEvent;
    }
}
