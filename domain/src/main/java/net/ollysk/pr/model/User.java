package net.ollysk.pr.model;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import lombok.Data;

@Data
public class User {

  private long id;
  private String username;
  private String email;
  private String company;
  private String fullName;
  private String position;
  private int countryId;
  private String site;
  private String telephone;
  private String word;
  private String password;
  private LocalDateTime created;
  private LocalDateTime lastAccess;
  private LocalDateTime lastLogIn;
  private long aliasId;
  private boolean isEnabled;
  private boolean isAccountNonExpired;
  private boolean isAccountNonLocked;
  private boolean isCredentialsNonExpired;

  public Collection<String> getRoles() {
    UserRole role = UserRole.ROLE_ANON;
    if (id >= 1) {
      role = id == 1 ? UserRole.ROLE_ADMIN : UserRole.ROLE_USER;
    }
    return Collections.singletonList(role.toString());
  }
}
