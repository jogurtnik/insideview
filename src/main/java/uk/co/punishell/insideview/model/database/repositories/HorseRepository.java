package uk.co.punishell.insideview.model.database.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.co.punishell.insideview.model.database.entities.Horse;

import java.util.Optional;

public interface HorseRepository extends JpaRepository<Horse, Long> {

    Optional<Horse> findByName(String name);
}
