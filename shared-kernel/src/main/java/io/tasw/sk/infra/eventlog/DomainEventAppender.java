package io.tasw.sk.infra.eventlog;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import io.tasw.sk.domain.DomainEvent;
import lombok.AllArgsConstructor;

/**
 * The domain event log appender listens for all {@link DomainEvent}s that are published and
 * {@link DomainEventService#append(DomainEvent) appends} them to the domain event log. The domain event is stored
 * inside the same transaction that published the event so if the transaction fails, no event is stored.
 */
@AllArgsConstructor

@Service
class DomainEventAppender {

    private final DomainEventService service;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void on(@NonNull DomainEvent domainEvent) {
        service.append(domainEvent);
    }
    
}
