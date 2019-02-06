package uk.co.punishell.insideview.model.database.services;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.co.punishell.insideview.model.database.entities.Race;
import uk.co.punishell.insideview.model.database.entities.Runner;
import uk.co.punishell.insideview.model.database.repositories.RaceRepository;
import uk.co.punishell.insideview.model.database.specifications.RaceSpecification;
import uk.co.punishell.insideview.model.services.converters.RaceToRaceCommand;
import uk.co.punishell.insideview.view.commands.guiCommands.RaceSearch;
import uk.co.punishell.insideview.view.commands.guiCommands.RaceSearchResult;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

        List<Race> races = raceRepository.findAll(raceSpecification);
        races = filterPostQuery(races, raceSearch);

        RaceSearchResult result = new RaceSearchResult();

        /*races
                .iterator()
                .forEachRemaining(race -> result.getRaces().add(raceToRaceCommand.convert(race)));
*/
        result.setRaces(races
                .stream()
                .map((@NotNull var race) -> raceToRaceCommand.convert(race))
                .collect(Collectors.toList()));

        result.setMessage("Race Query has " + result.getRaces().size() + " results.");

        log.debug("Race Query results: " + result.getRaces().size());

        return result;
    }

    /**
     * Returns filtered by criteria, list of entities, not applicable in Criteria API.
     *
     * @return filtered list by passed criteria
     */

    private List<Race> filterPostQuery(@NotNull List<Race> races, RaceSearch raceSearch) {

        List<Race> filteredRaces = new ArrayList<>();

        if (raceSearch.getFiveStarsCountMin() != 0 && raceSearch.getFiveStarsCountMax() != 0) {

        for (Race race : races) {

            boolean checkFiveStarsCountMinResult = false;
            boolean checkFiveStarsCountMaxResult = false;

            int fiveStarsCount = 0;

            List<Runner> runners = race.getRunners();

            for (Runner runner : runners) {

                if (runner.getStars() == 5) {

                    fiveStarsCount++;
                }
            }


            if (raceSearch.getFiveStarsCountMin() != 0) {
                if (fiveStarsCount >= raceSearch.getFiveStarsCountMin()) {
                    checkFiveStarsCountMinResult = true;
                } else {
                    checkFiveStarsCountMinResult = false;
                }
            } else {
                checkFiveStarsCountMinResult = true;
            }

            if (raceSearch.getFiveStarsCountMax() != 0) {
                if (fiveStarsCount <= raceSearch.getFiveStarsCountMax()) {
                    checkFiveStarsCountMaxResult = true;
                } else {
                    checkFiveStarsCountMaxResult = false;
                }
            } else {
                checkFiveStarsCountMaxResult = true;
            }

            if (checkFiveStarsCountMinResult && checkFiveStarsCountMaxResult) {

                filteredRaces.add(race);
            }

        }
        } else {
            return races;
        }

        return filteredRaces;
    }
}
