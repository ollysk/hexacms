package net.ollysk.pr.port.out;

import net.ollysk.pr.model.MailData;

public interface MailPort {

  void sendMail(MailData mailData);
}
