package net.ollysk.pr.port.in;

import java.util.List;
import net.ollysk.pr.model.Category;

public interface CategoryService {

  List<Category> findCountries();

  List<Category> findAll();
}
