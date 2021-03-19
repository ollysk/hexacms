package net.ollysk.pr.service;

import lombok.RequiredArgsConstructor;
import net.ollysk.pr.model.MailData;
import net.ollysk.pr.port.in.MailService;
import net.ollysk.pr.port.out.MailPort;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MailServiceImpl implements MailService {

  private final MailPort mailPort;

  @Override public void sendMail(MailData mailData) {
    mailPort.sendMail(mailData);
  }
}
