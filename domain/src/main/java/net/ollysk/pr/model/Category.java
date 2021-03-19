package net.ollysk.pr.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Category {

  int id;
  String name;
  String description;
  int weight;
}
