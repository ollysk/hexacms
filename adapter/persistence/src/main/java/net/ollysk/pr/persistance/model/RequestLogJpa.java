package net.ollysk.pr.persistance.model;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "request_log",
    indexes = {
        @Index(name = "trackingId", columnList = "trackingId")
    })
public class RequestLogJpa {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private long userId;
  private LocalDateTime created;
  private String request;
  private String ip;
  private long trackingId;
}
