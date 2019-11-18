package io.tasw.infra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.tasw.domain.business.Business;
import io.tasw.domain.business.BusinessId;
import io.tasw.domain.business.BusinessRepository;

@Repository
public interface BusinessJpaRepository extends BusinessRepository, JpaRepository<Business, BusinessId> {}
