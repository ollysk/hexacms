package net.ollysk.pr.model;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RequestLog {

  private long id;
  private long userId;
  private LocalDateTime created;
  private String request;
  private String ip;
  private long trackingId;
}
