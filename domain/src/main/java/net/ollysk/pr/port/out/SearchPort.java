package net.ollysk.pr.port.out;

import java.util.List;
import net.ollysk.pr.model.Node;
import net.ollysk.pr.model.NodeMeta;

public interface SearchPort {

  List<Node> searchNode(String query);

  List<NodeMeta> searchMoreLikeThis(NodeMeta nodeMeta);

  void reindex();
}
