package ch.sportchef.server.utils;

import ch.sportchef.server.SportChefConfiguration;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.testing.junit.DropwizardAppRule;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.exception.LockException;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.sql.SQLException;
import java.util.Properties;

public class LiquibaseUtil {
    private static Liquibase liquibase;

    public static void migrate(final DropwizardAppRule<SportChefConfiguration> rule) throws SQLException, LiquibaseException {
        final DataSourceFactory dataSourceFactory = rule.getConfiguration().getDataSourceFactory();

        final Properties info = new Properties();
        info.setProperty("user", dataSourceFactory.getUser());
        info.setProperty("password", dataSourceFactory.getPassword());

        final org.h2.jdbc.JdbcConnection h2Conn = new org.h2.jdbc.JdbcConnection(dataSourceFactory.getUrl(), info);
        final JdbcConnection conn = new JdbcConnection(h2Conn);

        final Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(conn);
        liquibase = new Liquibase("migrations.xml", new ClassLoaderResourceAccessor(), database);

        final String ctx = null;
        liquibase.update(ctx);
    }
    public static void dropAll() throws DatabaseException, LockException {
        if (liquibase != null) {
            liquibase.dropAll();
            liquibase = null;
        }
    }
}
