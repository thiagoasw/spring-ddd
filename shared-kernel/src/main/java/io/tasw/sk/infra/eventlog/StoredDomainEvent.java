package io.tasw.sk.infra.eventlog;

import static java.util.Objects.requireNonNull;
import static javax.persistence.GenerationType.SEQUENCE;
import static lombok.AccessLevel.PRIVATE;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.tasw.sk.domain.DomainEvent;
import io.tasw.sk.infra.jackson.RawJsonDeserializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Database entity for storing {@link DomainEvent}s as JSON in a relational database.
 */
@Getter
@Builder
@ToString
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(access = PRIVATE, force = true)

@Entity
public class StoredDomainEvent {

    @Id
    @GeneratedValue(strategy = SEQUENCE)
    @Column(nullable = false)
    private final Long id;

    @Column(nullable = false)
    private final String type;

    @Column(nullable = false)
    private final Instant occurredOn;

    @JsonRawValue
    @JsonDeserialize(using = RawJsonDeserializer.class)
    @Column(nullable = false)
    private final String event;

    /**
     * Creates a new {@code StoredDomainEvent}.
     *
     * @param domainEvent the domain event to store.
     * @param mapper the object mapper to use to convert the domain event into JSON.
     * @throws IllegalArgumentException if the domain event cannot be converted to JSON.
     */
    public static StoredDomainEvent map(DomainEvent domainEvent, ObjectMapper mapper) {

        requireNonNull(domainEvent, "domainEvent must not be null");
        requireNonNull(mapper, "objectMapper must not be null");

        StoredDomainEventBuilder builder = builder();

        builder.occurredOn(domainEvent.occurredOn());
        builder.type(domainEvent.getClass().getSimpleName());

        try {
            builder.event(mapper.writeValueAsString(domainEvent));
        } catch (JsonProcessingException ex) {
            throw new IllegalArgumentException("Could not serialize domain event to JSON", ex);
        }

        return builder.build();
    }

}
