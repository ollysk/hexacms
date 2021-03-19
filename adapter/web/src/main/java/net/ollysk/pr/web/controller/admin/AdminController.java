package net.ollysk.pr.web.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/api/admin")
public class AdminController {

  @GetMapping("")
  public String showIndexPage(Model model) {
    return "admin/nodeAdmin";
  }
}
