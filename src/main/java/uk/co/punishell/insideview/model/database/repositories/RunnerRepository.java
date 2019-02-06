package uk.co.punishell.insideview.model.database.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import uk.co.punishell.insideview.model.database.entities.Runner;

public interface RunnerRepository extends JpaRepository<Runner, Long>, JpaSpecificationExecutor<Runner> {
}
