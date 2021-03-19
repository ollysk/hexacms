package net.ollysk.pr.port.out;

import java.util.Optional;
import net.ollysk.pr.model.NodeMeta;

public interface NodeMetaRepo {

  NodeMeta save(NodeMeta nodeMeta);

  Optional<NodeMeta> findById(Long id);
}

