package net.ollysk.pr.web.config;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Configuration
@ConfigurationProperties(prefix = "pr.web")
@Data
@Validated
public class ConfigProperties {

  @Min(value = 1, message = "Should be > 1")
  @Max(value = 100, message = "Should be < 100")
  private int indexPageSize = 20;

  @Min(value = 1, message = "Should be > 1")
  @Max(value = 100, message = "Should be < 100")
  private int categoryPageSize = 20;

  private String serverUrl = "http://localhost:8080/";
}
