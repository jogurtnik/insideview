package uk.co.punishell.insideview.model.database.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.co.punishell.insideview.model.converters.RaceCommandToRace;
import uk.co.punishell.insideview.model.converters.RaceToRaceCommand;
import uk.co.punishell.insideview.model.database.entities.Race;
import uk.co.punishell.insideview.model.database.entities.Runner;
import uk.co.punishell.insideview.model.database.repositories.RaceRepository;

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

        Set<Race> races = this.getRaces();

        if (!races.isEmpty()) {

            for (Race foundRace : races) {

                // Check first if race already exists in the database
                if (    race.getDate().compareTo(foundRace.getDate()) == 0 &&
                        race.getCountry().equalsIgnoreCase(foundRace.getCountry())  &&
                        race.getTime().equals(foundRace.getTime()) &&
                        race.getTrackLength().equalsIgnoreCase(foundRace.getTrackLength()) &&
                        race.getTrackType().equalsIgnoreCase(foundRace.getTrackType())) {

                    log.info("Race already exists in the database with ID: " + foundRace.getId());

                    return foundRace;
                }
            }

            return saveAndSetRelations(race);

        } else {

            return saveAndSetRelations(race);
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

    private Race saveAndSetRelations(Race race) {

        Race savedRace = raceRepository.save(raceCommandToRace.convert(raceToRaceCommand.convert(race)));
        Set<Runner> savedRunners = runnerService.saveAll(race.getRunners());

        savedRace.setRunners(savedRunners);

        savedRunners.iterator().forEachRemaining(runner -> runner.setRace(savedRace));

        log.info("NEW RACE ID: " + savedRace.getId());

        return savedRace;
    }
}