package uk.co.punishell.insideview.model.database.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.co.punishell.insideview.model.database.entities.Race;

public interface RaceRepository extends JpaRepository<Race, Long> {
}
