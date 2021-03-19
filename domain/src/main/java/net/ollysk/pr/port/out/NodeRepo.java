package net.ollysk.pr.port.out;

import java.util.List;
import java.util.Optional;
import net.ollysk.pr.model.Node;

public interface NodeRepo {

  Node save(Node node);

  Optional<Node> findById(Long id);

  long findMaxId();

  List<Node> findByCategory(int id, int page, int size);

  List<Node> findAll(int page, int size);
}

