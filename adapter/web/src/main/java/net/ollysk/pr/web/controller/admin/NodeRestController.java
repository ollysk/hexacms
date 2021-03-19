package net.ollysk.pr.web.controller.admin;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.json.JsonMergePatch;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import net.ollysk.pr.model.Node;
import net.ollysk.pr.model.NodeMeta;
import net.ollysk.pr.port.in.NodeMetaService;
import net.ollysk.pr.port.in.NodeService;
import net.ollysk.pr.port.in.SearchService;
import net.ollysk.pr.web.mapper.NodeWebMapper;
import net.ollysk.pr.web.model.NodeWeb;
import net.ollysk.pr.web.security.UserSecurity;
import net.ollysk.pr.web.service.RestPatchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/admin")
public class NodeRestController {

  private final NodeService nodeService;
  private final NodeMetaService nodeMetaService;
  private final SearchService searchService;

  private final NodeWebMapper nodeWebMapper;
  private final RestPatchService restPatchService;

  @CrossOrigin
  @GetMapping(path = {"/nodes/{id:[\\d]+}/similar"})
  public ResponseEntity<List<NodeMeta>> getSimilar(@PathVariable("id") long id,
      @RequestParam(required = false) Map<String, String> params) {
    return nodeMetaService.findById(id)
        .map(searchService::searchMoreLikeThis)
        .map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @CrossOrigin
  @GetMapping(path = {"/nodes"})
  @ApiOperation(value = "Get all nodes", notes = "This method gets all nodes")
  public ResponseEntity<List<Node>> getAll(@ApiParam(
      name = "params", type = "Map<String, String>", value = "Map of parameters", example = "page, size"
  ) @RequestParam(required = false) Map<String, String> params) {
    int page = Integer.parseInt(params.getOrDefault("page", "0"));
    int size = Integer.parseInt(params.getOrDefault("size", "10"));
    List<Node> nodes = nodeService.findAll(page, size);
    return ResponseEntity.ok(nodes);
  }

  @CrossOrigin
  @GetMapping(path = {"/nodes"}, params = "categoryId")
  public ResponseEntity<List<Node>> getByCategory(
      @RequestParam(defaultValue = "0") int categoryId,
      @RequestParam(required = false) Map<String, String> params) {
    int page = Integer.parseInt(params.getOrDefault("page", "0"));
    int size = Integer.parseInt(params.getOrDefault("size", "10"));
    List<Node> nodes = nodeService.findByCategory(categoryId, page, size);
    return ResponseEntity.ok(nodes);
  }

  @CrossOrigin
  @GetMapping(path = {"/nodes/{id:[\\d]+}"})
  public ResponseEntity<Node> getById(@PathVariable("id") long id) {
    Optional<Node> node = nodeService.findById(id)
        .stream()
        .findFirst();
    return node.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  private String getClientIp(HttpServletRequest request) {
    final String xfHeader = request.getHeader("X-Forwarded-For");
    if (xfHeader != null) {
      return xfHeader.split(",")[0];
    }
    return request.getRemoteAddr();
  }

  @CrossOrigin(allowedHeaders = "*")
  @RequestMapping(value = "/nodes", method = {RequestMethod.POST,
      RequestMethod.PUT})
  public ResponseEntity<Node> addNode(@RequestBody NodeWeb nodeWeb,
      @AuthenticationPrincipal UserSecurity userSecurity,
      final HttpServletRequest request) {
    Node node =
        nodeService.addNode(nodeWebMapper.toNode(nodeWeb),
            userSecurity != null ? userSecurity.getId() : 0,
            getClientIp(request));

    URI location = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/").
        path(node.getNodeMeta().getPath())
        .buildAndExpand(node.getId()).toUri();
    return ResponseEntity.created(location).body(node);
  }

  @CrossOrigin //(origins = "*",allowedHeaders = "*")
  @PatchMapping(path = "/nodes/{id:[\\d]+}", consumes = "application/merge-patch+json")
  public ResponseEntity<Void> updateNodeMeta(@PathVariable long id,
      @RequestBody JsonMergePatch patch) {
    NodeMeta nodeMeta = nodeMetaService.findById(id).orElseThrow();
    nodeMeta.setChanged(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
    nodeMeta = restPatchService.mergePatch(patch, nodeMeta, NodeMeta.class);
    try {
      nodeMetaService.save(nodeMeta);
      return ResponseEntity.noContent().build();
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }
}