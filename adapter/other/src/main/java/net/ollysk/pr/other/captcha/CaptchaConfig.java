package net.ollysk.pr.other.captcha;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

@Configuration
public class CaptchaConfig {
  @Bean
  public ClientHttpRequestFactory clientHttpRequestFactory() {
    SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
    //OkHttp3ClientHttpRequestFactory factory = new OkHttp3ClientHttpRequestFactory();
    factory.setConnectTimeout(3 * 1000);
    factory.setReadTimeout(7 * 1000);
    return factory;
  }

  @Bean
  public RestOperations restTemplate() {
    return new RestTemplate(this.clientHttpRequestFactory());
  }
}
