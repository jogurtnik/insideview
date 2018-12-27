package uk.co.punishell.insideview.model.database.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uk.co.punishell.insideview.model.converters.RunnerCommandToRunner;
import uk.co.punishell.insideview.model.converters.RunnerToRunnerCommand;
import uk.co.punishell.insideview.model.database.entities.Horse;
import uk.co.punishell.insideview.model.database.entities.Runner;
import uk.co.punishell.insideview.model.database.repositories.RunnerRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class RunnerServiceImpl implements RunnerService {

    private final RunnerRepository runnerRepository;
    private final HorseService horseService;
    private final RunnerToRunnerCommand runnerToRunnerCommand;
    private final RunnerCommandToRunner runnerCommandToRunner;

    public RunnerServiceImpl(RunnerRepository runnerRepository,
                             HorseService horseService,
                             RunnerToRunnerCommand runnerToRunnerCommand,
                             RunnerCommandToRunner runnerCommandToRunner) {

        this.runnerRepository = runnerRepository;
        this.horseService = horseService;
        this.runnerToRunnerCommand = runnerToRunnerCommand;
        this.runnerCommandToRunner = runnerCommandToRunner;
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

        Runner savedRunner = runnerRepository.save(runnerCommandToRunner.convert(runnerToRunnerCommand.convert(runner)));

        Horse savedHorse = horseService.save(runner.getHorse());

        savedRunner.setHorse(savedHorse);

        log.info("NEW RUNNER ID: " + savedRunner.getId());

        return savedRunner;
    }

    @Override
    public Set<Runner> saveAll(Set<Runner> runners) {

        Set<Runner> savedRunners = new HashSet<>();

        runners.stream().forEach(runner -> savedRunners.add(this.save(runner)));

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
