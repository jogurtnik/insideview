package uk.co.punishell.insideview.model.database.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.co.punishell.insideview.model.database.entities.Race;
import uk.co.punishell.insideview.model.database.entities.Runner;
import uk.co.punishell.insideview.model.database.repositories.RaceRepository;
import uk.co.punishell.insideview.model.services.converters.RaceCommandToRace;
import uk.co.punishell.insideview.model.services.converters.RaceToRaceCommand;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class RaceServiceImpl implements RaceService {

    private final RaceRepository raceRepository;
    private final RunnerService runnerService;
    private final RaceToRaceCommand raceToRaceCommand;
    private final RaceCommandToRace raceCommandToRace;

    @Autowired
    public RaceServiceImpl(RaceRepository raceRepository,
                           RunnerService runnerService,
                           RaceToRaceCommand raceToRaceCommand,
                           RaceCommandToRace raceCommandToRace) {

        this.raceRepository = raceRepository;
        this.runnerService = runnerService;
        this.raceToRaceCommand = raceToRaceCommand;
        this.raceCommandToRace = raceCommandToRace;
    }

    @Override
    public Set<Race> getRaces() {

        Set<Race> races = new HashSet<>();

        raceRepository.findAll().iterator().forEachRemaining(races::add);

        return races;
    }

    @Override
    public Race findById(Long id) {

        Optional<Race> raceOptional = raceRepository.findById(id);

        if (raceOptional.isEmpty()) {
            throw new RuntimeException("Race not found by ID: " + id);
        }

        return raceOptional.get();
    }

    @Override
    @Transactional
    public Race save(Race race) {

        long startTime = System.currentTimeMillis();
        Race foundRace = raceRepository.findByLocalDateAndTimeAndCity(race.getLocalDate(), race.getTime(), race.getCity());
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        log.debug("Checking for duplicate took " + ((double) elapsedTime)/1000 + " s");

        if (foundRace != null) {

            return foundRace;

        } else {

            startTime = System.currentTimeMillis();
            // convert and de-convert to command object to save entity without reference to an unsaved object
            Race savedRace = raceRepository.save(race);
            List<Runner> savedRunners = runnerService.saveAll(race.getRunners());

            savedRace.setRunners(savedRunners);

            savedRunners.iterator().forEachRemaining(runner -> runner.setRace(savedRace));

            stopTime = System.currentTimeMillis();
            elapsedTime = stopTime - startTime;

            log.debug(savedRace.toString() + " saved in " + ((double) elapsedTime)/1000 + " s");

            return savedRace;
        }
    }

    @Override
    @Transactional
    public Set<Race> saveAll(List<Race> races) {

        Set<Race> savedRaces = new HashSet<>();

        races.stream().forEach(race -> savedRaces.add(this.save(race)));

        return savedRaces;
    }

    @Override
    public void delete(Race race) {

        raceRepository.delete(race);
    }

    @Override
    public void deleteById(Long id) {

        raceRepository.deleteById(id);
    }
}
