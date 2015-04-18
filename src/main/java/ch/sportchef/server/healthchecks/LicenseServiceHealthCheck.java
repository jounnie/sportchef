package ch.sportchef.server.healthchecks;

import ch.sportchef.server.App;
import ch.sportchef.server.representations.License;
import ch.sportchef.server.services.LicenseService;
import com.codahale.metrics.health.HealthCheck;

public class LicenseServiceHealthCheck extends HealthCheck {

    private final LicenseService licenseService;

    public LicenseServiceHealthCheck() {
        this.licenseService = App.getService(LicenseService.class);
    }

    @Override
    protected Result check() throws Exception {
        final License license = licenseService.readLicense();

        if (license != null) {
            return Result.healthy("LicenseService is fine.");
        }

        return Result.unhealthy("LicenseService has problems returning the correct license!");
    }
}
