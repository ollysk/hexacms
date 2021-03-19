package net.ollysk.pr.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import net.ollysk.pr.model.Category;
import net.ollysk.pr.model.Node;
import net.ollysk.pr.model.NodeMeta;
import net.ollysk.pr.port.in.NodeService;
import net.ollysk.pr.port.out.CategoryRepo;
import net.ollysk.pr.port.out.NodeRepo;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NodeServiceImpl implements NodeService {

  private final NodeRepo nodeRepo;
  private final CategoryRepo categoryRepo;
  private final DetectLang langDetector;

  @Override
  public Node save(Node node) {
    return nodeRepo.save(node);
  }

  @Override
  public Optional<Node> findById(long id) {
    return nodeRepo.findById(id);
  }

  @Override public long findMaxId() {
    return nodeRepo.findMaxId();
  }

  @Override
  public Node addNode(Node node, long userId, final String ip) {
    long id = findMaxId() + 1;
    node.setId(id);
    String path = getCleanUrl(id);
    node.setTeaser(getTeaser(node.getBody(), 100, 300));
    Set<Category> cats = node.getCategories();
    node.setCategories(cats);
    NodeMeta nodeMeta = NodeMeta.builder()
        .created(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
        .isPublished(true)
        .title(node.getNodeMeta().getTitle()).id(node.getId())
        .langId(langDetector.getLangId(node.getBody()))
        .userId(userId)
        .path(path)
        .ip(ip)
        .build();
    node.setNodeMeta(nodeMeta);
    return nodeRepo.save(node);
  }

  @Override public List<Node> findByCategory(int id, int page, int size) {
    return nodeRepo.findByCategory(id, page, size);
  }

  @Override public List<Node> findAll(int page, int size) {
    return nodeRepo.findAll(page, size);
  }

  private String stripHtml(String bodyHtml) {
    return Jsoup.parse(bodyHtml).text();
  }

  private String getCleanUrl(long id) {
    ZoneId zone = ZoneId.of("+3");
    DateTimeFormatter df = DateTimeFormatter.ofPattern("yy/MM/dd").withZone(zone);
    return df.format(Instant.now()) + "/" + id;
  }

  private String getTeaser(String text, int min, int max) {
    String[] sentences = stripHtml(text).split("\\.");
    StringBuilder res = new StringBuilder();
    int length = 0;
    for (String sentence : sentences) {
      length += sentence.length();
      if (length < max) {
        res.append(sentence).append(".");
      } else {
        break;
      }
    }
    return res.toString();
  }
}
