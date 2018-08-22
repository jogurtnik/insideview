package uk.co.punishell.insideview.model.database.repositories;

import org.springframework.data.repository.CrudRepository;
import uk.co.punishell.insideview.model.database.entities.Horse;

public interface HorseRepository extends CrudRepository<Horse, Long> {
}
