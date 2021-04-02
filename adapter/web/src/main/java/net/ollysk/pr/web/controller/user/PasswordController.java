package net.ollysk.pr.web.controller.user;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.ollysk.pr.exception.UserNotFoundException;
import net.ollysk.pr.model.User;
import net.ollysk.pr.model.VerificationToken;
import net.ollysk.pr.port.in.CaptchaService;
import net.ollysk.pr.port.in.PasswordResetService;
import net.ollysk.pr.port.in.PasswordService;
import net.ollysk.pr.port.in.UserService;
import net.ollysk.pr.web.config.ConfigProperties;
import net.ollysk.pr.web.event.UserResetPasswordEvent;
import net.ollysk.pr.web.event.UserResetPasswordTokenEvent;
import net.ollysk.pr.web.model.RegistrationEvent;
import net.ollysk.pr.web.model.UserWeb;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
@RequestMapping(path = "/user/password")
public class PasswordController {

  private final UserService userService;
  private final PasswordResetService passwordResetService;
  private final CaptchaService captchaService;
  private final MessageSource messageSource;
  private final PasswordService passwordService;
  private final ApplicationEventPublisher eventPublisher;
  private final ConfigProperties props;

  @GetMapping("")
  public String showPasswordResetPage(final UserWeb userWeb, BindingResult bindingResult,
      Model model,
      @RequestParam("messageKey") final Optional<String> messageKey,
      @RequestParam("error") final Optional<String> error) {
    error.ifPresent(e -> model.addAttribute("error", e));
    return "security/passwordReset";
  }

  private String getUserRedirect(HttpServletRequest request) {
    return "redirect:/user?lang=" + request.getLocale().getLanguage();
  }

  @PostMapping("")
  public String resetPassword(final HttpServletRequest request, final Model model,
      @RequestParam("email") final String userEmail, RedirectAttributes redirectAttrs) {
    final User user;
    try {
      user = userService.findByEmailOrUsername(userEmail);
      if (!user.getIsAccountNonExpired()) {
        throw new UserNotFoundException("blocked");
      }
    } catch (UserNotFoundException | UsernameNotFoundException unf) {
      log.error(unf.getMessage());
      redirectAttrs.addFlashAttribute("message",
          messageSource.getMessage("message.userNotFound", null, request.getLocale()));
      return getUserRedirect(request);
    }

    final String token = UUID.randomUUID().toString();
    passwordResetService.createPasswordResetTokenForUser(user, token);
    try {
      eventPublisher.publishEvent(new UserResetPasswordTokenEvent(
          RegistrationEvent.builder()
              .user(user).password("").token(token)
              .serverUrl(props.getServerUrl())
              .locale(request.getLocale()).build()));
    } /*catch (final MailAuthenticationException e) {
      return "redirect:/emailError.html?lang=" + request.getLocale().getLanguage();
    }*/ catch (final Exception e) {

      redirectAttrs.addFlashAttribute("message", e.getLocalizedMessage());
      return getUserRedirect(request);
    }
    redirectAttrs.addFlashAttribute("message",
        messageSource.getMessage("message.resetPasswordEmail", null, request.getLocale()));
    return getUserRedirect(request);
  }

  @GetMapping("/reset")
  public String resetPassword(final HttpServletRequest request, final Model model,
      @RequestParam("id") final long id, @RequestParam("token") final String token,
      RedirectAttributes redirectAttrs) {
    final Locale locale = request.getLocale();
    final VerificationToken passToken = passwordResetService.getPasswordResetToken(token);
    if (passToken == null || passToken.getUser().getId() != id) {
      final String message = messageSource.getMessage("auth.message.invalidToken", null, locale);
      redirectAttrs.addFlashAttribute("message", message);
      return "redirect:/user?lang=" + locale.getLanguage();
    }

    if (passToken.getExpiryDate().isBefore(LocalDateTime.now())) {
      redirectAttrs.addFlashAttribute("message",
          messageSource.getMessage("auth.message.expired", null, locale));
      return "redirect:/user?lang=" + locale.getLanguage();
    }
    //todo this is autologin after reset pass token click, uncomment later
/*    final Authentication
        auth = new UsernamePasswordAuthenticationToken(passToken.getUser(), null,
        myUserDetailsService.loadUserByUsername(passToken.getUser().getUsername())
            .getAuthorities());
    SecurityContextHolder.getContext().setAuthentication(auth);*/
    User user = passToken.getUser();
    String rawPassword = passwordService.generatePassword();
    user.setPassword(passwordService.getEncodedPassword(rawPassword));
    userService.saveRegisteredUser(user);
    passwordResetService.deleteResetToken(passToken);
    //userPasswordService.changeUserPassword(user,newPassword);
    eventPublisher.publishEvent(new UserResetPasswordEvent(
        RegistrationEvent.builder()
            .user(user).password(rawPassword).token(token)
            .serverUrl(props.getServerUrl())
            .locale(request.getLocale()).build()));
    redirectAttrs.addFlashAttribute("message", "new password was sent to your email");
    return "redirect:/";//?lang=" + locale.getLanguage();
  }
}