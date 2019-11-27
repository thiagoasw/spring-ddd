package io.tasw.app;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;
import static javax.persistence.LockModeType.PESSIMISTIC_READ;
import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
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
@Transactional(propagation = REQUIRES_NEW)
public class BusinessService {

    private final BusinessRepository repository;
    
    @NonNull
    @Lock(PESSIMISTIC_READ)
    public BusinessId handle(@NonNull @Valid CreateBusiness form) {
        
        Business business = repository.findOneByName(form.name())
            .orElse(toDomainModel(form));
        
        repository.save(business);
        
        return business.id();
    }

    Business toDomainModel(@NonNull CreateBusiness form) {
        return Business.builder(form.name())
            .totalEmployees(form.totalEmployees())
        .build();
    }
    
    @NonNull
    @Transactional(readOnly = true)
    public List<Business> list() {
        return repository.findAll();
    }

    @NonNull
    @Transactional(readOnly = true)
    public Business get(@NonNull BusinessId id) {
        return repository.findById(requireNonNull(id))
            .orElseThrow(() -> new EntityNotFoundException(format("Not found any Business with code %s.", id.toUUID())));
    }

}
