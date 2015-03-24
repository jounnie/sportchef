package ch.sportchef.server;

import ch.sportchef.server.healthchecks.UserServiceHealthCheck;
import ch.sportchef.server.resources.UserResource;
import ch.sportchef.server.services.UserService;
import com.codahale.metrics.health.HealthCheckRegistry;
import io.dropwizard.Bundle;
import io.dropwizard.jersey.setup.JerseyEnvironment;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyVararg;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest(App.class)
public class AppShould {

    @Test
    public void startWithArguments() throws Exception {
        final App appMock = mock(App.class);
        whenNew(App.class).withNoArguments().thenReturn(appMock);
        App.main(new String[]{"server", "config.yaml"});
        verify(appMock, times(1)).run(anyVararg());
    }

    @Test
    public void initializeProperly() {
        final Bootstrap<SportChefConfiguration> bootstrap = mock(Bootstrap.class);

        new App().initialize(bootstrap);

        verify(bootstrap, times(1)).addBundle(any(Bundle.class));
    }

    @Test
    public void runProperly() throws Exception {
        final SportChefConfiguration configuration = mock(SportChefConfiguration.class);
        final Environment environment = mock (Environment.class);
        final HealthCheckRegistry healthCheckRegistry = mock(HealthCheckRegistry.class);
        final JerseyEnvironment jerseyEnvironment = mock(JerseyEnvironment.class);

        when(environment.healthChecks()).thenReturn(healthCheckRegistry);
        when(environment.jersey()).thenReturn(jerseyEnvironment);

        new App().run(configuration, environment);

        assertThat(App.getService(UserService.class)).isNotNull();
        verify(healthCheckRegistry, times(1)).register(eq("userService"), any(UserServiceHealthCheck.class));
        verify(jerseyEnvironment, times(1)).register(any(UserResource.class));
    }
}
