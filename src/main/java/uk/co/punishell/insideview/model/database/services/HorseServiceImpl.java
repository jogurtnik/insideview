package uk.co.punishell.insideview.model.database.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.co.punishell.insideview.model.database.entities.Horse;
import uk.co.punishell.insideview.model.database.repositories.HorseRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class HorseServiceImpl implements HorseService {

    private final HorseRepository horseRepository;

    @Autowired
    public HorseServiceImpl(HorseRepository horseRepository) {
        this.horseRepository = horseRepository;
    }

    @Override
    public Set<Horse> getHorses() {

        Set<Horse> horses = new HashSet<>();
        horseRepository.findAll().iterator().forEachRemaining(horses::add);

        return horses;
    }

    @Override
    public Horse findById(Long id) {

        Optional<Horse> horseOptional = horseRepository.findById(id);

        if (horseOptional.isEmpty()) {

            throw new RuntimeException("Horse not found by ID: " + id);
        }

        return horseOptional.get();
    }

    @Override
    public Horse save(Horse horse) {

        Horse savedHorse = null;

        Set<Horse> horses = new HashSet<>();
        horseRepository.findAll().iterator().forEachRemaining(horses::add);

        // Check if horse already exists in the database
        for (Horse foundHorse : horses) {

            if(horse.getName().equalsIgnoreCase(foundHorse.getName())) {

                log.info("Horse already exists in database with name: " + foundHorse.getName());
                return foundHorse;
            }
        }

        savedHorse = horseRepository.save(horse);

        log.info("NEW HORSE ID: " + savedHorse.getId());

        return savedHorse;
    }

    @Override
    public void delete(Horse horse) {

        horseRepository.delete(horse);
    }

    @Override
    public void deleteById(Long id) {

        horseRepository.deleteById(id);
    }
}
