package net.ollysk.pr.port.in;

import java.util.Optional;
import net.ollysk.pr.model.User;
import net.ollysk.pr.model.VerificationToken;

public interface PasswordResetService {

  void createPasswordResetTokenForUser(final User user, final String token);

  VerificationToken getPasswordResetToken(final String token);

  Optional<User> getUserByPasswordResetToken(final String token);

  void changeUserPassword(User user, String password);

  void deleteResetToken(VerificationToken verificationToken);
}
