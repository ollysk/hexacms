package net.ollysk.pr.model;

import java.util.Locale;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MailData {
  Type type;
  User user;
  String password;
  String token;
  String serverUrl;
  Locale locale;

  public enum Type {
    REGISTRATION_MAIL, RESET_PASSWORD_TOKEN_MAIL, RESET_PASSWORD_MAIL
  }
}

