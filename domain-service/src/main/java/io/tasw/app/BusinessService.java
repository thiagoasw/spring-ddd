package io.tasw.app;

import static javax.persistence.LockModeType.PESSIMISTIC_READ;

import javax.validation.Valid;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import io.tasw.app.form.BusinessForms.CreateBusiness;
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
    @Lock(PESSIMISTIC_READ)
    public BusinessId handle(@NonNull @Valid CreateBusiness form) {
        
        Business business = repository.findOneByName(form.name())
            .orElse(Business.builder(form.name())
                    .totalEmployees(form.totalEmployees())
                .build());
        
        repository.save(business);
        
        return business.id();
    }
    
}
