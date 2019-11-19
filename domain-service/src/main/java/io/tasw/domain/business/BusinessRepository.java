package io.tasw.domain.business;

import java.util.Optional;

public interface BusinessRepository {

    Business save(Business entity);

    Optional<Business> getOne(BusinessId id);
  
    Optional<Business> findOneByName(String name);
    
}