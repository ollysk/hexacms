package net.ollysk.pr.service;

import java.time.LocalDateTime;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import net.ollysk.pr.model.User;
import net.ollysk.pr.model.VerificationToken;
import net.ollysk.pr.port.in.VerificationTokenService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class VerificationTokenServiceImpl implements VerificationTokenService {
  @Override public VerificationToken findByToken(String token) {
    return null;
  }

  @Override public VerificationToken findByUser(User user) {
    return null;
  }

  @Override public Stream<VerificationToken> findAllByExpiryDateLessThan(LocalDateTime now) {
    return null;
  }

  @Override public void deleteByExpiryDateLessThan(LocalDateTime now) {

  }

  @Override public void deleteAllExpiredSince(LocalDateTime now) {

  }
}
