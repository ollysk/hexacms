package net.ollysk.pr.persistance.dao;

import net.ollysk.pr.persistance.model.NodeMetaJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NodeMetaJpaRepo extends JpaRepository<NodeMetaJpa, Long> {

}
