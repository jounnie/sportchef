package ch.sportchef.server;

import io.dropwizard.Bundle;
import io.dropwizard.setup.Bootstrap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyVararg;
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
}
