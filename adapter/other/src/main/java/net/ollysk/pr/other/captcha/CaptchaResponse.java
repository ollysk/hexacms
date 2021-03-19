package net.ollysk.pr.other.captcha;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.HashMap;
import java.util.Map;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({"success", "score", "action", "challenge_ts", "hostname", "error-codes"})
public class CaptchaResponse {

  @JsonProperty("success")
  private boolean isSuccess;
  @JsonProperty("challenge_ts")
  private String challengeTs;
  @JsonProperty("hostname")
  private String hostname;
  @JsonProperty("score")
  private float score;
  @JsonProperty("action")
  private String action;
  @JsonProperty("error-codes")
  private ErrorCode[] errorCodes;

  @JsonIgnore
  public boolean hasClientError() {
    final ErrorCode[] errors = getErrorCodes();
    if (errors == null) {
      return false;
    }
    for (final ErrorCode error : errors) {
      switch (error) {
        case INVALID_RESPONSE:
        case MISSING_RESPONSE:
        case BAD_REQUEST:
          return true;
        default:
          break;
      }
    }
    return false;
  }

  enum ErrorCode {
    MISSING_SECRET, INVALID_SECRET, MISSING_RESPONSE, INVALID_RESPONSE, BAD_REQUEST,
    TIMEOUT_OR_DUPLICATE;

    private static final Map<String, ErrorCode> errorsMap = new HashMap<>(6);

    static {
      errorsMap.put("missing-input-secret", MISSING_SECRET);
      errorsMap.put("invalid-input-secret", INVALID_SECRET);
      errorsMap.put("missing-input-response", MISSING_RESPONSE);
      errorsMap.put("bad-request", INVALID_RESPONSE);
      errorsMap.put("invalid-input-response", BAD_REQUEST);
      errorsMap.put("timeout-or-duplicate", TIMEOUT_OR_DUPLICATE);
    }

    @JsonCreator
    public static ErrorCode forValue(final String value) {
      return errorsMap.get(value.toLowerCase());
    }
  }
}
