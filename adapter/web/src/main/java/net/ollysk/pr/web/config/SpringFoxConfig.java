package net.ollysk.pr.web.config;

import java.util.Collections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SpringFoxConfig {

  @Bean
  public Docket springFox() {
    return new Docket(DocumentationType.SWAGGER_2)
        //.enableUrlTemplating(true) //will allow overloaded methods to show up (springfox only)
        .apiInfo(apiInfo())
        .select()
        //.apis(RequestHandlerSelectors.basePackage("net.ollysk.pr.web.controller.admin"))
        .paths(PathSelectors.ant("/api/**"))
        .build();
  }

  private ApiInfo apiInfo() {
    return new ApiInfo(
        "PR REST API",
        "Some description",
        "0.1",
        "Terms of service",
        new Contact("@ollysk", "https://github.com/ollysk", "ollysk@ollysk.net"),
        "License of API",
        "https://github.com/ollysk",
        Collections.emptyList());
  }
}
