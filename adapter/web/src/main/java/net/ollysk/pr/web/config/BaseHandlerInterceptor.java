package net.ollysk.pr.web.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import net.ollysk.pr.port.in.RequestLogService;
import net.ollysk.pr.web.mapper.RequestLogWebMapper;
import net.ollysk.pr.web.security.UserSecurity;
import net.ollysk.pr.web.service.CookieService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;

@RequiredArgsConstructor
@Configuration
public class BaseHandlerInterceptor implements HandlerInterceptor {

  private final RequestLogService requestLogService;
  private final CookieService cookieService;
  private final RequestLogWebMapper requestLogWebMapper;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    cookieService.setCookieIdIfNeeded(request, response);
    return true;
  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
      Object handler, Exception ex) throws Exception {
    requestLogService.save(requestLogWebMapper.toRequestLog(getUserId(), request));
  }

  private long getUserId() {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return isUserLoggedIn(principal) ? ((UserSecurity) principal).getId() : 0;
  }

  private boolean isUserLoggedIn(Object principal) {
    return principal instanceof UserSecurity;
  }
}
