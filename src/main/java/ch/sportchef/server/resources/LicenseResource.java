package ch.sportchef.server.resources;

import ch.sportchef.server.App;
import ch.sportchef.server.representations.License;
import ch.sportchef.server.services.LicenseService;
import com.codahale.metrics.annotation.Timed;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("/license")
public class LicenseResource {

    private final LicenseService licenseService;

    public LicenseResource() {
        this.licenseService = App.getService(LicenseService.class);
    }

    @GET
    @Timed
    public Response readLicense() throws IOException {
        final License license = licenseService.readLicense();
        if (license == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(license).build();
    }

}
