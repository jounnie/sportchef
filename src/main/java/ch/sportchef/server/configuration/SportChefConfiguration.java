package ch.sportchef.server.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class SportChefConfiguration extends Configuration {

    @Valid
    @NotNull
    @JsonProperty("database")
    private SportChefDataSourceFactory database = new SportChefDataSourceFactory();

    public SportChefDataSourceFactory getDataSourceFactory() {
        return database;
    }
}
