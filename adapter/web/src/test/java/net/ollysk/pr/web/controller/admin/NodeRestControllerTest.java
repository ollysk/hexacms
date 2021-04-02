package net.ollysk.pr.web.controller.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.json.JsonMergePatch;
import javax.servlet.http.HttpServletRequest;
import net.ollysk.pr.model.Category;
import net.ollysk.pr.model.Node;
import net.ollysk.pr.model.NodeMeta;
import net.ollysk.pr.port.in.CategoryService;
import net.ollysk.pr.port.in.NodeMetaService;
import net.ollysk.pr.port.in.NodeService;
import net.ollysk.pr.port.in.SearchService;
import net.ollysk.pr.web.config.ConfigProperties;
import net.ollysk.pr.web.mapper.NodeWebMapper;
import net.ollysk.pr.web.model.NodeWeb;
import net.ollysk.pr.web.security.UserSecurity;
import net.ollysk.pr.web.service.RestPatchService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = NodeRestController.class, excludeAutoConfiguration = {
    SecurityAutoConfiguration.class})
class NodeRestControllerTest {

  private int page;
  private int size;
  private long id;
  private NodeMeta nodeMeta;
  private Node node;
  private List<Node> nodes;
  private final String serverUrl = "http://localhost:8080/";
  @Autowired private MockMvc mockMvc;
  @MockBean private NodeMetaService nodeMetaService;
  @MockBean private RestPatchService restPatchService;
  @MockBean private JsonMergePatch patch;
  @MockBean private NodeService nodeService;
  @MockBean private CategoryService categoryService;
  @MockBean private SearchService searchService;
  @MockBean private NodeWebMapper nodeWebMapper;
  @MockBean private HttpServletRequest request;
  @MockBean private UserSecurity userSecurity;
  @MockBean private ConfigProperties props;

  @BeforeEach
  void setUp() {
    id = 303971;
    nodeMeta = getNodeMeta(id);
    node = getNode(id);
    nodes = new ArrayList<>();
    for (int i = 0; i < 100; i++) {
      nodes.add(getNode(i));
    }
    page = 0;
    size = 10;
  }

  private NodeMeta getNodeMeta(long id) {
    return NodeMeta.builder().id(id).title("title-" + id)
        .isPublished(true).path("/" + id).build();
  }

  private String getUrl(String url) {
    return "/api/admin" + url;
  }

  private Node getNode(long id) {
    return Node.builder()
        .id(id)
        .nodeMeta(getNodeMeta(id))
        .body("body-" + id)
        .categories(Set.of(Category.builder().id(30).build()))
        .build();
  }

  @AfterEach
  void tearDown() {
  }

  @Test
  @WithMockUser(username = "1", password = "1", roles = "ADMIN")
  void getSimilar() throws Exception {
    given(nodeMetaService.findById(id)).willReturn(Optional.of(nodeMeta));
    given(searchService.searchMoreLikeThis(nodeMeta)).willReturn(List.of(nodeMeta));

    mockMvc.perform(get(getUrl("/nodes/{id}/similar"),
        id)
        .header("Content-Type", "application/json"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$[0].title").value(nodeMeta.getTitle()));
  }

  @Test
  @WithMockUser(username = "1", password = "1", roles = "ADMIN")
  void getByCategory() throws Exception {
    int categoryId = 35;

    given(nodeService.findByCategory(categoryId, page, size))
        .willReturn(nodes);

    mockMvc.perform(get(getUrl("/nodes"),
        id)
        .param("categoryId", String.valueOf(categoryId))
        .param("size", String.valueOf(size))
        .header("Content-Type", "application/json"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$[0].body").value(nodes.get(0).getBody()));
  }

  @Test
  @WithMockUser(username = "1", password = "1", roles = "ADMIN")
  void getAll() throws Exception {

    given(nodeService.findAll(page, size))
        .willReturn(nodes);
    mockMvc.perform(get(getUrl("/nodes"),
        id)
        .param("size", String.valueOf(size))
        .header("Content-Type", "application/json"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$[0].body").value(nodes.get(0).getBody()));
  }

  @Test
  void getById() throws Exception {
    given(nodeService.findById(id)).willReturn(Optional.of(node));

    mockMvc.perform(get(getUrl("/nodes/{id}"),
        id)
        .header("Content-Type", "application/json"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.body").value(node.getBody()));
  }

  @Test
  void addNode() throws Exception {
    NodeWeb nodeWeb = NodeWeb.builder()
        .title(node.getNodeMeta().getTitle()).body(node.getBody())
        .categories(Set.of(30))
        .build();

    given(nodeWebMapper.toNode(nodeWeb)).willReturn(node);

    given(nodeService.addNode(node, 0, "127.0.0.1"))
        .willReturn(node);

    given(props.getServerUrl()).willReturn(serverUrl);

    mockMvc.perform(post(getUrl("/nodes"))
        .content(new ObjectMapper().writeValueAsString(nodeWeb))
        .header("Content-Type", "application/json"))
        .andExpect(status().isCreated())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.body").value(node.getBody()));
  }

  //@Test
  @WithMockUser(username = "1", password = "1", roles = "ADMIN")
  void updateNodeMeta() throws Exception {

    LocalDateTime time = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
    given(nodeMetaService.findById(id)).willReturn(Optional.of(nodeMeta));
    nodeMeta.setChanged(time);
    nodeMeta.setIsPublished(false);
    given(restPatchService.mergePatch(patch, nodeMeta, NodeMeta.class))
        .willReturn(nodeMeta);

    ObjectMapper mapper = new ObjectMapper();
    HashMap<String, Object> updates = new HashMap<>();
    updates.put("isPublished", false);

    mockMvc.perform(patch(getUrl("/nodes/{id}"),
        id)
        .header("Content-Type", "application/merge-patch+json")
        .content(mapper.writeValueAsString(updates)))
        .andExpect(status().isNoContent());

    then(nodeMetaService).should().save(eq(nodeMeta));
  }
}