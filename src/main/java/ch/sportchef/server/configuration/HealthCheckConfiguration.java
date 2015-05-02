package ch.sportchef.server.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class HealthCheckConfiguration {

    @Valid
    @NotNull
    @JsonProperty("userService")
    private UserServiceConfiguration userService = new UserServiceConfiguration();

    public UserServiceConfiguration getUserServiceConfiguration() {
        return userService;
    }
}
