package net.ollysk.pr.persistance.model;

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
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "category",
    indexes = {
        @Index(name = "weight", columnList = "weight")
        //,@Index(name = "bodyHash", columnList = "bodyHash", unique = true)
    })

public class CategoryJpa {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  long id;
  String name;
  String description;
  int weight;
}
