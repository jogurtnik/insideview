package uk.co.punishell.insideview.model.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import uk.co.punishell.insideview.model.database.entities.Race;
import uk.co.punishell.insideview.model.database.repositories.HorseRepository;
import uk.co.punishell.insideview.model.database.repositories.RaceRepository;
import uk.co.punishell.insideview.model.database.repositories.RunnerRepository;

import java.util.List;
@Service
public class DBPopulatorImpl implements DBPopulator {

    private static final Logger logger = LoggerFactory.getLogger(DBPopulatorImpl.class);

    RaceRepository raceRepository;
    RunnerRepository runnerRepository;
    HorseRepository horseRepository;

    public DBPopulatorImpl(RaceRepository raceRepository, RunnerRepository runnerRepository, HorseRepository horseRepository) {
        this.raceRepository = raceRepository;
        this.runnerRepository = runnerRepository;
        this.horseRepository = horseRepository;
    }

    @Override
    public void populate(List<Race> races) {

        /*for (Race race : races) {

            raceRepository.save(race);

            for (Runner runner : race.getRunners()) {

                runnerRepository.save(runner);
                horseRepository.save(runner.getHorse());
            }
        }*/
    }
}
