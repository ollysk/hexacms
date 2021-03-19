package net.ollysk.pr.web.security;

import java.util.Locale;
import lombok.RequiredArgsConstructor;
import net.ollysk.pr.port.in.UserService;
import org.springframework.context.MessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

  private final UserService userService;
  private final MessageSource messageSource;
  private final UserSecurityMapper userSecurityMapper;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    try {
      return userService.findByUsername(username).map(userSecurityMapper::toUserSecurity)
          .orElseThrow(() -> new UsernameNotFoundException(
              messageSource.getMessage("message.badEmail", null, Locale.ENGLISH)));
    } catch (final Exception e) {
      throw new UsernameNotFoundException("User not found");
    }
  }
}