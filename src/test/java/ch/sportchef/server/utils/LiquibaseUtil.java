package ch.sportchef.server.utils;

import ch.sportchef.server.SportChefConfiguration;
import io.dropwizard.db.ManagedDataSource;
import io.dropwizard.testing.junit.DropwizardAppRule;
import liquibase.Liquibase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.sql.Connection;
import java.sql.SQLException;

public class LiquibaseUtil {
    public static void migrate(final DropwizardAppRule<SportChefConfiguration> rule) throws LiquibaseException, SQLException {
        final ManagedDataSource ds = rule.getConfiguration().getDataSourceFactory().build(
                rule.getEnvironment().metrics(), "migrations");
        final Connection connection = ds.getConnection();
        final Liquibase liquibase = new Liquibase("migrations.xml", new ClassLoaderResourceAccessor(), new JdbcConnection(connection));
        liquibase.dropAll();
        liquibase.update("");
    }
}
