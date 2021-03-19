package net.ollysk.pr.web.security;

import java.util.Collection;
import java.util.Collections;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import net.ollysk.pr.model.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@NoArgsConstructor

public class UserSecurity implements UserDetails {

  @Getter @Accessors(fluent = true)
  private boolean isAccountNonExpired = true;

  @Getter @Accessors(fluent = true)
  private boolean isAccountNonLocked = true;

  @Getter @Accessors(fluent = true)
  private boolean isCredentialsNonExpired = true;

  @Getter @Accessors(fluent = true)
  private boolean isEnabled = true;

  private String username;
  private long id;
  private String email;
  private String fullName;
  private String password;

  @Override public Collection<? extends GrantedAuthority> getAuthorities() {
    UserRole role = UserRole.ROLE_ANON;
    if (id >= 1) {
      role = id == 1 ? UserRole.ROLE_ADMIN : UserRole.ROLE_USER;
    }
    return Collections.singletonList(new SimpleGrantedAuthority(role.toString()));
  }
}
