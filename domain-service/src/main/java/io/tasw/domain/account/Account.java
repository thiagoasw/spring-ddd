package io.tasw.domain.account;

import static io.tasw.sk.domain.DomainObjectId.randomId;
import static java.util.Objects.requireNonNull;
import static lombok.AccessLevel.PRIVATE;

import javax.money.MonetaryAmount;
import javax.persistence.Column;
import javax.persistence.Entity;

import org.hibernate.annotations.Columns;
import org.javamoney.moneta.FastMoney;

import io.tasw.domain.account.event.AccountOpened;
import io.tasw.domain.business.BusinessId;
import io.tasw.sk.domain.AbstractAggregateRoot;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Aggregate root representing an account.
 */
@Getter
@ToString
@NoArgsConstructor(access = PRIVATE, force = true)

@Entity
public class Account extends AbstractAggregateRoot<AccountId> {

    public static transient final MonetaryAmount INITIAL_BALANCE = FastMoney.of(0, "BRL");
    
    private final BusinessId business;

    @Columns(columns = { 
        @Column(name = "limit_currency"), 
        @Column(name = "limit_value") 
    })
    private MonetaryAmount limit;

    @Columns(columns = { 
        @Column(name = "balance_currency"), 
        @Column(name = "balance_value") 
    })
    private MonetaryAmount balance;
    
    private Account(BusinessId business, MonetaryAmount limit) {
        super(randomId(AccountId.class));
        this.business = requireNonNull(business);
        this.limit = requireNonNull(limit);
        this.balance = INITIAL_BALANCE;
    }
    
    public static Account of(BusinessId business, MonetaryAmount limit) {
        Account account = new Account(business, limit);
        account.registerEvent(AccountOpened.from(account));
        return account;
    }
    
}
