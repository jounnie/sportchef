package ch.sportchef.server.services;

import ch.sportchef.server.representations.License;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.assertj.core.api.Assertions.assertThat;

public class LicenseServiceShould {

    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

    private License agpl = null;
    private LicenseService licenseService;

    @Before
    public void setUp() throws IOException {
        agpl = MAPPER.readValue(fixture("fixtures/license.json"), License.class);
        licenseService = new LicenseService();
    }

    @After
    public void tearDown() {
        licenseService = null;
        agpl = null;
    }

    @Test
    public void returnLicense() throws IOException {
        final License license = licenseService.readLicense();
        assertThat(license).isEqualTo(agpl);
    }
}
