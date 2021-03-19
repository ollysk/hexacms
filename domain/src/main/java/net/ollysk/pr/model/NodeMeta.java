package net.ollysk.pr.model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NodeMeta {

  private long id;
  private long userId;
  private String title;
  private boolean isPublished;
  private LocalDateTime created;
  private LocalDateTime changed;
  private String path;
  private String ip;
  private int langId;
}
