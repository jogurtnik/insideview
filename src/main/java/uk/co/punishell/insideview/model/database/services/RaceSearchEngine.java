package uk.co.punishell.insideview.model.database.services;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.co.punishell.insideview.model.database.entities.Race;
import uk.co.punishell.insideview.model.database.entities.Runner;
import uk.co.punishell.insideview.model.database.repositories.RaceRepository;
import uk.co.punishell.insideview.model.database.specifications.RaceSpecification;
import uk.co.punishell.insideview.model.services.web.commands.guiCommands.RaceSearch;
import uk.co.punishell.insideview.model.services.web.commands.guiCommands.RaceSearchResult;
import uk.co.punishell.insideview.model.services.web.converters.RaceToRaceCommand;

import java.util.LinkedList;
import java.util.List;

@Slf4j
@Component
public class RaceSearchEngine implements SearchEngine<RaceSearch, RaceSearchResult> {

    private RaceRepository raceRepository;
    private RaceToRaceCommand raceToRaceCommand;
    private RaceSpecification raceSpecification;

    @Autowired
    public RaceSearchEngine(RaceRepository raceRepository,
                            RaceToRaceCommand raceToRaceCommand) {

        this.raceRepository = raceRepository;
        this.raceToRaceCommand = raceToRaceCommand;
    }

    @Override
    public RaceSearchResult search(RaceSearch raceSearch) {

        this.raceSpecification = new RaceSpecification(raceSearch);

        List<Race> races = this.filterPostQuery(raceRepository.findAll(raceSpecification), raceSearch);

        RaceSearchResult result = new RaceSearchResult();
        races
                .iterator()
                .forEachRemaining(race -> result.getRaces().add(raceToRaceCommand.convert(race)));

        result.setMessage("Query has " + result.getRaces().size() + " results.");
        log.debug("Query results: " + result.getRaces().size());

        return result;
    }

    /**
     * Returns filtered by criteria, list of entities, not applicable in Criteria API.
     *
     * @return filtered list by passed criteria
     */

    private List<Race> filterPostQuery(@NotNull List<Race> races, RaceSearch raceSearch) {

        List<Race> filteredRaces = new LinkedList<>();

        for (Race race : races) {

            boolean checkFiveStarsCountResult = false;

            int fiveStarsCount = 0;

            List<Runner> runners = race.getRunners();

            for (Runner runner : runners) {

                if (runner.getStars() == 5) {

                    fiveStarsCount++;
                }
            }

            if (raceSearch.getFiveStarsCountMin() != 0 || raceSearch.getFiveStarsCountMax() != 0) {

                if (raceSearch.getFiveStarsCountMin() != 0) {
                    if (fiveStarsCount >= raceSearch.getFiveStarsCountMin()) {
                        checkFiveStarsCountResult = true;
                    } else {
                        checkFiveStarsCountResult = false;
                    }
                }

                if (raceSearch.getFiveStarsCountMax() != 0) {
                    if (fiveStarsCount <= raceSearch.getFiveStarsCountMax()) {
                        checkFiveStarsCountResult = true;
                    } else {
                        checkFiveStarsCountResult = false;
                    }
                }
            }

            if (checkFiveStarsCountResult) {

                filteredRaces.add(race);
            }

        }

        return filteredRaces;
    }
}
