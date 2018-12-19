package uk.co.punishell.insideview.model.services;

import org.springframework.stereotype.Service;
import uk.co.punishell.insideview.model.database.entities.Race;
import uk.co.punishell.insideview.model.database.entities.Runner;
import uk.co.punishell.insideview.model.database.repositories.HorseRepository;
import uk.co.punishell.insideview.model.database.repositories.RaceRepository;
import uk.co.punishell.insideview.model.database.repositories.RunnerRepository;

import java.time.LocalTime;
import java.util.Date;
import java.util.Set;
import java.util.function.Consumer;

@Service
public class DBPopulatorImpl implements DBPopulator {

    RaceRepository raceRepository;
    RunnerRepository runnerRepository;
    HorseRepository horseRepository;

    public DBPopulatorImpl(RaceRepository raceRepository, RunnerRepository runnerRepository, HorseRepository horseRepository) {
        this.raceRepository = raceRepository;
        this.runnerRepository = runnerRepository;
        this.horseRepository = horseRepository;
    }

    @Override
    public void populate(Set<Race> races) {

    }
}
