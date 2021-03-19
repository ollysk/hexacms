package net.ollysk.pr.persistance.dao;

import java.util.List;
import net.ollysk.pr.persistance.model.CategoryJpa;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryJpaRepo extends JpaRepository<CategoryJpa, Integer> {
  @Query("select t from CategoryJpa t where t.id>=45 and t.id<49")
  List<CategoryJpa> findCountries();

  List<CategoryJpa> findTop20ById(@Param("id") int id, Pageable pageable);

  List<CategoryJpa> findAllByOrderByWeightAsc();
}
