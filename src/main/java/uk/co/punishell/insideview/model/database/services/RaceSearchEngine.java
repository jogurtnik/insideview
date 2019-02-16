package uk.co.punishell.insideview.model.database.services;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.co.punishell.insideview.model.database.entities.Race;
import uk.co.punishell.insideview.model.database.entities.RaceType;
import uk.co.punishell.insideview.model.database.entities.Runner;
import uk.co.punishell.insideview.model.database.repositories.RaceRepository;
import uk.co.punishell.insideview.model.database.specifications.RaceSpecification;
import uk.co.punishell.insideview.model.services.converters.RaceToRaceCommand;
import uk.co.punishell.insideview.view.commands.guiCommands.RaceSearch;
import uk.co.punishell.insideview.view.commands.guiCommands.RaceSearchResult;

import java.util.ArrayList;
import java.util.Collections;
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
    public RaceSearchResult search(RaceSearch criteria) {

        this.raceSpecification = new RaceSpecification(criteria);

        List<Race> races = raceRepository.findAll(raceSpecification);

        List<Race> postQueryFilteredRaces;
        postQueryFilteredRaces = this.postSearch(races, criteria);

        Collections.sort(postQueryFilteredRaces);

        postQueryFilteredRaces
                .stream().forEach(race -> Collections.sort(race.getRunners()));

        RaceSearchResult result = new RaceSearchResult();

        result.setRaces(postQueryFilteredRaces
                .stream()
                .map((@NotNull var race) -> raceToRaceCommand.convert(race))
                .collect(Collectors.toList()));

        result.setMessage("Race Query has " + result.getRaces().size() + " results.");

        log.debug("Race Query results: " + result.getRaces().size());

        return result;
    }

    public List<Race> postSearch(List<Race> queryResult, RaceSearch criteria) {

        // In order to avoid ConcurrentModificationException create this copy of passed queryResult
        List<Race> safeCopyOfQueryResult = new ArrayList<>();
        queryResult.iterator().forEachRemaining(race -> safeCopyOfQueryResult.add(race));

        // remove races which do not meet the criteria
        for (Race race : safeCopyOfQueryResult) {

            if (!criteria.getSelectedWeekDays().isEmpty()) {
                boolean isMatch = false;
                for (String weekday : criteria.getSelectedWeekDays()) {
                    if (race.getLocalDate().getDayOfWeek().name().equalsIgnoreCase(weekday)) {
                        isMatch = true;
                    }
                }
                if (!isMatch) {
                    queryResult.remove(race);
                }
            }

            if (!criteria.getSelectedRaceTypes().isEmpty()) {
                if(race.getRaceTypes().size() != criteria.getSelectedRaceTypes().size()) {
                    queryResult.remove(race);
                } else {
                    for (RaceType raceType : race.getRaceTypes()) {
                        boolean exists = false;
                        for (String selectedRaceType : criteria.getSelectedRaceTypes()) {
                            if (raceType.getName().equalsIgnoreCase(selectedRaceType)) {
                                exists = true;
                            }
                        }
                        if (!exists) {
                            queryResult.remove(race);
                        }
                    }
                }
            }

            if (criteria.getFiveStarsCountMin() != 0 ||
                criteria.getFiveStarsCountMax() != 0) {
                int count = 0;
                for (Runner runner : race.getRunners()) {
                    if (runner.getStars() == 5) {
                        count++;
                    }
                }
                if (count > criteria.getFiveStarsCountMin()) {
                    queryResult.remove(race);
                }
                if (count < criteria.getFiveStarsCountMax()) {
                    queryResult.remove(race);
                }
            }
        }



        return queryResult;
    }
}
