package io.tasw.domain.business.event;

import static lombok.AccessLevel.PRIVATE;

import java.time.Instant;

import io.tasw.domain.business.Business;
import io.tasw.domain.business.BusinessId;
import io.tasw.sk.domain.DomainEvent;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder(access = PRIVATE)
public class BusinessCreated implements DomainEvent {

    private final BusinessId id;
    
    private final String name;

    private final int totalEmployees;
    
    private final Instant occurredOn;

    public static BusinessCreated from(Business business) {
        return BusinessCreated.builder()
            .id(business.id())
            .name(business.name())
            .totalEmployees(business.totalEmployees())
            .occurredOn(Instant.now())
        .build();
    }

}
