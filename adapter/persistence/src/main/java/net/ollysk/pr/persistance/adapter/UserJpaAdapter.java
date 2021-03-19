package net.ollysk.pr.persistance.adapter;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import net.ollysk.pr.model.User;
import net.ollysk.pr.persistance.dao.UserJpaRepo;
import net.ollysk.pr.persistance.mapper.UserMapper;
import net.ollysk.pr.persistance.model.UserJpa;
import net.ollysk.pr.port.out.UserRepo;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserJpaAdapter implements UserRepo {

  private final UserJpaRepo userJpaRepo;
  private final UserMapper userMapper;

  @Override public User save(User user) {
    UserJpa userJpa = userMapper.toUserJpa(user);
    return userMapper.toUser(userJpaRepo.save(userJpa));
  }

  @Override public Optional<User> findById(Long id) {
    return userJpaRepo.findById(id).map(userMapper::toUser);
  }

  @Override public Optional<User> findByEmail(String email) {
    return userJpaRepo.findByEmail(email).map(userMapper::toUser);
  }

  @Override public Optional<User> findByUsername(String username) {
    return userJpaRepo.findByUsername(username).map(userMapper::toUser);
  }
}
