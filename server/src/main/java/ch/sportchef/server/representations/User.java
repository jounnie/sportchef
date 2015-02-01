package ch.sportchef.server.representations;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class User {

    private final long id;

    @NotBlank
    @Length(min=1, max=100)
    private final String firstname;

    @NotBlank
    @Length(min=1, max=100)
    private final String lastname;

    @NotBlank
    @Email
    @Length(min=1, max=100)
    private final String email;

    public User(final long id, @Nonnull final String firstname, @Nonnull final String lastname, @Nonnull final String email) {
        super();
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public @Nullable
    String getFirstname() {
        return firstname;
    }

    public @Nullable String getLastname() {
        return lastname;
    }

    public @Nullable String getEmail() {
        return email;
    }
}
