package io.tasw.sk.domain;

import static java.util.Collections.unmodifiableList;
import static java.util.Objects.requireNonNull;
import static lombok.AccessLevel.PROTECTED;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.springframework.data.domain.AfterDomainEventPublication;
import org.springframework.data.domain.DomainEvents;
import org.springframework.lang.NonNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.NoArgsConstructor;

/**
 * Base class for aggregate roots.
 *
 * @param <ID> the aggregate root ID type.
 */
@MappedSuperclass
@NoArgsConstructor(access = PROTECTED)
public abstract class AbstractAggregateRoot<ID extends DomainObjectId> extends AbstractEntity<ID> {

    @Transient
    @JsonIgnore
    private List<DomainEvent> domainEvents = new ArrayList<>();

    /**
     * Copy constructor. Please note that any registered domain events are <b>not</b> copied.
     *
     * @param source the aggregate root to copy from.
     */
    protected AbstractAggregateRoot(@NonNull AbstractAggregateRoot<ID> source) {
        super(source);
    }

    /**
     * Constructor for creating new aggregate roots.
     *
     * @param id the ID to assign to the aggregate root.
     */
    protected AbstractAggregateRoot(ID id) {
        super(id);
    }

    /**
     * Registers the given domain event to be published when the aggregate root is persisted.
     *
     * @param event the event to register.
     */
    @NonNull
    protected void registerEvent(@NonNull DomainEvent event) {
        requireNonNull(event, "event must not be null");
        this.domainEvents.add(event);
    }

    /**
     * Called by the persistence framework to clear all registered domain events once they have been published.
     */
    @AfterDomainEventPublication
    protected void clearDomainEvents() {
        this.domainEvents.clear();
    }

    /**
     * Returns all domain events that have been registered for publication. Intended to be used by the persistence
     * framework only.
     */
    @DomainEvents
    protected Collection<Object> domainEvents() {
        return unmodifiableList(domainEvents);
    }
    
}
