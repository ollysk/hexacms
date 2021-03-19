package net.ollysk.pr.web.model;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NodeWeb {

  //@NotEmpty
  //@Size(min = 100, message = "Size.userWeb.firstName")
  private String title;
  //@Lob
  private String body;
  private Set<Integer> categories;
  private Set<Integer> countries;

  public Set<Integer> getCategories() {
    if (getCountries() != null) {
      categories.addAll(getCountries());
    }
    return categories;
  }
}
