package net.ollysk.pr.persistance.mapper;

import net.ollysk.pr.model.Category;
import net.ollysk.pr.persistance.model.CategoryJpa;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {

  Category toCategory(CategoryJpa categoryJpa);

  CategoryJpa toCategoryJpa(Category category);
}
