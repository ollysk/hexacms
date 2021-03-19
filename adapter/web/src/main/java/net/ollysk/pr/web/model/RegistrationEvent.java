package net.ollysk.pr.web.model;

import java.util.Locale;
import lombok.Builder;
import lombok.Data;
import net.ollysk.pr.model.User;

@Builder
@Data
public class RegistrationEvent {

  User user;
  String password;
  String token;
  String serverUrl;
  Locale locale;
}
