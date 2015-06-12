package ch.sportchef.server.utils;

import ch.sportchef.server.SportChefConfiguration;
import ch.sportchef.server.SportChefDataSourceFactory;
import io.dropwizard.db.ManagedDataSource;
import io.dropwizard.setup.Environment;
import liquibase.Liquibase;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.Verifications;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;

import static org.mockito.Matchers.anyString;

public class LiquibaseUtilShould {

    @Injectable
    private Connection connection;

    @Injectable
    private ManagedDataSource managedDataSource;

    @Injectable
    private SportChefDataSourceFactory dataSourceFactory;

    @Injectable
    private SportChefConfiguration configuration;

    @Injectable
    private Environment environment;

    @Mocked
    private Liquibase liquibase;

    @Test
    public void beWellDefined() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        UtilityClassVerifier.assertUtilityClassWellDefined(LiquibaseUtil.class);
    }

    @Test
    public void migrateTheDatabase() throws Exception {

        new Expectations() {{
            managedDataSource.getConnection(); result = connection;
            configuration.getDataSourceFactory(); result = dataSourceFactory;
        }};

        LiquibaseUtil.migrate(configuration, environment);

        new Verifications() {{
            liquibase.update(anyString()); times = 1;
        }};
    }
}
