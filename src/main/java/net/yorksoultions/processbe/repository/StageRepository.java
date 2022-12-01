package net.yorksoultions.processbe.repository;

import net.yorksoultions.processbe.entity.Stage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StageRepository extends CrudRepository<Stage, Long> {
}
