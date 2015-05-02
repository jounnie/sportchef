package ch.sportchef.server.configuration;

import ch.sportchef.server.representations.User;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class UserServiceConfiguration {

    @Valid
    @NotNull
    @JsonProperty("referenceUser")
    private User referenceUser = null;

    public User getReferenceUser() {
        return referenceUser;
    }
}
