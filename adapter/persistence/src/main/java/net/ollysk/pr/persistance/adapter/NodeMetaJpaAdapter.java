package net.ollysk.pr.persistance.adapter;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import net.ollysk.pr.model.NodeMeta;
import net.ollysk.pr.persistance.dao.NodeMetaJpaRepo;
import net.ollysk.pr.persistance.mapper.NodeMetaMapper;
import net.ollysk.pr.persistance.model.NodeMetaJpa;
import net.ollysk.pr.port.out.NodeMetaRepo;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class NodeMetaJpaAdapter implements NodeMetaRepo {

  private final NodeMetaJpaRepo nodeMetaJpaRepo;
  private final NodeMetaMapper nodeMetaMapper;

  @Override public NodeMeta save(NodeMeta nodeMeta) {
    NodeMetaJpa nodeMetaJpa = nodeMetaMapper.toNodeMetaJpa(nodeMeta);
    return nodeMetaMapper.toNodeMeta(nodeMetaJpaRepo.save(nodeMetaJpa));
  }

  @Override public Optional<NodeMeta> findById(Long id) {
    return nodeMetaJpaRepo.findById(id).map(nodeMetaMapper::toNodeMeta);
  }
}
