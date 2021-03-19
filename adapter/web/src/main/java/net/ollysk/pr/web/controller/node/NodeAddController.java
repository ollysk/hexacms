package net.ollysk.pr.web.controller.node;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import net.ollysk.pr.model.Category;
import net.ollysk.pr.model.Node;
import net.ollysk.pr.model.NodeMeta;
import net.ollysk.pr.port.in.CategoryService;
import net.ollysk.pr.port.in.NodeService;
import net.ollysk.pr.web.mapper.NodeWebMapper;
import net.ollysk.pr.web.model.NodeWeb;
import net.ollysk.pr.web.security.UserSecurity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class NodeAddController {

  private final CategoryService categoryService;
  private final NodeService nodeService;
  private final NodeWebMapper nodeWebMapper;

  @GetMapping("/add/pr")
  public String showAddPage(Model model) {
    List<Category> categories = categoryService.findAll();
    List<Category> countries = categoryService.findCountries();
    model.addAttribute("categories", categories);
    model.addAttribute("countries", countries);
    return "add";
  }

  private String getClientIp(HttpServletRequest request) {
    final String xfHeader = request.getHeader("X-Forwarded-For");
    if (xfHeader != null) {
      return xfHeader.split(",")[0];
    }
    return request.getRemoteAddr();
  }

  @PostMapping("/add/pr")
  public String addNode(NodeWeb nodeWeb, @AuthenticationPrincipal UserSecurity userSecurity,
      final HttpServletRequest request) {
    Node node = nodeWebMapper.toNode(nodeWeb);
    node.setNodeMeta(NodeMeta.builder().title(nodeWeb.getTitle()).build());
    node = nodeService.addNode(node, userSecurity.getId(), getClientIp(request));
    return "redirect:/" + node.getNodeMeta().getPath();
  }
}
