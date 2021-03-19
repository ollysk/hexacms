package net.ollysk.pr.web.event;

import lombok.Getter;
import lombok.ToString;
import net.ollysk.pr.web.model.RegistrationEvent;
import org.springframework.context.ApplicationEvent;

@Getter @ToString
public class UserResetPasswordTokenEvent extends ApplicationEvent {

  private final RegistrationEvent registrationEvent;

  public UserResetPasswordTokenEvent(RegistrationEvent registrationEvent) {
    super(registrationEvent);
    this.registrationEvent = registrationEvent;
  }
}
