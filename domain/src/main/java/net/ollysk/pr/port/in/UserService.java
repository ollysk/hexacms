package net.ollysk.pr.port.in;

import java.util.Optional;
import net.ollysk.pr.exception.UserAlreadyExistException;
import net.ollysk.pr.exception.UserNotFoundException;
import net.ollysk.pr.model.User;

public interface UserService {

  User register(User user, String encodedPassword, long userTrackingId,
      long userAliasId) throws UserAlreadyExistException;

  void saveRegisteredUser(User user);

  void disableUser(User user);

  Optional<User> findByEmail(String email);

  User findByEmailOrUsername(String emailOrUsername) throws UserNotFoundException;

  Optional<User> findByUsername(String username);

  Optional<User> findById(long id);
}
