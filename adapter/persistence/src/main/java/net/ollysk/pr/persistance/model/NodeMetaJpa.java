package net.ollysk.pr.persistance.model;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.TermVector;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Indexed
@Table(name = "node_meta",
    indexes = {
        @Index(name = "idDesc", columnList = "id DESC")
    })
public class NodeMetaJpa {
  @Id
  //@GeneratedValue(strategy = GenerationType.AUTO)
  //@Column(name = "page_id")
  private long id;

  private long userId;
  @Field(termVector = TermVector.YES)
  private String title;

  @Column(columnDefinition = "boolean default true")
  private boolean isPublished;
  private LocalDateTime created;
  private LocalDateTime changed;
  private String path;
  private String ip;
  private int langId;
}
