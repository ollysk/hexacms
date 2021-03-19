package net.ollysk.pr.persistance.dao;

import java.util.List;
import net.ollysk.pr.persistance.model.NodeJpa;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NodeJpaRepo extends JpaRepository<NodeJpa, Long> {

  @Query("select max(t.id) from NodeJpa t")
  long findMaxId();

  @Query("select b from NodeJpa b inner join b.categories c where c.id = :id and b.nodeMeta.isPublished = true")
  List<NodeJpa> findByCategory(@Param("id") long id, Pageable pageable);

  @Query("select id from NodeMetaJpa n where n.isPublished = true")
  List<Long> findAllIds(Pageable pageable);

  @EntityGraph(attributePaths = {"nodeMeta", "categories"})
  @Query("select n from NodeJpa n where n.id in (:ids)")
  List<NodeJpa> findByIds(@Param("ids") List<Long> ids, Pageable pageable);
}
