package net.ollysk.pr.web.mapper;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.ollysk.pr.model.Category;
import net.ollysk.pr.model.Node;
import net.ollysk.pr.web.model.NodeWeb;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface NodeWebMapper {

  @Mapping(target = "categories", expression = "java(mapMergeCategoryIds(nodeWeb))")
  @Mapping(target = "countries", expression = "java(mapCountryIds(nodeWeb))")
  Node toNode(NodeWeb nodeWeb);

  @Mapping(target = "categories", expression = "java(mapMergeCategory(node))")
  @Mapping(target = "countries", expression = "java(mapCountry(node))")
  NodeWeb toNodeWeb(Node node);

  default Set<Integer> mapMergeCategory(Node node) {
    return node.getCategories().stream()
        //.flatMap(Collection::stream)
        .map(Category::getId)
        .collect(Collectors.toSet());
  }

  default Set<Integer> mapCountry(Node node) {
    return Collections.emptySet();
  }

  default Set<Category> mapMergeCategoryIds(NodeWeb nodeWeb) {
    return Stream.of(nodeWeb.getCategories(), nodeWeb.getCountries())
        .flatMap(Collection::stream)
        .map(id -> Category.builder().id(id).build())
        .collect(Collectors.toSet());
  }

  default Set<Category> mapCountryIds(NodeWeb nodeWeb) {
    return nodeWeb.getCountries().stream().map(id -> Category.builder().id(id).build())
        .collect(Collectors.toSet());
  }
}
