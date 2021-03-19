package net.ollysk.pr.web.event.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.ollysk.pr.model.MailData;
import net.ollysk.pr.port.in.MailService;
import net.ollysk.pr.web.event.UserRegistrationEvent;
import net.ollysk.pr.web.event.UserResetPasswordEvent;
import net.ollysk.pr.web.event.UserResetPasswordTokenEvent;
import net.ollysk.pr.web.mapper.RegistrationEventMapper;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class RegistrationListener {

  private final MailService mailService;
  private final RegistrationEventMapper registrationEventMapper;

  @Async
  @EventListener
  void handleUserRegistrationEvent(UserRegistrationEvent event) {
    MailData mailData = registrationEventMapper.toMailData(event.getRegistrationEvent());
    mailData.setType(MailData.Type.REGISTRATION_MAIL);
    mailService.sendMail(mailData);
    log.info("UserRegistrationEvent received async. " + Thread.currentThread().getName());
  }

  @Async
  @EventListener
  void handleUserResetPasswordTokenEvent(UserResetPasswordTokenEvent event) {
    MailData mailData = registrationEventMapper.toMailData(event.getRegistrationEvent());
    mailData.setType(MailData.Type.RESET_PASSWORD_TOKEN_MAIL);
    mailService.sendMail(mailData);
  }

  @Async
  @EventListener
  void handleUserResetPasswordEvent(UserResetPasswordEvent event) {
    MailData mailData = registrationEventMapper.toMailData(event.getRegistrationEvent());
    mailData.setType(MailData.Type.RESET_PASSWORD_MAIL);
    mailService.sendMail(mailData);
  }
}