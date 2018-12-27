package uk.co.punishell.insideview.model.database.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uk.co.punishell.insideview.model.database.entities.Runner;
import uk.co.punishell.insideview.model.database.repositories.RunnerRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class RunnerServiceImpl implements RunnerService {

    private final RunnerRepository runnerRepository;

    public RunnerServiceImpl(RunnerRepository runnerRepository) {

        this.runnerRepository = runnerRepository;
    }

    @Override
    public Set<Runner> getRunners() {

        Set<Runner> runners = new HashSet<>();

        runnerRepository.findAll().iterator().forEachRemaining(runners::add);

        return runners;
    }

    @Override
    public Runner findById(Long id) {

        Optional<Runner> runnerOptional = runnerRepository.findById(id);

        if (runnerOptional.isEmpty()) {

            log.info("Runner to found by ID: " + id);
            return null;
        }

        return runnerOptional.get();
    }

    @Override
    public Runner save(Runner runner) {

        Runner savedRunner = runnerRepository.save(runner);

        return savedRunner;
    }

    @Override
    public Set<Runner> saveAll(Set<Runner> runners) {

        Set<Runner> savedRunners = new HashSet<>();

        runners.stream().forEach(runner -> savedRunners.add(runner));

        return savedRunners;
    }

    @Override
    public void delete(Runner runner) {

        runnerRepository.delete(runner);
    }

    @Override
    public void deleteById(Long id) {

        runnerRepository.deleteById(id);
    }
}
