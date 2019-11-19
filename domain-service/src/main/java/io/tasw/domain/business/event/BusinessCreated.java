package io.tasw.domain.business.event;

import static lombok.AccessLevel.PRIVATE;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonProperty;

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

    private static final long serialVersionUID = -251277531938559464L;

    @JsonProperty
    private final BusinessId businessId;
    
    @JsonProperty
    private final String name;

    @JsonProperty
    private final int totalEmployees;
    
    @JsonProperty
    private final Instant occurredOn;

    public static BusinessCreated from(Business business) {
        return BusinessCreated.builder()
            .businessId(business.id())
            .name(business.name())
            .totalEmployees(business.totalEmployees())
            .occurredOn(Instant.now())
        .build();
    }

}
