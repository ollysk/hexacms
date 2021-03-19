package net.ollysk.pr.persistance.adapter;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import net.ollysk.pr.model.Category;
import net.ollysk.pr.persistance.dao.CategoryJpaRepo;
import net.ollysk.pr.persistance.mapper.CategoryMapper;
import net.ollysk.pr.port.out.CategoryRepo;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CategoryJpaAdapter implements CategoryRepo {

  private final CategoryJpaRepo categoryJpaRepo;
  private final CategoryMapper categoryMapper;

  @Override public List<Category> findCountries() {
    return categoryJpaRepo.findCountries().stream().map(categoryMapper::toCategory)
        .collect(Collectors.toList());
  }

  @Override public List<Category> findAll() {
    return categoryJpaRepo.findAllByOrderByWeightAsc().stream().map(categoryMapper::toCategory)
        .collect(Collectors.toList());
  }
}
