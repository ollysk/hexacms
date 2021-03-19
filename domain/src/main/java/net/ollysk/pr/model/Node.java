package net.ollysk.pr.model;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Node {

  private long id;
  private String body;
  private String teaser;
  private NodeMeta nodeMeta;
  private Set<Category> countries;
  private Set<Category> categories;
}
