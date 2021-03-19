package net.ollysk.pr.web.mapper;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import javax.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import net.ollysk.pr.model.RequestLog;
import net.ollysk.pr.web.service.CookieService;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class RequestLogWebMapper {

  private final CookieService cookieService;

  public RequestLog toRequestLog(long userId, HttpServletRequest request) {
    return toRequestLog(userId, request, LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
  }

  public RequestLog toRequestLog(long userId, HttpServletRequest request, LocalDateTime created) {
    String requestString = getRequestString(request);
    long cookieId = cookieService.getCookieId(request).orElse(0L);
    return RequestLog.builder()
        .userId(userId)
        .created(created)
        .ip(getClientIp(request))
        .request(requestString)
        .trackingId(cookieId)
        .build();
  }

  private String getRequestString(HttpServletRequest request) {
    return request.getQueryString() != null ?
        request.getRequestURI() + "?" + request.getQueryString()
        : request.getRequestURI();
  }

  private String getClientIp(HttpServletRequest request) {
    final String xfHeader = request.getHeader("X-Forwarded-For");
    if (xfHeader != null) {
      return xfHeader.split(",")[0];
    }
    return request.getRemoteAddr();
  }
}
