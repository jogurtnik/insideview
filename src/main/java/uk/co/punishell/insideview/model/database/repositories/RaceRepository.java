package uk.co.punishell.insideview.model.database.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import uk.co.punishell.insideview.model.database.entities.Race;

import java.time.LocalDate;
import java.time.LocalTime;

public interface RaceRepository extends JpaRepository<Race, Long>, JpaSpecificationExecutor<Race> {

    Race findByLocalDateAndTimeAndCity(LocalDate date, LocalTime time, String city);
}
