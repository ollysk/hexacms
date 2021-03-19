package net.ollysk.pr.port.out;

public interface PasswordPort {

  String encode(CharSequence rawPassword);

  boolean matches(CharSequence rawPassword, String encodedPassword);

  String generatePassword();

  String getEncodedPassword();

  String getEncodedPassword(String password);
}
