package net.ollysk.pr.other.password.adapter;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.ollysk.pr.port.out.PasswordPort;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class PasswordAdapter implements PasswordPort {

  @Override
  public String encode(CharSequence rawPassword) {
    MessageDigest md;
    byte[] digest = null;
    try {
      md = MessageDigest.getInstance("MD5");
      md.update(toBytes(rawPassword));
      digest = md.digest();
    } catch (NoSuchAlgorithmException e) {
      log.error("Encode exception { }", e);
    }

    return bytesToHex(digest);
  }

  private byte[] toBytes(CharSequence str) {
    return str != null ? str.toString().getBytes() : new byte[0];
  }

  private String bytesToHex(byte[] bytes) {
    StringBuilder sb = new StringBuilder();
    for (byte b : bytes) {
      sb.append(String.format("%02x", b));
    }
    return sb.toString();
  }

  @Override
  public boolean matches(CharSequence rawPassword, String encodedPassword) {
    return encodedPassword.equalsIgnoreCase(encode(rawPassword));
  }

  public String generatePassword() {
    List<CharacterRule> rules = Arrays.asList(
        // at least one upper-case character
        new CharacterRule(EnglishCharacterData.UpperCase, 1),

        // at least one lower-case character
        new CharacterRule(EnglishCharacterData.LowerCase, 1),

        // at least one digit character
        new CharacterRule(EnglishCharacterData.Digit, 1));

    org.passay.PasswordGenerator generator = new org.passay.PasswordGenerator();

    // Generated password is 12 characters long, which complies with policy
    return generator.generatePassword(10, rules);
  }

  public String getEncodedPassword() {
    return encode(generatePassword());
  }

  public String getEncodedPassword(String password) {
    return encode(password);
  }
}
