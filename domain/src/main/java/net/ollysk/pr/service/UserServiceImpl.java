package net.ollysk.pr.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import net.ollysk.pr.exception.UserAlreadyExistException;
import net.ollysk.pr.exception.UserNotFoundException;
import net.ollysk.pr.model.User;
import net.ollysk.pr.port.in.UserAliasService;
import net.ollysk.pr.port.in.UserService;
import net.ollysk.pr.port.out.UserRepo;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserRepo userRepo;
  private final UserAliasService userAliasService;

  @Override
  public User register(User user, String encodedPassword, long userTrackingId,
      long userAliasId) throws UserAlreadyExistException {

    if (emailExists(user.getEmail()) || usernameExists(user.getUsername())) {
      throw new UserAlreadyExistException(
          "There is an account with that email address or username: " + user.getEmail()
              + user.getUsername());
    }

    long aliasId =
        userAliasId != 0 ? userAliasId : userAliasService.findUserAliasId(userTrackingId);
    //User user = userMapper.toUser(userWeb);
    user.setPassword(encodedPassword);
    user.setAliasId(aliasId);
    user.setCreated(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
    user.setIsEnabled(true);
    return userRepo.save(user);
  }

  @Override public void saveRegisteredUser(net.ollysk.pr.model.User user) {
    userRepo.save(user);
  }

  private boolean emailExists(final String email) {
    return userRepo.findByEmail(email).isPresent();
  }

  private boolean usernameExists(final String username) {
    return userRepo.findByUsername(username).isPresent();
  }

  @Override
  public void disableUser(User user) {

  }

  @Override public User findByEmailOrUsername(String emailOrUsername)
      throws UserNotFoundException {
    Optional<User> user = Optional.empty();
    if (emailOrUsername.contains("@")) {
      user = userRepo.findByEmail(emailOrUsername);
    }
    return user.or(() -> findByUsername(emailOrUsername)).orElseThrow(UserNotFoundException::new);
  }

  @Override
  public Optional<User> findByEmail(String email) {
    return userRepo.findByEmail(email);
  }

  @Override
  public Optional<User> findByUsername(String username) {
    return userRepo.findByUsername(username);
  }

  @Override
  public Optional<User> findById(long id) {
    return userRepo.findById(id);
  }
}
