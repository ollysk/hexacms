package net.ollysk.pr.persistance.search;

import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.ollysk.pr.model.Node;
import net.ollysk.pr.model.NodeMeta;
import net.ollysk.pr.persistance.mapper.NodeMapper;
import net.ollysk.pr.persistance.mapper.NodeMetaMapper;
import net.ollysk.pr.persistance.model.NodeJpa;
import net.ollysk.pr.persistance.model.NodeMetaJpa;
import net.ollysk.pr.port.out.SearchPort;
import org.apache.lucene.search.Query;
import org.hibernate.search.engine.ProjectionConstants;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.stereotype.Repository;

@Slf4j
@RequiredArgsConstructor
@Repository
public class SearchAdapter implements SearchPort {

  private final NodeMetaMapper nodeMetaMapper;
  private final NodeMapper nodeMapper;
  @PersistenceContext
  private EntityManager entityManager;

  @Transactional
  public void reindex() {
    FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
    try {
      fullTextEntityManager.createIndexer().startAndWait();
    } catch (InterruptedException e) {
      log.error("{ }", e);
    }
  }

  public List<NodeJpa> searchProductNameByKeywordQuery(String text) {
    Query keywordQuery = getQueryBuilder()
        .keyword()
        .onField("title")
        .matching(text)
        .createQuery();
    return getJpaQuery(keywordQuery).setMaxResults(20).getResultList();
  }

  public List<Node> searchNode(String text) {
    FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
    Query keywordQuery = fullTextEntityManager.getSearchFactory()
        .buildQueryBuilder()
        .forEntity(NodeJpa.class)
        .get()
        .phrase()
        .withSlop(1)
        .onField("body")
        .sentence(text)
        .createQuery();

    //FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);

    Query phraseQuery = getQueryBuilder()
        .phrase()
        .withSlop(1)
        .onField("description")
        .sentence(text)
        .createQuery();

    FullTextQuery fullTextQuery =
        fullTextEntityManager.createFullTextQuery(keywordQuery, NodeJpa.class);
    List<NodeJpa> results = fullTextQuery.setMaxResults(20).getResultList();

    return results.stream().map(nodeMapper::toNode).collect(Collectors.toList());
  }

  public List<NodeJpa> searchProductNameByFuzzyQuery(String text) {
    Query fuzzyQuery = getQueryBuilder()
        .keyword()
        .fuzzy()
        .withEditDistanceUpTo(2)
        .withPrefixLength(0)
        .onField("title")
        .matching(text)
        .createQuery();
    return getJpaQuery(fuzzyQuery).getResultList();
  }

  public List<NodeJpa> searchByWildcardQuery(String text) {
    Query wildcardQuery = getQueryBuilder()
        .keyword()
        .wildcard()
        .onField("title")
        .matching(text)
        .createQuery();
    return getJpaQuery(wildcardQuery).getResultList();
  }

  public List<NodeJpa> searchDescriptionByPhraseQuery(String text) {
    Query phraseQuery = getQueryBuilder()
        .phrase()
        .withSlop(1)
        .onField("description")
        .sentence(text)
        .createQuery();
    return getJpaQuery(phraseQuery).getResultList();
  }

  public List<NodeJpa> searchNameAndDescription(String text) {
    Query simpleQueryStringQuery = getQueryBuilder()
        .simpleQueryString()
        .onFields("title", "description")
        .matching(text)
        .createQuery();
    return getJpaQuery(simpleQueryStringQuery).getResultList();
  }

  public List<NodeJpa> searchNameByRangeQuery(int low, int high) {
    Query rangeQuery = getQueryBuilder()
        .range()
        .onField("memory")
        .from(low)
        .to(high)
        .createQuery();
    return getJpaQuery(rangeQuery).getResultList();
  }

  public List<Object[]> searchProductNameByMoreLikeThisQuery0(NodeMetaJpa entity) {

    Query moreLikeThisQuery = getQueryBuilder()
        .moreLikeThis()
        .comparingField("title")
        .toEntity(entity)
        .createQuery();
    return getJpaQuery(moreLikeThisQuery)
        .setProjection(ProjectionConstants.THIS, ProjectionConstants.SCORE)
        .setMaxResults(20)
        .getResultList();
  }

  public List<NodeMeta> searchMoreLikeThis(NodeMeta entity) {
    Query moreLikeThisQuery = getQueryBuilder()
        .moreLikeThis()
        .comparingField("title")
        .toEntity(nodeMetaMapper.toNodeMetaJpa(entity))
        .createQuery();

    FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
    FullTextQuery fullTextQuery =
        fullTextEntityManager.createFullTextQuery(moreLikeThisQuery, NodeMetaJpa.class);

    @SuppressWarnings("unchecked")
    List<Object[]> results = fullTextQuery
        .setProjection(ProjectionConstants.THIS, ProjectionConstants.SCORE)
        .setMaxResults(20)
        .getResultList();

    return results.stream()
        .map(s -> nodeMetaMapper.toNodeMeta((NodeMetaJpa) s[0]))
        .filter(nodeMeta -> nodeMeta != null && nodeMeta.getId() != entity.getId())
        .collect(Collectors.toList());
  }

  public List<NodeJpa> searchNameAndDescriptionByKeywordQuery(String text) {

    Query keywordQuery = getQueryBuilder()
        .keyword()
        .onFields("title", "description")
        .matching(text)
        .createQuery();
    return getJpaQuery(keywordQuery).getResultList();
  }

  public List<NodeJpa> searchNameAndDescriptionByCombinedQuery(String manufactorer,
      int memoryLow, int memoryTop, String extraFeature, String exclude) {
    Query combinedQuery = getQueryBuilder()
        .bool()
        .must(getQueryBuilder().keyword()
            .onField("title")
            .matching(manufactorer)
            .createQuery())
        .must(getQueryBuilder()
            .range()
            .onField("memory")
            .from(memoryLow)
            .to(memoryTop)
            .createQuery())
        .should(getQueryBuilder()
            .phrase()
            .onField("description")
            .sentence(extraFeature)
            .createQuery())
        .must(getQueryBuilder()
            .keyword()
            .onField("title")
            .matching(exclude)
            .createQuery())
        .not()
        .createQuery();

    return getJpaQuery(combinedQuery).getResultList();
  }

  private FullTextQuery getJpaQuery(org.apache.lucene.search.Query luceneQuery) {
    FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
    return fullTextEntityManager.createFullTextQuery(luceneQuery, NodeMetaJpa.class);
  }

  private QueryBuilder getQueryBuilder() {
    FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);

    return fullTextEntityManager.getSearchFactory()
        .buildQueryBuilder()
        .forEntity(NodeMetaJpa.class)
        .get();
  }
}
