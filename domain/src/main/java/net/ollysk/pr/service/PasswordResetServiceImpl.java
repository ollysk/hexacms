package net.ollysk.pr.service;

import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import net.ollysk.pr.model.User;
import net.ollysk.pr.model.VerificationToken;
import net.ollysk.pr.port.in.PasswordResetService;
import net.ollysk.pr.port.in.PasswordService;
import net.ollysk.pr.port.out.UserRepo;
import net.ollysk.pr.port.out.VerificationTokenRepo;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordResetServiceImpl implements PasswordResetService {

  private static final int TOKEN_EXPIRATION_HOURS = 24;
  private final PasswordService passwordService;
  private final UserRepo userRepo;
  private final VerificationTokenRepo tokenRepo;

  @Override
  public void createPasswordResetTokenForUser(final User user, final String token) {
    final VerificationToken verificationToken =
        VerificationToken.builder()
            .token(token).user(user).expiryDate(LocalDateTime.now()
            .plusHours(TOKEN_EXPIRATION_HOURS))
            .build();
    tokenRepo.save(verificationToken);
  }

  @Override
  public VerificationToken getPasswordResetToken(final String token) {
    return tokenRepo.findByToken(token);
  }

  @Override
  public Optional<User> getUserByPasswordResetToken(final String token) {
    return Optional.ofNullable(tokenRepo.findByToken(token).getUser());
  }

  @Override
  public void changeUserPassword(final User user, final String password) {
    user.setPassword(passwordService.encode(password));
    userRepo.save(user);
  }

  @Override
  public void deleteResetToken(VerificationToken verificationToken) {
    ///verificationTokenRepo.delete(verificationToken);
    ///user.setPassword(passwordService.encode(password));
    ///userRepo.save(user);
  }
}
