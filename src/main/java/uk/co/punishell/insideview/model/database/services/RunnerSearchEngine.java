package uk.co.punishell.insideview.model.database.services;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.co.punishell.insideview.model.database.entities.Runner;
import uk.co.punishell.insideview.model.database.repositories.RunnerRepository;
import uk.co.punishell.insideview.model.database.specifications.RunnerSpecification;
import uk.co.punishell.insideview.model.services.converters.RunnerToRunnerCommand;
import uk.co.punishell.insideview.view.commands.guiCommands.RunnerSeachResult;
import uk.co.punishell.insideview.view.commands.guiCommands.RunnerSearch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class RunnerSearchEngine implements SearchEngine<RunnerSearch, RunnerSeachResult> {

    private RunnerRepository runnerRepository;
    private RunnerToRunnerCommand runnerToRunnerCommand;
    private RunnerSpecification runnerSpecification;

    @Autowired
    public RunnerSearchEngine(RunnerRepository runnerRepository, RunnerToRunnerCommand runnerToRunnerCommand) {
        this.runnerRepository = runnerRepository;
        this.runnerToRunnerCommand = runnerToRunnerCommand;
    }

    @Override
    public RunnerSeachResult search(RunnerSearch criteria) {

        this.runnerSpecification = new RunnerSpecification(criteria);

        List<Runner> runners = runnerRepository.findAll(runnerSpecification);

        List<Runner> postQueryFilteredRunners;
        postQueryFilteredRunners = this.postSearch(runners, criteria);

        RunnerSeachResult result = new RunnerSeachResult();

        result.setRunners(postQueryFilteredRunners
                .stream()
                .map((@NotNull var runner) -> runnerToRunnerCommand.convert(runner))
                .collect(Collectors.toList()));

        result.setMessage("Runner Query has " + result.getRunners().size() + " results.");
        log.debug("Runner Query results: " + result.getRunners().size());

        return result;
    }

    public List<Runner> postSearch(List<Runner> queryResult, RunnerSearch criteria) {

        // In order to avoid ConcurrentModificationException create this copy of passed queryResult
        List<Runner> safeCopyOfQueryResult = new ArrayList<>();
        queryResult.iterator().forEachRemaining(runner -> safeCopyOfQueryResult.add(runner));

        // remove runners which do not meet the criteria
        for (Runner runner : safeCopyOfQueryResult) {

            List<Runner> raceRunners = runner.getRace().getRunners();
            Collections.sort(raceRunners);

            if (criteria.getRunnersCountMin() != 0) {
                if (raceRunners.size() < criteria.getRunnersCountMin()) {
                    queryResult.remove(runner);
                }
            }
            if (criteria.getRunnersCountMax() != 0) {
                if (raceRunners.size() > criteria.getRunnersCountMax()) {
                    queryResult.remove(runner);
                }
            }

            if (criteria.getNptipsPerRaceMin() != 0) {
                int count = 0;
                for (Runner raceRunner : raceRunners) {
                    if (runner.getNptips() >= criteria.getNptipsCountMin()) {
                        count++;
                    }
                }
                if (count < criteria.getNptipsPerRaceMin()) {
                    queryResult.remove(runner);
                }
            }
            if (criteria.getNptipsPerRaceMax() != 0) {
                int count = 0;
                for (Runner raceRunner : runner.getRace().getRunners()) {
                    if (runner.getNptips() <= criteria.getNptipsPerRaceMax()) {
                        count++;
                    }
                }
                if (count > criteria.getNptipsPerRaceMax()) {
                    queryResult.remove(runner);
                }
            }

            if (criteria.getFavouritePlaceMin() != 0) {
                for (int i = 1; i <= raceRunners.size(); i++) {
                    if (runner.equals(raceRunners.get(i-1))) {
                        if (i > criteria.getFavouritePlaceMin()) {
                            queryResult.remove(runner);
                        }
                    }
                }
            }
            if (criteria.getFavouritePlaceMax() != 0) {
                for (int i = 1; i <= raceRunners.size(); i++) {
                    if (runner.equals(raceRunners.get(i-1))) {
                        if (i < criteria.getFavouritePlaceMax()) {
                            queryResult.remove(runner);
                        }
                    }
                }
            }

            if (criteria.getFavouritePlaceAmongStarsMin() != 0 ||
                criteria.getFavouritePlaceAmongStarsMax() != 0) {
                int count = 1;
                for (Runner raceRunner : raceRunners) {
                    if (raceRunner.getStars() == runner.getStars()) {
                        count++;

                    }
                }
                if (count <  criteria.getFavouritePlaceAmongStarsMin()) {
                    queryResult.remove(runner);
                }
                if (count > criteria.getFavouritePlaceAmongStarsMax()) {
                    queryResult.remove(runner);
                }
            }
        }

        return queryResult;
    }
}
