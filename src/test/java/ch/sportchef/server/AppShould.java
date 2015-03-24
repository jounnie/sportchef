package ch.sportchef.server;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Matchers.anyVararg;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
}
