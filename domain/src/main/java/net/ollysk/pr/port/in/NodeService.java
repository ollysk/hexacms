package net.ollysk.pr.port.in;

import java.util.List;
import java.util.Optional;
import net.ollysk.pr.model.Node;

public interface NodeService {

  Node save(Node node);

  Optional<Node> findById(long id);

  long findMaxId();

  Node addNode(Node node, long userId, final String ip);

  List<Node> findByCategory(int id, int page, int size);

  List<Node> findAll(int page, int size);
}
