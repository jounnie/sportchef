package ch.sportchef.server.resources;

import ch.sportchef.server.representations.User;
import ch.sportchef.server.services.ServiceRegistry;
import ch.sportchef.server.services.TokenService;
import io.dropwizard.auth.Auth;
import io.dropwizard.auth.AuthenticationException;

import javax.management.ServiceNotFoundException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.Map;

@Path("token")
public class TokenResource {

    private TokenService tokenService;

    public TokenResource() throws ServiceNotFoundException {
        super();
        this.tokenService = ServiceRegistry.getService(TokenService.class);
    }

    @GET
    @Path("generate")
    public Map<String, String> generate(final User user) throws AuthenticationException {
        return tokenService.generateToken(user);
    }

    @GET
    @Path("/check")
    public Response user(@Auth final User user) {
        return Response.ok().build();
    }
}
