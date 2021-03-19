package net.ollysk.pr.service;

//@SpringBootTest
class NodeServiceTest {

/*    // @Autowired
    NodeService nodeService;
    @Autowired
    NodeJpaRepo nodeRepo;
    @Autowired
    CategoryJpaRepo categoryRepo;
    NodeWeb nodeDto;
    List<NodeWeb> nodeDtos;

    @Autowired
    NodeMapper nodeMapper;

    @Autowired
    private Validator validator;

    @BeforeEach
    void setUp() {
        CategoryJpa category = CategoryJpa.builder()
            .id(1)
            .description("one")
            .name("one").build();
        CategoryJpa category1 = CategoryJpa.builder()
            .id(2)
            .description("two")
            .name("two").build();
        CategoryJpa country = CategoryJpa.builder()
            .id(3)
            .description("Ukraine")
            .name("Ukraine").build();

        Set<CategoryJpa> categories = Set.of(category, category1);
        Set<CategoryJpa> countries = Set.of(country);
        *//*categoryRepo.saveAll(categories);
        categoryRepo.saveAll(countries);*//*
        nodeService = new NodeServiceImpl(nodeRepo, categoryRepo, nodeMapper, null);

        nodeDto = NodeWeb.builder()
            .body("body")
            .categories(categories)
            .countries(countries)
            .title("title").build();

        nodeDtos = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            NodeWeb nodeDto = NodeWeb.builder()
                .body("body-" + i)
                .title("title-" + i)
                .categories(categories)
                .countries(countries)
                .build();
            nodeDtos.add((nodeDto));
        }
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void save() {
        NodeJpa node = nodeService.save(nodeDto);
        Set<ConstraintViolation<NodeWeb>> violations = validator.validate(nodeDto);
        System.out.println("JJJ " + violations);
        assertThat(node.getBody()).isEqualTo("body");
    }

    @Test
    void getNodeById() {
    }
*//*
    @Test
    void getLast() {
        nodeDtos.forEach(n -> nodeService.save(n));
        List<NodeJpa> nodes = nodeService.getLast();
        assertThat(nodes.size()).isEqualTo(15);
    }*/
}