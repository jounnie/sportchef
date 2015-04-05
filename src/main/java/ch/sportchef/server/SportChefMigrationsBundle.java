package ch.sportchef.server;

import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.migrations.MigrationsBundle;

public class SportChefMigrationsBundle extends MigrationsBundle<SportChefConfiguration> {

    @Override
    public DataSourceFactory getDataSourceFactory(SportChefConfiguration configuration) {
        return configuration.getDataSourceFactory();
    }
}
