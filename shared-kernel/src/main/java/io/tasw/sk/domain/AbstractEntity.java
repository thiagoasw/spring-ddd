package io.tasw.sk.domain;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;
import static lombok.AccessLevel.PROTECTED;
import static org.springframework.data.util.ProxyUtils.getUserClass;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.NoArgsConstructor;

/**
 * Base class for entities.
 *
 * @param <ID> the entity ID type.
 */
@MappedSuperclass
@NoArgsConstructor(access = PROTECTED)
public abstract class AbstractEntity<ID extends DomainObjectId> implements IdentifiableDomainObject<ID>, ConcurrentDomainObject {

    private static final long serialVersionUID = -3123692781951371785L;

    @Id
    @JsonProperty
    private ID id;

    @Version
    @Nullable
    @JsonProperty
    private Long version;

    /**
     * Copy constructor
     *
     * @param source the entity to copy from.
     */
    protected AbstractEntity(@NonNull AbstractEntity<ID> source) {
        requireNonNull(source, "source must not be null");
        this.id = source.id;
    }

    /**
     * Constructor for creating new entities.
     *
     * @param id the ID to assign to the entity.
     */
    protected AbstractEntity(@NonNull ID id) {
        this.id = requireNonNull(id, "id must not be null");
    }

    @Override
    @NonNull
    public ID id() {
        return id;
    }

    @Override
    @NonNull
    public Long version() {
        return version;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == this)
            return true;

        if (obj == null || !getClass().equals(getUserClass(obj)))
            return false;

        var other = (AbstractEntity<?>) obj;

        return id != null && id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return id == null ? super.hashCode() : id.hashCode();
    }

    @Override
    public String toString() {
        return format("%s[%s]", getClass().getSimpleName(), id);
    }

}