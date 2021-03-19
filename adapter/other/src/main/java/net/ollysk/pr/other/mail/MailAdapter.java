package net.ollysk.pr.other.mail;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import net.ollysk.pr.model.MailData;
import net.ollysk.pr.port.out.MailPort;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MailAdapter implements MailPort {
  private final JavaMailSender mailSender;
  private final Map<String, MailGenerator> generatorMap;

  @Override public void sendMail(MailData mailData) {
    String type = mailData.getType().toString();
    MailGenerator mailGenerator = generatorMap.get(type);
    if (mailGenerator == null) {
      throw new UnsupportedOperationException(type + " is not supported");
    }
    SimpleMailMessage message = mailGenerator.generate(mailData);
    send(message);
  }

  public void send(SimpleMailMessage message) throws MailException {
    mailSender.send(message);
  }

  /* private final List<MailGenerator> mailGenerators;
   *//**
   * Just optional method.
   * Use it as alternative to @Component("MAILGENERATOR_TYPE_NAME") with map injection
   * List<MailGenerator> mailGenerators should be @Autowired (and contain all implementations)
   *//*
  Map<MailData.MailType, MailGenerator> getMailGeneratorMap() {
    return mailGenerators.stream().collect(Collectors.toMap(
        MailGenerator::getMyCode,mailGenerator -> mailGenerator));
  }*/
}
