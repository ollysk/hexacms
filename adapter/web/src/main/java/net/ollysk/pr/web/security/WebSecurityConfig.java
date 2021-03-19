package net.ollysk.pr.web.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@RequiredArgsConstructor
@Configuration
public class WebSecurityConfig {

  private final MyUserDetailsService myUserDetailsService;
  private final PasswordEncoderAdapter passwordEncoder;

  @Bean
  public static AuthenticationFailureHandler authenticationFailureHandler() {
    return new CustomAuthenticationFailureHandler();
  }

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(myUserDetailsService).passwordEncoder(passwordEncoder);
  }

  @Bean
  public BCryptPasswordEncoder passwordEncoder2() {
    return new BCryptPasswordEncoder();
  }

  @Configuration
  @Order(1)
  public static class ApiWebSecurityConfigAdapter extends WebSecurityConfigurerAdapter {
    @Override protected void configure(HttpSecurity http) throws Exception {
      //https://www.baeldung.com/spring-security-cors-preflight
      http.antMatcher("/api/**")
          .authorizeRequests()
          .antMatchers("/api/admin/**")
          .authenticated()
          .antMatchers("/api/**").permitAll()
          .and()
          .httpBasic()
          .and().csrf().disable()
          .cors();
    }
  }

  @Configuration
  public static class FormLoginWebSecurityConfigAdapter extends WebSecurityConfigurerAdapter {

    @Override protected void configure(HttpSecurity http) throws Exception {
      http
          .authorizeRequests()
          // .antMatchers( "/favicon.ico").permitAll()
          .antMatchers("/static/**").permitAll()
          .antMatchers("/admin/**").hasRole("ADMIN")
          //.antMatchers("/admin/**").access("hasRole('ADMIN')")
          .antMatchers("/add/**").authenticated()
          .antMatchers("/**").permitAll()
          .and().csrf().ignoringAntMatchers("/h234-console/**")
          .and().headers().frameOptions().sameOrigin()
          .and()
          .formLogin().loginPage("/user")
          //.defaultSuccessUrl("/homepage.html")
          //.loginProcessingUrl("/user")
          .failureHandler(authenticationFailureHandler())
          //.failureHandler()
          //.failureUrl("/user?error=true")
          .permitAll()
          .and()
          .logout()
          .logoutRequestMatcher(
              new AntPathRequestMatcher("/logout"))
          .logoutSuccessUrl("/");
    }
  }
}



