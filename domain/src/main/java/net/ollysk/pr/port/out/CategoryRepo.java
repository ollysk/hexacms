package net.ollysk.pr.port.out;

import java.util.List;
import net.ollysk.pr.model.Category;

public interface CategoryRepo {
  List<Category> findCountries();

  List<Category> findAll();
}
