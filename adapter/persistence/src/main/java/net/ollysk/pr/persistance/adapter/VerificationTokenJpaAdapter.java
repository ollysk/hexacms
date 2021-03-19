package net.ollysk.pr.persistance.adapter;

import java.time.LocalDateTime;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import net.ollysk.pr.model.User;
import net.ollysk.pr.model.VerificationToken;
import net.ollysk.pr.persistance.dao.VerificationTokenJpaRepo;
import net.ollysk.pr.persistance.mapper.UserMapper;
import net.ollysk.pr.persistance.mapper.VerificationTokenMapper;
import net.ollysk.pr.persistance.model.VerificationTokenJpa;
import net.ollysk.pr.port.out.VerificationTokenRepo;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class VerificationTokenJpaAdapter implements VerificationTokenRepo {

  private final VerificationTokenJpaRepo tokenRepo;
  private final VerificationTokenMapper tokenMapper;
  private final UserMapper userMapper;

  @Override public VerificationToken save(VerificationToken token) {
    VerificationTokenJpa tokenJpa = tokenMapper.toVerificationTokenJpa(token);
    return tokenMapper.toVerificationToken(tokenRepo.save(tokenJpa));
  }

  @Override public VerificationToken findByToken(String token) {
    return tokenMapper.toVerificationToken(tokenRepo.findByToken(token));
  }

  @Override public VerificationToken findByUser(User user) {
    return tokenMapper.toVerificationToken(tokenRepo
        .findByUser(userMapper.toUserJpa(user)));
  }

  @Override public Stream<VerificationToken> findAllByExpiryDateLessThan(LocalDateTime now) {
    return null;
  }

  @Override public void deleteByExpiryDateLessThan(LocalDateTime now) {

  }

  @Override public void deleteAllExpiredSince(LocalDateTime now) {

  }
}
