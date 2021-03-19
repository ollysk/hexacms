package net.ollysk.pr.port.out;

import java.util.Optional;
import net.ollysk.pr.model.User;

public interface UserRepo {

  User save(User user);

  Optional<User> findById(Long id);

  Optional<User> findByEmail(String email);

  Optional<User> findByUsername(String username);
}
