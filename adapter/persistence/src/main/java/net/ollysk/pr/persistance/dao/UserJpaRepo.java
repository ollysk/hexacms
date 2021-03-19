package net.ollysk.pr.persistance.dao;

import java.util.Optional;
import net.ollysk.pr.persistance.model.UserJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJpaRepo extends JpaRepository<UserJpa, Long> {
  Optional<UserJpa> findByEmail(String email);

  Optional<UserJpa> findByUsername(String username);
}
