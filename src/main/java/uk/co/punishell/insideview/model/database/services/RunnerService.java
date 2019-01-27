package uk.co.punishell.insideview.model.database.services;

import uk.co.punishell.insideview.model.database.entities.Runner;

import java.util.List;
import java.util.Set;

public interface RunnerService {

    Set<Runner> getRunners();

    Runner findById(Long id);

    Runner save(Runner runner);

    List<Runner> saveAll(List<Runner> runnrs);

    void delete(Runner runner);

    void deleteById(Long id);
}
