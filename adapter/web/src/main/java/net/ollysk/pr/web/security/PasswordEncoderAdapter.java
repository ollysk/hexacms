package net.ollysk.pr.web.security;

import lombok.RequiredArgsConstructor;
import net.ollysk.pr.port.in.PasswordService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PasswordEncoderAdapter implements PasswordEncoder {

  private final PasswordService passwordService;

  @Override public String encode(CharSequence rawPassword) {
    return passwordService.encode(rawPassword);
  }

  @Override public boolean matches(CharSequence rawPassword, String encodedPassword) {
    return passwordService.matches(rawPassword, encodedPassword);
  }
}
