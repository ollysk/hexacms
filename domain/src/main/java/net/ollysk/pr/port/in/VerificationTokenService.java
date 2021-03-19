package net.ollysk.pr.port.in;

import java.time.LocalDateTime;
import java.util.stream.Stream;
import net.ollysk.pr.model.User;
import net.ollysk.pr.model.VerificationToken;

public interface VerificationTokenService {

  VerificationToken findByToken(String token);

  VerificationToken findByUser(User user);

  Stream<VerificationToken> findAllByExpiryDateLessThan(LocalDateTime now);

  void deleteByExpiryDateLessThan(LocalDateTime now);

  void deleteAllExpiredSince(LocalDateTime now);
}
