package io.tasw.domain.account.event;

import static lombok.AccessLevel.PRIVATE;

import java.time.Instant;

import javax.money.MonetaryAmount;

import io.tasw.domain.account.Account;
import io.tasw.domain.account.AccountId;
import io.tasw.domain.business.BusinessId;
import io.tasw.sk.domain.DomainEvent;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder(access = PRIVATE)
public class AccountOpened implements DomainEvent {

    private final AccountId id;
    
    private final BusinessId business;
    
    private final MonetaryAmount limit;
    
    private final MonetaryAmount balance;
    
    private final Instant occurredOn;

    public static AccountOpened from(Account account) {
        return AccountOpened.builder()
            .id(account.id())
            .business(account.business())
            .limit(account.limit())
            .balance(account.balance())
            .occurredOn(Instant.now())
        .build();
    }

}
