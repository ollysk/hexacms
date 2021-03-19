package net.ollysk.pr.persistance.mapper;

import net.ollysk.pr.model.NodeMeta;
import net.ollysk.pr.persistance.model.NodeMetaJpa;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface NodeMetaMapper {

  NodeMeta toNodeMeta(NodeMetaJpa nodeMetaJpa);

  NodeMetaJpa toNodeMetaJpa(NodeMeta nodeMeta);
}
