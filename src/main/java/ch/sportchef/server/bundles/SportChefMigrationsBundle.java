package ch.sportchef.server.bundles;

import ch.sportchef.server.configuration.SportChefConfiguration;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.migrations.MigrationsBundle;

public class SportChefMigrationsBundle extends MigrationsBundle<SportChefConfiguration> {

    @Override
    public DataSourceFactory getDataSourceFactory(SportChefConfiguration configuration) {
        return configuration.getDataSourceFactory();
    }
}
