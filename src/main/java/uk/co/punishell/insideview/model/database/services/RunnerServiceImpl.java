package uk.co.punishell.insideview.model.database.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.co.punishell.insideview.model.database.entities.Horse;
import uk.co.punishell.insideview.model.database.entities.Runner;
import uk.co.punishell.insideview.model.database.repositories.RunnerRepository;

import java.util.*;

@Slf4j
@Service
public class RunnerServiceImpl implements RunnerService {

    private final RunnerRepository runnerRepository;
    private final HorseService horseService;
    private final String tm = "horseRacingTransactionManager";

    public RunnerServiceImpl(RunnerRepository runnerRepository,
                             HorseService horseService) {

        this.runnerRepository = runnerRepository;
        this.horseService = horseService;
    }

    @Override
    @Transactional(tm)
    public Set<Runner> getRunners() {

        Set<Runner> runners = new HashSet<>();

        runnerRepository.findAll().iterator().forEachRemaining(runners::add);

        return runners;
    }

    @Override
    @Transactional(tm)
    public Runner findById(Long id) {

        Optional<Runner> runnerOptional = runnerRepository.findById(id);

        if (runnerOptional.isEmpty()) {

            return null;
        }

        return runnerOptional.get();
    }

    @Override
    @Transactional(tm)
    public Runner save(Runner runner) {

        Horse savedHorse = horseService.save(runner.getHorse());

        runner.setHorse(savedHorse);

        Runner savedRunner = runnerRepository.save(runner);

        savedHorse.getRunners().add(savedRunner);

        //update Horse entity
        horseService.save(savedHorse);

        return savedRunner;
    }

    @Override
    @Transactional(tm)
    public List<Runner> saveAll(List<Runner> runners) {

        List<Runner> savedRunners = new ArrayList<>();

        runners.stream().forEach(runner -> savedRunners.add(this.save(runner)));

        return savedRunners;
    }

    @Override
    @Transactional(tm)
    public void delete(Runner runner) {

        runnerRepository.delete(runner);
    }

    @Override
    @Transactional(tm)
    public void deleteById(Long id) {

        runnerRepository.deleteById(id);
    }
}
