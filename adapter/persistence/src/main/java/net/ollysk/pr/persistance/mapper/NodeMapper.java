package net.ollysk.pr.persistance.mapper;

import net.ollysk.pr.model.Node;
import net.ollysk.pr.persistance.model.NodeJpa;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface NodeMapper {

  Node toNode(NodeJpa nodeJpa);

  NodeJpa toNodeJpa(Node node);
}
