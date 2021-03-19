package net.ollysk.pr.other.captcha;

import java.net.URI;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.ollysk.pr.exception.ReCaptchaInvalidException;
import net.ollysk.pr.port.in.CaptchaService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestOperations;

@RequiredArgsConstructor
@Slf4j
@Service("captchaService")
public class CaptchaServiceImpl implements CaptchaService {

  private static final Pattern RESPONSE_PATTERN = Pattern.compile("[A-Za-z0-9_-]+");
  private static final String RECAPTCHA_URL_TEMPLATE =
      "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s&remoteip=%s";

  private final HttpServletRequest request;
  private final CaptchaSettings captchaSettings;
  private final RestOperations restTemplate;

  @Override
  public String getCaptchaSite() {
    return captchaSettings.getSite();
  }

  @Override
  public void processResponse(final String response) {
    checkSecurity(response);

    final URI verifyUri = URI.create(
        String.format(RECAPTCHA_URL_TEMPLATE, getCaptchaSecret(), response, getClientIp()));
    log.info("verifyUri {}", verifyUri);
    try {
      final CaptchaResponse captchaResponse =
          restTemplate.getForObject(verifyUri, CaptchaResponse.class);
      log.info("Google's response: {} ", captchaResponse);

      if (captchaResponse == null
          || !captchaResponse.getIsSuccess()
          || captchaResponse.hasClientError()) {
        log.warn("Captcha was not successfully validated");
        throw new ReCaptchaInvalidException("Captcha was not successfully validated");
      }
    } catch (RestClientException rce) {
      log.warn("Registration unavailable at this time.  Please try again later");
    }
    log.info("CAPTCHA OK {}", getClientIp());
  }

  @Override
  public String getCaptchaSecret() {
    return captchaSettings.getSecret();
  }

  private void checkSecurity(final String response) {
    log.info("Attempting to validate response {}" + response);
    if (!isValidResponse(response)) {
      throw new ReCaptchaInvalidException("Response contains invalid characters");
    }
  }

  private boolean isValidResponse(final String response) {
    return StringUtils.hasLength(response) && RESPONSE_PATTERN.matcher(response).matches();
  }

  private String getClientIp() {
    final String xfHeader = request.getHeader("X-Forwarded-For");
    if (xfHeader == null) {
      return request.getRemoteAddr();
    }
    return xfHeader.split(",")[0];
  }
}
