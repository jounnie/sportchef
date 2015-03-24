package ch.sportchef.server;

import ch.sportchef.server.healthchecks.UserServiceHealthCheck;
import ch.sportchef.server.resources.UserResource;
import ch.sportchef.server.services.Service;
import ch.sportchef.server.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public class App extends Application<SportChefConfiguration> {

    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    private static final Map<Integer, Service> services = new HashMap<>();

    public static void main(@Nonnull final String[] args) throws Exception {
        LOGGER.info("Starting application with arguments: %s", new Object[]{args});
        new App().run(args);
    }

    public static <T extends Service> T getService(Class<T> serviceClass) {
        return serviceClass.cast(services.get(serviceClass.hashCode()));
    }

    @Override
    public void initialize(@Nonnull final Bootstrap<SportChefConfiguration> bootstrap) {
        // Configure Assets
        bootstrap.addBundle(new AssetsBundle("/assets/", "/", "html/index.html"));

        // Register additional Jackson modules
        final ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JSR310Module());
    }

    @Override
    public void run(@Nonnull final SportChefConfiguration configuration, @Nonnull final Environment environment) throws Exception {
        // Initialize services
        services.put(UserService.class.hashCode(), new UserService());

        // Initialize health checks
        environment.healthChecks().register("userService", new UserServiceHealthCheck());

        // Initialize resources
        environment.jersey().register(new UserResource());
    }
}
