package net.ollysk.pr.other.mail.generator;

import lombok.RequiredArgsConstructor;
import net.ollysk.pr.model.MailData;
import net.ollysk.pr.other.mail.MailGenerator;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component("RESET_PASSWORD_MAIL")
public class ResetPasswordMail implements MailGenerator {

  private final MessageSource messages;

  @Override public SimpleMailMessage generate(MailData mailData) {
    final String message =
        messages.getMessage("message.updatePassword",
            new Object[] {mailData.getUser().getUsername(), mailData.getPassword()},
            mailData.getLocale());
    return getMailMessage("New Password", message, mailData.getUser());
  }

  @Override public MailData.Type getType() {
    return MailData.Type.RESET_PASSWORD_MAIL;
  }
}
