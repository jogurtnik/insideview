package uk.co.punishell.insideview.model.database.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.co.punishell.insideview.model.database.entities.Runner;

public interface RunnerRepository extends JpaRepository<Runner, Long> {
}
