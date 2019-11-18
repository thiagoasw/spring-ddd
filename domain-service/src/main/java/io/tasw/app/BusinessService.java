package io.tasw.app;

import javax.validation.Valid;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import io.tasw.app.form.BusinessForms.BusinessForm;
import io.tasw.domain.business.Business;
import io.tasw.domain.business.BusinessId;
import io.tasw.domain.business.BusinessRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor

@Service
@Validated
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class BusinessService {

    private final BusinessRepository repository;
    
    @NonNull
    public BusinessId create(@NonNull @Valid BusinessForm form) {
        
        Business business = Business.builder(form.getName())
            .totalEmployees(form.getTotalEmployees())
        .build();
        
        repository.save(business);
        
        return business.id();
    }
    
}
