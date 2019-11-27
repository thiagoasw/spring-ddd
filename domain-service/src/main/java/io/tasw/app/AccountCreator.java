package io.tasw.app;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import io.tasw.domain.account.Account;
import io.tasw.domain.account.AccountLimitPolicy;
import io.tasw.domain.account.AccountRepository;
import io.tasw.domain.business.event.BusinessCreated;
import lombok.AllArgsConstructor;

@AllArgsConstructor

@Service
@Transactional(propagation = REQUIRES_NEW)
class AccountCreator {

    private final AccountRepository repository;

    @TransactionalEventListener
    public void on(BusinessCreated event) {
        AccountLimitPolicy policy = AccountLimitPolicy.defaultPolicy();
        repository.save(Account.of(event.id(), policy.apply(event.totalEmployees())));
    }

}
