package io.tasw.domain.business;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessRepository extends JpaRepository<Business, BusinessId> {

    Optional<Business> findOneByName(String name);

}
