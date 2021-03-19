package net.ollysk.pr.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import net.ollysk.pr.model.Category;
import net.ollysk.pr.port.in.CategoryService;
import net.ollysk.pr.port.out.CategoryRepo;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

  private final CategoryRepo categoryRepo;

  @Override public List<Category> findCountries() {
    return categoryRepo.findCountries();
  }

  @Override public List<Category> findAll() {
    return categoryRepo.findAll();
  }
}
