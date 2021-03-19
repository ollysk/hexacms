package net.ollysk.pr.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import net.ollysk.pr.model.Node;
import net.ollysk.pr.model.NodeMeta;
import net.ollysk.pr.port.in.SearchService;
import net.ollysk.pr.port.out.SearchPort;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SearchServiceImpl implements SearchService {

  private final SearchPort searchPort;

  @Override public List<Node> searchNode(String query) {
    return searchPort.searchNode(query);
  }

  @Override public List<NodeMeta> searchMoreLikeThis(NodeMeta nodeMeta) {
    return searchPort.searchMoreLikeThis(nodeMeta);
  }

  @Override public void reindex() {
    searchPort.reindex();
  }
}
