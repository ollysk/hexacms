package net.ollysk.pr.web.service;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class CookieService {

  private static final String COOKIE_NAME = "cookieId";

  public void setCookieIdIfNeeded(HttpServletRequest request, HttpServletResponse response) {
    getCookieId(request).ifPresentOrElse(c -> {
    }, () -> setCookie(response));
  }

  public Optional<Long> getCookieId(HttpServletRequest request) {
    return request.getCookies() != null ?
        Arrays.stream(
            request.getCookies())
            .filter(c -> c.getName().equals(COOKIE_NAME))
            .map(r -> Long.valueOf(r.getValue()))
            .findFirst()
        : Optional.empty();
  }

  public Optional<Long> getMinAliasIdFromCookie(HttpServletRequest request) {
    return request.getCookies() != null ?
        Arrays.stream(
            request.getCookies())
            .filter(c -> c.getName().equals("aliases"))
            .map(r -> getMinFromCsvString(r.getValue()))
            .findFirst()
        : Optional.empty();
  }

  private long getMinFromCsvString(String str) {
    long aliasId = 0;
    try (Stream<String> stringStream = Arrays.stream(str.split("%2C"))) {
      aliasId = stringStream.mapToLong(Long::parseLong).min().orElse(0);
    } catch (Exception e) {
      log.error("Exception { }", e);
    }
    return aliasId;
  }

  public String setCookie(HttpServletResponse response) {
    long uuid = UUID.randomUUID().getMostSignificantBits();
    Cookie cookie = new Cookie(COOKIE_NAME, String.valueOf(uuid));
    cookie.setMaxAge(365 * 10 * 24 * 60 * 60); // expires in 365*10 days
    //cookie.setSecure(true);
    //cookie.setHttpOnly(true);
    cookie.setPath("/"); // global cookie accessible everywhere
    response.addCookie(cookie);
    return "Username is changed!";
  }

  public void getCookies(HttpServletRequest request) {

    Arrays.stream(request.getCookies())
        .forEach(c ->
            log.info(c.getName() + ">>" + UriUtils.decode(c.getValue(), "UTF-8")));
  }
}
