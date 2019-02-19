package uk.co.punishell.insideview.model.database.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    @Transactional
    public Horse save(Horse horse) {

        // update entity if it already has an ID
        if (horse.getId() != null) {

            return horseRepository.save(horse);
        }

        Optional<Horse> horseOptional = horseRepository.findByName(horse.getName());

        if (horseOptional.isEmpty()) {
            return horseRepository.save(horse);
        } else {
            return horseOptional.get();
        }
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
