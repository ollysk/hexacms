package net.ollysk.pr.service;

import lombok.RequiredArgsConstructor;
import net.ollysk.pr.port.in.PasswordService;
import net.ollysk.pr.port.out.PasswordPort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordServiceImpl implements PasswordService {

  private final PasswordPort passwordPort;

  @Override public String encode(CharSequence rawPassword) {
    return passwordPort.encode(rawPassword);
  }

  @Override public boolean matches(CharSequence rawPassword, String encodedPassword) {
    return passwordPort.matches(rawPassword, encodedPassword);
  }

  @Override public String generatePassword() {
    return passwordPort.generatePassword();
  }

  @Override public String getEncodedPassword() {
    return passwordPort.getEncodedPassword();
  }

  @Override public String getEncodedPassword(String password) {
    return passwordPort.getEncodedPassword(password);
  }
}
