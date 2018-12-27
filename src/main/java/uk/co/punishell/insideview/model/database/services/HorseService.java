package uk.co.punishell.insideview.model.database.services;

import uk.co.punishell.insideview.model.database.entities.Horse;

import java.util.Set;

public interface HorseService {

    Set<Horse> getHorses();

    Horse findById(Long id);

    Horse save(Horse horse);

    void delete(Horse horse);

    void deleteById(Long id);
}
