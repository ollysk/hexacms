package net.ollysk.pr.web.controller.user;

import java.util.Locale;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.ollysk.pr.exception.ReCaptchaInvalidException;
import net.ollysk.pr.exception.UserAlreadyExistException;
import net.ollysk.pr.model.User;
import net.ollysk.pr.model.UserCountry;
import net.ollysk.pr.port.in.CaptchaService;
import net.ollysk.pr.port.in.PasswordService;
import net.ollysk.pr.port.in.UserService;
import net.ollysk.pr.web.config.ConfigProperties;
import net.ollysk.pr.web.event.UserRegistrationEvent;
import net.ollysk.pr.web.mapper.UserWebMapper;
import net.ollysk.pr.web.model.RegistrationEvent;
import net.ollysk.pr.web.model.UserWeb;
import net.ollysk.pr.web.service.CookieService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/user")
public class RegistrationController {

  private final UserService userService;
  private final CaptchaService captchaService;
  private final MessageSource messageSource;
  private final ApplicationEventPublisher eventPublisher;
  private final PasswordService passwordService;
  private final CookieService cookieService;
  private final UserWebMapper userWebMapper;
  private final ConfigProperties props;

  @GetMapping("")
  public String showSignInPage(final HttpServletRequest request, final UserWeb userWeb,
      BindingResult bindingResult, Model model,
      @AuthenticationPrincipal User user,
      @RequestParam("messageKey") final Optional<String> messageKey,
      @RequestParam("error") final Optional<String> error) {

    Locale locale = request.getLocale();
    messageKey.ifPresent(key -> {
          String message = messageSource.getMessage(key, null, locale);
          model.addAttribute("message", message);
        }
    );
    model.addAttribute("user", user);
    error.ifPresent(e -> model.addAttribute("error", e));

    if (bindingResult.hasErrors()) {
      model.addAttribute("message", bindingResult.getAllErrors());
      return "security/signIn";
    }
    return user != null ? "user" : "security/signIn";
  }

  @GetMapping("/register")
  public String showRegistrationPage(UserWeb userWeb, Model model) {
    model.addAttribute("countries", UserCountry.values());
    return "security/registration";
  }

  @PostMapping("/register")
  public String register(final @Valid UserWeb userWeb, BindingResult bindingResult, Model model,
      final HttpServletRequest request, RedirectAttributes redirectAttrs) {
    model.addAttribute("countries", UserCountry.values());
    if (bindingResult.hasErrors()) {
      log.error("{ }" + bindingResult.getAllErrors());
      return "security/registration";
    }
    final String response = request.getParameter("g-recaptcha-response");

    try {

      long aliasId = cookieService.getMinAliasIdFromCookie(request).orElse(0L);

      long trackingId = cookieService.getCookieId(request).orElse(0L);
      //todo uncomment, if tokens for Recaptcha are updated in application.yml
      ///captchaService.processResponse(response);
      String rawPassword = passwordService.generatePassword();

      User user = userWebMapper.toUser(userWeb);

      user = userService
          .register(user, passwordService.getEncodedPassword(rawPassword),
              trackingId, aliasId);
      eventPublisher.publishEvent(new UserRegistrationEvent(
          RegistrationEvent.builder()
              .user(user).password(rawPassword).token("")
              .serverUrl(props.getServerUrl())
              .locale(request.getLocale()).build()));
    } catch (ReCaptchaInvalidException reCaptchaInvalidException) {
      model.addAttribute("message", "ReCaptcha Invalid Exception");
      return "security/registration";
    } catch (UserAlreadyExistException uae) {
      model.addAttribute("message", "user already exists");
      return "security/registration";
    }
    redirectAttrs.addFlashAttribute("message", "password was sent to your email");
    return "redirect:/user";
  }
}
