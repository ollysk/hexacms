package net.ollysk.pr.persistance.model;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Builder
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class UserJpa {

  @Transient @Builder.Default
  boolean isAccountNonExpired = true;
  @Transient @Builder.Default
  boolean isAccountNonLocked = true;
  @Transient @Builder.Default
  boolean isCredentialsNonExpired = true;
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
  private LocalDateTime lastAccess;
  private LocalDateTime lastLogIn;
  private long aliasId;
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(unique = true, nullable = false)
  private long id;
  private LocalDateTime created;
  private boolean isEnabled;
}
