package net.ollysk.pr.web.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserWeb {

  @Size(min = 3, message = "{Size.userWeb.lastName}")
  private String username;
  ///@ValidEmail
  private String email;
  private String company;
  private String fullName;
  private String position;
  @Min(value = 1, message = "choose countryId")
  private int countryId;
  private String site;
  private String telephone;
  //@Size(min = 3, message = "{Size.userWeb.lastName}")
  private String word;
}
