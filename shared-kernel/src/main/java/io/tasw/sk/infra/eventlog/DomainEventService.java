package io.tasw.sk.infra.eventlog;

import static io.tasw.sk.infra.eventlog.StoredDomainEvent.map;
import static org.springframework.transaction.annotation.Propagation.MANDATORY;

import java.util.List;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.tasw.sk.domain.DomainEvent;
import lombok.AllArgsConstructor;

/**
 * The domain event service is responsible for storing {@link DomainEvent}s. These can be used for
 * auditing or for integration with other systems / bounded contexts.
 *
 * @see StoredDomainEvent
 * @see DomainEvent
 * @see DomainEventAppender
 */
@AllArgsConstructor

@Service
public class DomainEventService {

    private final ObjectMapper mapper;
    
    private final StoredDomainEventRepository repository;

    /**
     * Appends the given domain event to the event log.
     *
     * @param domainEvent the domain event to append.
     */
    @Transactional(propagation = MANDATORY)
    public void append(@NonNull DomainEvent domainEvent) {
        repository.saveAndFlush(map(domainEvent, mapper));
    }
    
    @NonNull
    @Transactional(readOnly = true)
    public List<StoredDomainEvent> findEvents(@NonNull Long start, @NonNull  Long end) {
        return repository.findEventsBetween(start, end);
    }

}
