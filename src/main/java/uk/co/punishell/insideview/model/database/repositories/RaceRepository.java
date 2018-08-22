package uk.co.punishell.insideview.model.database.repositories;

import org.springframework.data.repository.CrudRepository;
import uk.co.punishell.insideview.model.database.entities.Race;

public interface RaceRepository extends CrudRepository<Race, Long> {
}
