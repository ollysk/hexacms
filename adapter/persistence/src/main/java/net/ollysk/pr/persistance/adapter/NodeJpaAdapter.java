package net.ollysk.pr.persistance.adapter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import net.ollysk.pr.model.Node;
import net.ollysk.pr.persistance.dao.NodeJpaRepo;
import net.ollysk.pr.persistance.mapper.NodeMapper;
import net.ollysk.pr.persistance.model.NodeJpa;
import net.ollysk.pr.port.out.NodeRepo;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class NodeJpaAdapter implements NodeRepo {

  private final NodeJpaRepo nodeJpaRepo;
  private final NodeMapper nodeMapper;

  @Override public Node save(Node node) {
    NodeJpa nodeJpa = nodeMapper.toNodeJpa(node);
    NodeJpa res = nodeJpaRepo.save(nodeJpa);
    return nodeMapper.toNode(res);
  }

  @Override public Optional<Node> findById(Long id) {
    return nodeJpaRepo.findById(id).map(nodeMapper::toNode);
  }

  @Override public long findMaxId() {
    return nodeJpaRepo.findMaxId();
  }

  @Override public List<Node> findByCategory(int id, int page, int size) {
    return nodeJpaRepo.findByCategory(id, PageRequest.of(page, size, Sort.by("id").descending()))
        .stream().map(nodeMapper::toNode).collect(Collectors.toList());
  }

  @Override public List<Node> findAll(int page, int size) {
    List<Long> ids = nodeJpaRepo.findAllIds(PageRequest.of(page, size, Sort.by("id").descending()));
    return nodeJpaRepo.findByIds(ids, PageRequest.of(page, size, Sort.by("id").descending()))
        .stream().map(nodeMapper::toNode).collect(Collectors.toList());
  }
}
