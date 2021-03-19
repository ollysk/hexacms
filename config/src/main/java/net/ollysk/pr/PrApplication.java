package net.ollysk.pr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

//@ComponentScan("net.ollysk.pr")
@EnableAsync
@SpringBootApplication
public class PrApplication {

  public static void main(String[] args) {
    SpringApplication.run(PrApplication.class, args);
  }
}
