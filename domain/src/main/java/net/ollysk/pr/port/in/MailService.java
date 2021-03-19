package net.ollysk.pr.port.in;

import net.ollysk.pr.model.MailData;

public interface MailService {

  void sendMail(MailData mailData);
}
