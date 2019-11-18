package io.tasw.sk.domain;

import java.time.Instant;

import org.springframework.lang.NonNull;

/**
 * Interface for domain events.
 */
public interface DomainEvent extends DomainObject {

    /**
     * Returns the time and date on which the event occurred.
     */
    @NonNull
    Instant getOccurredOn();

}
