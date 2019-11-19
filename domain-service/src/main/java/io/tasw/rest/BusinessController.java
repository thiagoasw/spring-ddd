package io.tasw.rest;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

import java.util.Collection;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.tasw.app.BusinessService;
import io.tasw.app.form.BusinessForms.CreateBusiness;
import io.tasw.domain.business.Business;
import io.tasw.domain.business.BusinessId;
import lombok.AllArgsConstructor;

@AllArgsConstructor

@RestController
@RequestMapping(path = "/api/business", produces = APPLICATION_JSON_VALUE)
public class BusinessController {

    private final BusinessService service;
 
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    ResponseEntity<Void> create(@RequestBody CreateBusiness form) {
        
        BusinessId id = service.handle(form);
        
        return ResponseEntity.created(fromCurrentRequest()
                .path("/").path(id.toUUID()).build().toUri())
        .build();
    }

    @GetMapping
    Collection<Business> list() {
        return service.list();
    }
    
    @GetMapping(path = "/{id}")
    Business get(@PathVariable BusinessId id) {
        return service.get(id);
    }

}
