package net.yorksoultions.processbe.repository;
import net.yorksoultions.processbe.entity.ProcessConfig;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessConfigRepository extends CrudRepository<ProcessConfig, Long> {
}

