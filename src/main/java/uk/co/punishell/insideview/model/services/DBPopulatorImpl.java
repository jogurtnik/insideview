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

        races.forEach(new Consumer<Race>() {

            @Override
            public void accept(Race race) {

                if (!isExist(race)) {

                    raceRepository.save(race);

                    race.getRunners().forEach(new Consumer<Runner>() {

                        @Override
                        public void accept(Runner runner) {

                            runnerRepository.save(runner);
                            horseRepository.save(runner.getHorse());
                        }
                    });
                }
            }
        });
    }

    private boolean isExist(Race race){

        final boolean[] result = {false};

        Date date = race.getDate();
        String country = race.getCountry();
        String trackLength = race.getTrackLength();
        String trackType = race.getTrackType();
        LocalTime time = race.getTime();

        raceRepository.findAll().forEach(new Consumer<Race>() {

            @Override
            public void accept(Race race) {

                if (race.getDate() == date && race.getCountry() == country && race.getTrackLength() == trackLength && race.getTrackType() == trackType && race.getTime() == time) {

                    result[0] = true;
                }
            }
        });

        return result[0];
    }
}
