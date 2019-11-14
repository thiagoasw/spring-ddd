package io.tasw.sk.ddd;

import static java.util.Objects.requireNonNull;

import java.util.Objects;
import java.util.UUID;

import org.springframework.lang.NonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Base class for value objects that are used as identifiers for {@link IdentifiableDomainObject}s. These are
 * essentially UUID-wrappers.
 */
public abstract class DomainObjectId implements ValueObject {

    private static final long serialVersionUID = 6493750573405978912L;

    private final String uuid;

    @JsonCreator
    protected DomainObjectId(@NonNull String uuid) {
        this.uuid = requireNonNull(uuid, "uuid must not be null");
    }

    /**
     * Creates a new, random instance of the given {@code idClass}.
     */
    @NonNull
    public static <ID extends DomainObjectId> ID randomId(@NonNull Class<ID> idClass) {
        requireNonNull(idClass, "idClass must not be null");
        try {
            return idClass.getConstructor(String.class).newInstance(UUID.randomUUID().toString());
        } catch (Exception ex) {
            throw new RuntimeException("Could not create new instance of " + idClass, ex);
        }
    }

    /**
     * Returns the ID as a UUID string.
     */
    @JsonValue
    @NonNull
    public String toUUID() {
        return uuid;
    }

    @Override
    public boolean equals(Object obj) {
        
        if (this == obj)
            return true;
        
        if (obj == null || !getClass().equals(obj.getClass()))
            return false;
        
        return Objects.equals(uuid, ((DomainObjectId) obj).uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }

    @Override
    public String toString() {
        return String.format("%s[%s]", getClass().getSimpleName(), uuid);
    }
}
