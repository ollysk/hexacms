package net.ollysk.pr.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserCountry {

  UKRAINE(1), RUSSIA(2), BELARUS(3);
  private final int id;

/*  UserCountry(int id) {
    this.id = id;
  }*/
}

