package uk.co.punishell.insideview.model.database.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import uk.co.punishell.insideview.model.database.entities.Race;

import java.time.LocalTime;
import java.util.Date;

public interface RaceRepository extends JpaRepository<Race, Long>, JpaSpecificationExecutor<Race> {

    Race findByDateAndCityAndCountryAndTimeAndTrackLength(Date date, String city, String country, LocalTime time, double trackLength);
}
