package io.tasw.infra.stream;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import io.tasw.domain.business.event.BusinessCreated;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@AllArgsConstructor

@Service
@EnableBinding(value = Source.class)
public class BusinessPublisher {

    private final Source source;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onBusinessCreatedEvent(BusinessCreated event) {

        log.trace(event);

        source.output().send(
            MessageBuilder.withPayload(event)
                .setHeader("type", BusinessCreated.class.getSimpleName())
            .build()
        );
    }
}
