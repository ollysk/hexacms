package net.ollysk.pr.port.in;

import java.util.Optional;
import net.ollysk.pr.model.NodeMeta;

public interface NodeMetaService {

  NodeMeta save(NodeMeta nodeMeta);

  Optional<NodeMeta> findById(Long id);
}
