package net.ollysk.pr.web.event;

import lombok.Getter;
import lombok.ToString;
import net.ollysk.pr.web.model.RegistrationEvent;
import org.springframework.context.ApplicationEvent;

@Getter @ToString
public class UserResetPasswordEvent extends ApplicationEvent {

  private final RegistrationEvent registrationEvent;

  public UserResetPasswordEvent(RegistrationEvent registrationEvent) {
    super(registrationEvent);
    this.registrationEvent = registrationEvent;
  }
}
