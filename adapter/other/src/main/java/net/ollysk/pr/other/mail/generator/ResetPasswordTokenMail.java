package net.ollysk.pr.other.mail.generator;

import lombok.RequiredArgsConstructor;
import net.ollysk.pr.model.MailData;
import net.ollysk.pr.other.mail.MailGenerator;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component("RESET_PASSWORD_TOKEN_MAIL")
public class ResetPasswordTokenMail implements MailGenerator {

  private final MessageSource messages;

  @Override public SimpleMailMessage generate(MailData mailData) {
    final String url =
        mailData.getServerUrl()
            + "/user/password/reset?id="
            + mailData.getUser().getId()
            + "&token=" + mailData.getToken();
    final String message = messages.getMessage("message.resetPassword",
        new Object[] {url}, mailData.getLocale());
    return getMailMessage("Reset Password", message, mailData.getUser());
  }

  @Override public MailData.Type getType() {
    return MailData.Type.RESET_PASSWORD_TOKEN_MAIL;
  }
}
