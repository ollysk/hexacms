package net.ollysk.pr.port.in;

public interface PasswordService {

  String encode(CharSequence rawPassword);

  boolean matches(CharSequence rawPassword, String encodedPassword);

  String generatePassword();

  String getEncodedPassword();

  String getEncodedPassword(String password);
}
