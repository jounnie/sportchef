package ch.sportchef.server;

import ch.sportchef.server.event.EventServiceConfiguration;
import ch.sportchef.server.user.UserServiceConfiguration;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class SportChefHealthCheckConfiguration {

    @Valid
    @NotNull
    @JsonProperty("userService")
    private UserServiceConfiguration userService = new UserServiceConfiguration();

    @Valid
    @NotNull
    @JsonProperty("eventService")
    private EventServiceConfiguration eventService = new EventServiceConfiguration();

    public UserServiceConfiguration getUserServiceConfiguration() {
        return userService;
    }

    public EventServiceConfiguration getEventServiceConfiguration() {
        return eventService;
    }
}
