package ch.sportchef.server.utils;

import ch.sportchef.server.representations.User;
import ch.sportchef.server.services.ServiceRegistry;
import ch.sportchef.server.services.UserService;
import com.github.toastshaman.dropwizard.auth.jwt.JsonWebTokenValidator;
import com.github.toastshaman.dropwizard.auth.jwt.exceptions.TokenExpiredException;
import com.github.toastshaman.dropwizard.auth.jwt.hmac.HmacSHA512Signer;
import com.github.toastshaman.dropwizard.auth.jwt.model.JsonWebToken;
import com.github.toastshaman.dropwizard.auth.jwt.model.JsonWebTokenClaim;
import com.github.toastshaman.dropwizard.auth.jwt.model.JsonWebTokenHeader;
import com.google.common.base.Optional;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import org.joda.time.DateTime;

import javax.management.ServiceNotFoundException;
import java.util.Collections;
import java.util.Map;

public class SportChefAuthenticator implements Authenticator<JsonWebToken, User> {

    private final UserService userService;
    private final JsonWebTokenValidator jsonWebTokenValidator;
    private byte[] tokenSecret;

    public SportChefAuthenticator(final JsonWebTokenValidator jsonWebTokenValidator,
                                  final byte[] tokenSecret) throws ServiceNotFoundException {
        this.tokenSecret = tokenSecret;
        this.jsonWebTokenValidator = jsonWebTokenValidator;
        this.userService = ServiceRegistry.getService(UserService.class);
    }

    public Map<String, String> generateNewToken(final User user) throws AuthenticationException {
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

        return Collections.singletonMap("token", signedToken);
    }

    @Override
    public Optional<User> authenticate(final JsonWebToken token) throws AuthenticationException {
        validate(token);
        final long userId = Long.parseLong(token.claim().getParameter("userId").toString());
        final User user = userService.readUserById(userId);
        return Optional.fromNullable(user);
    }

    private void validate(final JsonWebToken token) throws AuthenticationException {
        try {
            jsonWebTokenValidator.validate(token);
        } catch (final TokenExpiredException e) {
            throw new AuthenticationException(e);
        }
    }
}