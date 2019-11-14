package io.tasw.sk.ddd;

import static java.util.Objects.requireNonNull;
import static lombok.AccessLevel.PROTECTED;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.springframework.data.util.ProxyUtils;
import org.springframework.lang.NonNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.NoArgsConstructor;

/**
 * Base class for entities.
 *
 * @param <ID> the entity ID type.
 */
@MappedSuperclass
@NoArgsConstructor(access = PROTECTED)
public abstract class AbstractEntity<ID extends DomainObjectId> implements IdentifiableDomainObject<ID> {

    private static final long serialVersionUID = -3123692781951371785L;

    @Id
    @JsonProperty("id")
    private ID id;

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
    public boolean equals(Object obj) {

        if (obj == this)
            return true;

        if (obj == null || !getClass().equals(ProxyUtils.getUserClass(obj)))
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
        return String.format("%s[%s]", getClass().getSimpleName(), id);
    }
    
}