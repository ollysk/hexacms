package net.ollysk.pr.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import net.ollysk.pr.model.NodeMeta;
import net.ollysk.pr.port.in.NodeMetaService;
import net.ollysk.pr.port.out.NodeMetaRepo;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NodeMetaServiceImpl implements NodeMetaService {

  private final NodeMetaRepo nodeMetaRepo;

  @Override public NodeMeta save(NodeMeta nodeMeta) {
    return nodeMetaRepo.save(nodeMeta);
  }

  @Override public Optional<NodeMeta> findById(Long id) {
    return nodeMetaRepo.findById(id);
  }
}
