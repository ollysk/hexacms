package net.ollysk.pr.other.mail;

import net.ollysk.pr.model.MailData;
import net.ollysk.pr.model.User;
import org.springframework.mail.SimpleMailMessage;

public interface MailGenerator {

  SimpleMailMessage generate(MailData mailData);

  MailData.Type getType();

  default SimpleMailMessage getMailMessage(String subject, String text, User user) {
    final SimpleMailMessage email = new SimpleMailMessage();
    email.setTo(user.getEmail());
    email.setSubject(subject);
    email.setText(text);
    // email.setFrom(env.getProperty("support.email"));
    return email;
  }
}
