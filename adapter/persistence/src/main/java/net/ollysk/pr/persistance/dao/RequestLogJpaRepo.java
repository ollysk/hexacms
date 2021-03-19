package net.ollysk.pr.persistance.dao;

import java.util.Optional;
import net.ollysk.pr.persistance.model.RequestLogJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestLogJpaRepo extends JpaRepository<RequestLogJpa, Long> {

  long findTop10ByIdLessThanEqualOrderByIdDesc(@Param("id") long id);

  @Query("select min(t.userId) from RequestLogJpa t where t.trackingId = :trackingId and t.userId>0")
  Optional<Long> findUserAliasId(@Param("trackingId") long trackingId);
}
