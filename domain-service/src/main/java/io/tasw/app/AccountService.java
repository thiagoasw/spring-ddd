package io.tasw.app;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;
import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import io.tasw.domain.account.Account;
import io.tasw.domain.account.AccountId;
import io.tasw.domain.account.AccountRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor

@Service
@Validated
@Transactional(propagation = REQUIRES_NEW)
public class AccountService {

    private final AccountRepository repository;
    
    @NonNull
    @Transactional(readOnly = true)
    public List<Account> list() {
        return repository.findAll();
    }

    @NonNull
    @Transactional(readOnly = true)
    public Account get(@NonNull AccountId id) {
        return repository.findById(requireNonNull(id))
            .orElseThrow(() -> new EntityNotFoundException(format("Not found any Account with code %s.", id.toUUID())));
    }
    
}
