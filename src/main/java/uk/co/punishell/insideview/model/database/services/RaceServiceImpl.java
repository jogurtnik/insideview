package uk.co.punishell.insideview.model.database.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.co.punishell.insideview.model.database.entities.Race;
import uk.co.punishell.insideview.model.database.repositories.RaceRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class RaceServiceImpl implements RaceService {

    private final RaceRepository raceRepository;

    @Autowired
    public RaceServiceImpl(RaceRepository raceRepository) {

        this.raceRepository = raceRepository;
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
    public Race save(Race race) {

        Race savedRace = null;

        Set<Race> races = new HashSet<>();
        raceRepository.findAll().iterator().forEachRemaining(races::add);

        for (Race foundRace : races) {

            // Check first if race already exists in the database
            if (race.getDate() == foundRace.getDate() &&
                race.getCountry() == foundRace.getCountry() &&
                race.getTime() == foundRace.getTime() &&
                race.getTrackLength() == foundRace.getTrackLength() &&
                race.getTrackType() == foundRace.getTrackType()) {

                throw new RuntimeException("Race already exists in the database!");
            }
        }

        savedRace = raceRepository.save(race);

        return savedRace;
    }

    @Override
    public Set<Race> saveAll(List<Race> races) {

        Set<Race> savedRaces = new HashSet<>();

        races.stream().forEach(race -> savedRaces.add(race));

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
