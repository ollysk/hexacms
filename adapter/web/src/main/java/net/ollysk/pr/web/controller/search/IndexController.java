package net.ollysk.pr.web.controller.search;

import lombok.RequiredArgsConstructor;
import net.ollysk.pr.port.in.SearchService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
class IndexController {
  private final SearchService searchService;

  @GetMapping("/reindex")
  public String reindex() {
    searchService.reindex();
    return "redirect:/";
  }
}
