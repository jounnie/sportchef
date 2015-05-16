package ch.sportchef.server.services;

import ch.sportchef.server.representations.User;
import com.github.toastshaman.dropwizard.auth.jwt.hmac.HmacSHA512Signer;
import com.github.toastshaman.dropwizard.auth.jwt.model.JsonWebToken;
import com.github.toastshaman.dropwizard.auth.jwt.model.JsonWebTokenClaim;
import com.github.toastshaman.dropwizard.auth.jwt.model.JsonWebTokenHeader;
import io.dropwizard.auth.AuthenticationException;
import org.joda.time.DateTime;

import javax.management.ServiceNotFoundException;
import java.util.Map;

import static java.util.Collections.singletonMap;

public class TokenService implements Service {

    private final byte[] tokenSecret;
    private final UserService userService;

    public TokenService(final byte[] tokenSecret) throws ServiceNotFoundException {
        super();
        this.tokenSecret = tokenSecret;
        this.userService = ServiceRegistry.getService(UserService.class);
    }

    public Map<String, String> generateToken(final User user) throws AuthenticationException {
        if (!user.equals(userService.readUserById(user.getUserId()))) {
            throw new AuthenticationException(
                    String.format("Invalid user '%s'", user));
        }

        final DateTime now = DateTime.now();
        final HmacSHA512Signer signer = new HmacSHA512Signer(tokenSecret);
        final JsonWebToken token = JsonWebToken.builder()
                .header(JsonWebTokenHeader.HS512())
                .claim(JsonWebTokenClaim.builder()
                        .param("userId", user.getUserId())
                        .issuedAt(now)
                        .expiration(now.plusHours(1))
                        .build())
                .build();
        final String signedToken = signer.sign(token);

        return singletonMap("token", signedToken);
    }
}
