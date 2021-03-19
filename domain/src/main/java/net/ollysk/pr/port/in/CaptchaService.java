package net.ollysk.pr.port.in;

public interface CaptchaService {

  void processResponse(final String response);

  String getCaptchaSite();

  String getCaptchaSecret();
}
