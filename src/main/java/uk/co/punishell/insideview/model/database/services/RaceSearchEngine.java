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
                if (race.getRaceTypes().size() != criteria.getSelectedRaceTypes().size()) {
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

            String mov9to11Color = criteria.getMov9to11Color();
            int mov9to11CountPerRace = criteria.getMov9to11CountPerRace();
            if (!mov9to11Color.isEmpty()) {
                if (mov9to11CountPerRace != 0) {
                    int count = 0;
                    for (Runner raceRunner : race.getRunners()) {
                        double raceRunnerMov9to11 = raceRunner.getMov9to11();
                        if (mov9to11Color.equalsIgnoreCase("blue")) {
                            double blueMovementMin = criteria.getMovementsColorsMap().get("blueMovementMin");
                            if (raceRunnerMov9to11 >= blueMovementMin) {
                                count++;
                            }
                        } else if (mov9to11Color.equalsIgnoreCase("green")) {
                            double greenMovementMin = criteria.getMovementsColorsMap().get("greenMovementMin");
                            double greenMovementMax = criteria.getMovementsColorsMap().get("greenMovementMax");
                            if (raceRunnerMov9to11 <= greenMovementMax && raceRunnerMov9to11 >= greenMovementMin) {
                                count++;
                            }
                        } else if (mov9to11Color.equalsIgnoreCase("yellow")) {
                            double yellowMovementMin = criteria.getMovementsColorsMap().get("yellowMovementMax");
                            double yellowMovementMax = criteria.getMovementsColorsMap().get("yellowMovementMin");
                            if (raceRunnerMov9to11 <= yellowMovementMin && raceRunnerMov9to11 >= yellowMovementMax) {
                                count++;
                            }
                        } else if (mov9to11Color.equalsIgnoreCase("orange")) {
                            double orangeMovementMin = criteria.getMovementsColorsMap().get("orangeMovementMax");
                            double orangeMovementMax = criteria.getMovementsColorsMap().get("orangeMovementMin");
                            if (raceRunnerMov9to11 <= orangeMovementMax && raceRunnerMov9to11 >= orangeMovementMin) {
                                count++;
                            }
                        } else if (mov9to11Color.equalsIgnoreCase("pink")) {
                            double pinkMovementMax = criteria.getMovementsColorsMap().get("pinkMovementMax");
                            if (raceRunnerMov9to11 <= pinkMovementMax) {
                                count++;
                            }
                        }
                    }
                    if (count != mov9to11CountPerRace) {
                        queryResult.remove(race);
                    }
                }
            }

            String lastThreeMovsColor = criteria.getLastThreeMovsColor();
            if (!lastThreeMovsColor.isEmpty()) {
                int count = 0;
                if (criteria.getLastThreeMovsCountPerRace() != 0) {
                        for (Runner raceRunner : race.getRunners()) {
                            if (countLastThreeMovs(raceRunner, criteria) == criteria.getLastThreeMovsCount()) {
                                count++;
                            }
                        }
                        if (count != criteria.getLastThreeMovsCountPerRace()) {
                            queryResult.remove(race);
                        }
                }
            }
        }

        return queryResult;
    }

    private int countLastThreeMovs(Runner runner, RaceSearch criteria) {
        int count = 0;
        String color = criteria.getLastThreeMovsColor();

        if (color.equalsIgnoreCase("blue")) {
            double blueMovementMin = criteria.getMovementsColorsMap().get("blueMovementMin");
            if (runner.getMov1() >= blueMovementMin) {
                count++;
            }
            if (runner.getMean() >= blueMovementMin) {
                count++;
            }
            if (runner.getMov3to1() >= blueMovementMin) {
                count++;
            }
            return count;
        } else if (color.equalsIgnoreCase("green")) {
            double greenMovementMin = criteria.getMovementsColorsMap().get("greenMovementMin");
            double greenMovementMax = criteria.getMovementsColorsMap().get("greenMovementMax");
            if (runner.getMov1() >= greenMovementMin && runner.getMov1() <= greenMovementMax) {
                count++;
            }
            if (runner.getMean() >= greenMovementMin && runner.getMean() <= greenMovementMax) {
                count++;
            }
            if (runner.getMov3to1() >= greenMovementMin && runner.getMov3to1() <= greenMovementMax) {
                count++;
            }
            log.debug(runner.getHorse().getName() + " has " + count + " greens");
            return count;
        } else if (color.equalsIgnoreCase("yellow")) {
            double yellowMovementMin = criteria.getMovementsColorsMap().get("yellowMovementMin");
            double yellowMovementMax = criteria.getMovementsColorsMap().get("yellowMovementMax");
            if (runner.getMov1() >= yellowMovementMin && runner.getMov1() <= yellowMovementMax) {
                count++;
            }
            if (runner.getMean() >= yellowMovementMin && runner.getMean() <= yellowMovementMax) {
                count++;
            }
            if (runner.getMov3to1() >= yellowMovementMin && runner.getMov3to1() <= yellowMovementMax) {
                count++;
            }
            return count;
        } else if (color.equalsIgnoreCase("orange")) {
            double orangeMovementMin = criteria.getMovementsColorsMap().get("orangeMovementMin");
            double orangeMovementMax = criteria.getMovementsColorsMap().get("orangeMovementMax");
            if (runner.getMov1() >= orangeMovementMin && runner.getMov1() <= orangeMovementMax) {
                count++;
            }
            if (runner.getMean() >= orangeMovementMin && runner.getMean() <= orangeMovementMax) {
                count++;
            }
            if (runner.getMov3to1() >= orangeMovementMin && runner.getMov3to1() <= orangeMovementMax) {
                count++;
            }
        } else if (color.equalsIgnoreCase("pink")) {
            double pinkMovementMax = criteria.getMovementsColorsMap().get("pinkMovementMax");
            if (runner.getMov1() <= pinkMovementMax) {
                count++;
            }
            if (runner.getMean() <= pinkMovementMax) {
                count++;
            }
            if (runner.getMov3to1() <= pinkMovementMax) {
                count++;
            }
            return count;
        }
        return count;
    }
}
