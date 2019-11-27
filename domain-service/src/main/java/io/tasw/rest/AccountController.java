package io.tasw.rest;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.Collection;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.tasw.app.AccountService;
import io.tasw.domain.account.Account;
import io.tasw.domain.account.AccountId;
import lombok.AllArgsConstructor;

@AllArgsConstructor

@RestController
@RequestMapping(path = "/api/accounts", produces = APPLICATION_JSON_VALUE)

public class AccountController {

    private final AccountService service;
    
    @GetMapping
    Collection<Account> list() {
        return service.list();
    }
    
    @GetMapping(path = "/{id}")
    Account get(@PathVariable AccountId id) {
        return service.get(id);
    }
    
}
