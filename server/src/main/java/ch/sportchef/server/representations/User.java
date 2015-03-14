package ch.sportchef.server.representations;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class User {

    private final long id;

    @NotBlank
    @Length(min=1, max=100)
    private final String firstName;

    @NotBlank
    @Length(min=1, max=100)
    private final String lastName;

    @NotBlank
    @Length(min=1, max=30)
    private final String phone;

    @NotBlank
    @Email
    @Length(min=1, max=100)
    private final String email;

    private User() {
        // Jackson deserialization
        this(0, null, null, null, null);
    }

    public User(final long id, @Nonnull final String firstName, @Nonnull final String lastName, @Nonnull final String phone, @Nonnull final String email) {
        super();
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public long getId() {
        return id;
    }

    public @Nullable
    String getFirstName() {
        return firstName;
    }

    public @Nullable String getLastName() {
        return lastName;
    }

    public @Nullable String getPhone() {
        return phone;
    }

    public @Nullable String getEmail() {
        return email;
    }
}
