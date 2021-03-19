package net.ollysk.pr.persistance.dao;

import java.time.LocalDateTime;
import java.util.stream.Stream;
import net.ollysk.pr.persistance.model.UserJpa;
import net.ollysk.pr.persistance.model.VerificationTokenJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationTokenJpaRepo extends JpaRepository<VerificationTokenJpa, Long> {

  VerificationTokenJpa findByToken(String token);

  VerificationTokenJpa findByUser(UserJpa user);

  Stream<VerificationTokenJpa> findAllByExpiryDateLessThan(LocalDateTime now);

  void deleteByExpiryDateLessThan(LocalDateTime now);

  @Modifying
  @Query("delete from VerificationTokenJpa t where t.expiryDate <= ?1")
  void deleteAllExpiredSince(LocalDateTime now);
}
