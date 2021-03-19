package net.ollysk.pr.validation;

@SuppressWarnings("serial")
public class EmailExistsException extends Exception {

  public EmailExistsException(final String message) {
    super(message);
  }
}
