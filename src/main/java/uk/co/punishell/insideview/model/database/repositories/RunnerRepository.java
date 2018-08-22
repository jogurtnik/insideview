package uk.co.punishell.insideview.model.database.repositories;

import org.springframework.data.repository.CrudRepository;
import uk.co.punishell.insideview.model.database.entities.Runner;

public interface RunnerRepository extends CrudRepository<Runner, Long> {
}
