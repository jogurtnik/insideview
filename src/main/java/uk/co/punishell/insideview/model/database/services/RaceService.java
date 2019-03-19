package uk.co.punishell.insideview.model.database.services;

import uk.co.punishell.insideview.model.database.entities.Race;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface RaceService {

    Set<Race> getRaces();

    Race findById(Long id);

    Set<Race> findByDate(LocalDate date);

    Race save(Race race);

    Set<Race> saveAll(List<Race> races);

    void delete(Race race);

    void deleteById(Long id);
}