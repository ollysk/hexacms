package net.ollysk.pr.persistance.model;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

@Builder
@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Indexed
@Table(name = "node",
    indexes = {
        @Index(name = "idDesc", columnList = "id DESC")
    })
public class NodeJpa {
  @Id
  //@GeneratedValue(strategy = GenerationType.IDENTITY)
  //@Column(name = "page_id")
  private long id;

  @Field
  @Lob
  private String body;

  @Lob
  private String teaser;

  @OneToOne(fetch = FetchType.LAZY, optional = false, cascade = {
      CascadeType.PERSIST,
      CascadeType.MERGE
  })
  @JoinColumn(name = "id", nullable = false)
  @ToString.Exclude
  private NodeMetaJpa nodeMeta;

  @ManyToMany(fetch = FetchType.LAZY, cascade = {
/*            CascadeType.PERSIST,
            CascadeType.MERGE*/
      CascadeType.REFRESH
  })
  @JoinTable(name = "node_category",
      joinColumns = @JoinColumn(name = "node_id"),
      inverseJoinColumns = @JoinColumn(name = "category_id")
  )
  @ToString.Exclude
  private Set<CategoryJpa> categories;

}
