package net.ollysk.pr.other.captcha.error;

public final class ReCaptchaInvalidException extends RuntimeException {

  private static final long serialVersionUID = 2791344587323289861L;

  public ReCaptchaInvalidException() {
    super();
  }

  public ReCaptchaInvalidException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public ReCaptchaInvalidException(final String message) {
    super(message);
  }

  public ReCaptchaInvalidException(final Throwable cause) {
    super(cause);
  }
}
