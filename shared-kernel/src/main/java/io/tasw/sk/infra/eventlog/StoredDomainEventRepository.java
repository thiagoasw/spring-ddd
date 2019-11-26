package io.tasw.sk.infra.eventlog;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

interface StoredDomainEventRepository extends JpaRepository<StoredDomainEvent, Long> {
    
    @Query("select se from StoredDomainEvent se where se.id >= :start and se.id <= :end order by se.id")
    List<StoredDomainEvent> findEventsBetween(@Param("start") Long start, @Param("end") Long end);
    
}
