package io.tasw.sk.infra.rest.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.Collection;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.tasw.sk.infra.eventlog.DomainEventService;
import io.tasw.sk.infra.eventlog.StoredDomainEvent;
import lombok.AllArgsConstructor;

/**
 * REST controller that exposes the domain event log as a REST service.
 *
 */
@AllArgsConstructor

@RestController
@RequestMapping(path = "/api/events/", produces = APPLICATION_JSON_VALUE)
public class EventController {

    private final DomainEventService service;

    @GetMapping(path = "/{start},{end}")
    Collection<StoredDomainEvent> domainEvents(@PathVariable("start") long start, @PathVariable("end") long end) {
        return service.findEvents(start, end);
    }

}
