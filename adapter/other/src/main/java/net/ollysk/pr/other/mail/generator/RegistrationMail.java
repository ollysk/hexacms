package net.ollysk.pr.other.mail.generator;

import lombok.RequiredArgsConstructor;
import net.ollysk.pr.model.MailData;
import net.ollysk.pr.other.mail.MailGenerator;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component("REGISTRATION_MAIL")
public class RegistrationMail implements MailGenerator {

  private final MessageSource messages;

  @Override public SimpleMailMessage generate(MailData mailData) {
    String message =
        messages.getMessage("message.regSucc",
            new Object[] {mailData.getUser().getUsername(), mailData.getPassword()},
            mailData.getLocale());
    return getMailMessage("Registration + Password", message, mailData.getUser());
  }

  @Override public MailData.Type getType() {
    return MailData.Type.REGISTRATION_MAIL;
  }
}
