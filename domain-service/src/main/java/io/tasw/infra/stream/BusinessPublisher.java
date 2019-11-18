package io.tasw.infra.stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import io.tasw.domain.business.event.BusinessCreated;
import lombok.AllArgsConstructor;

@AllArgsConstructor

@Service
@EnableBinding(value = Source.class)
public class BusinessPublisher {

    private static final Logger LOGGER = LoggerFactory.getLogger(BusinessPublisher.class);

    private final Source source;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void onBusinessCreatedEvent(BusinessCreated event) {

        LOGGER.trace(event.toString());

        source.output().send(
            MessageBuilder.withPayload(event)
                .setHeader("type", BusinessCreated.class.getSimpleName())
            .build()
        );
    }
}
