package net.ollysk.pr.web.controller.node;

import java.util.List;
import lombok.RequiredArgsConstructor;
import net.ollysk.pr.model.Node;
import net.ollysk.pr.model.NodeMeta;
import net.ollysk.pr.port.in.CategoryService;
import net.ollysk.pr.port.in.NodeMetaService;
import net.ollysk.pr.port.in.NodeService;
import net.ollysk.pr.port.in.SearchService;
import net.ollysk.pr.web.config.ConfigProperties;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class NodeController {

  private final NodeService nodeService;
  private final NodeMetaService nodeMetaService;
  private final CategoryService categoryService;
  private final SearchService searchService;
  private final ConfigProperties props;

  @GetMapping("/")
  public String showIndexPage(Model model) {
    model.addAttribute("nodes", nodeService.findAll(0, props.getIndexPageSize()));
    model.addAttribute("allCategories", categoryService.findAll());
    return "index";
  }

  private List<NodeMeta> similar(long id) {
    NodeMeta nodeMeta = nodeMetaService.findById(id).orElse(NodeMeta.builder().build());
    return searchService.searchMoreLikeThis(nodeMeta);
  }

  private String cleanHtml(String bodyHtml) {
    return Jsoup.clean(bodyHtml, Whitelist.basic());
  }

  private String nl2br(String text) {
    return text.replaceAll("(\r\n|\n)", "<br>");
  }

  @GetMapping(path = {"/{numericId:[\\d]+}/*/*/{id}",
      "/{numericId:[\\d]+}/*/*/*/{id}"})
  public String showNodePage(Model model, @PathVariable("id") long id) {
    Node node = nodeService.findById(id).orElse(Node.builder().body("404.NOT FOUND")
        .nodeMeta(NodeMeta.builder().build())
        .build());
    node.setBody(cleanHtml(nl2br(node.getBody())));
    model.addAttribute("node", node);
    model.addAttribute("similar", similar(id));
    model.addAttribute("categories", node.getCategories());
    model.addAttribute("allCategories", categoryService.findAll());
    return "node";
  }

  @GetMapping(path = {"/category/{categoryId}"})
  public String showCategoryPage(Model model, @PathVariable("categoryId") int categoryId) {
    List<Node> nodes = nodeService.findByCategory(categoryId, 0, props.getCategoryPageSize());
    model.addAttribute("nodes", nodes);
    model.addAttribute("allCategories", categoryService.findAll());
    return "index";
  }
}
