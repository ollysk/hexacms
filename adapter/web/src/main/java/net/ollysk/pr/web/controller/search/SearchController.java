package net.ollysk.pr.web.controller.search;

import java.util.List;
import lombok.RequiredArgsConstructor;
import net.ollysk.pr.model.Node;
import net.ollysk.pr.port.in.SearchService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/search")
public class SearchController {

  private final SearchService searchService;

  @GetMapping(path = {"/node"})
  public String searchNode(Model model, @RequestParam("query") String query) {
    List<Node> nodes = searchService.searchNode(query);
    model.addAttribute("nodes", nodes);
    return "index";
  }
}
