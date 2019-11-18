package io.tasw.domain.business;

public interface BusinessRepository {

    Business save(Business entity);

    Business getOne(BusinessId id);
  
}