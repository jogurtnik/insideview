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

        List<Runner> sortedRunners = mergeSortRunnersByDate(postQueryFilteredRunners);

        RunnerSeachResult result = new RunnerSeachResult();

        result.setRunners(sortedRunners
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

            // remove runners not matching date specs
            if (criteria.getLocalDateSince() != null) {
                int dateComparisonResult = runner.getRace().getLocalDate().compareTo(criteria.getLocalDateSince());

                if (dateComparisonResult < 0) {
                    queryResult.remove(runner);
                }
            }
            if (criteria.getLocalDateTo() != null) {
                int dateComparisonResult = runner.getRace().getLocalDate().compareTo(criteria.getLocalDateTo());

                if (dateComparisonResult > 0) {
                    queryResult.remove(runner);
                }
            }

            // remove runners not matching weekday specs
            if (!criteria.getSelectedWeekDays().isEmpty()) {
                boolean isMatch = false;
                for (String weekday : criteria.getSelectedWeekDays()) {
                    if (runner.getRace().getLocalDate().getDayOfWeek().name().equalsIgnoreCase(weekday)) {
                        isMatch = true;
                    }
                }
                if (!isMatch) {
                    queryResult.remove(runner);
                }
            }

            // remove runners from races with runners count different from criteria
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

            // remove runners from races which do not match nptips count criteria
            if (criteria.getNptipsPerRaceMin() != 0) {
                int count = 0;
                if (criteria.getNptipsCountMin() != 0 && criteria.getNptipsCountMax() == 0) {
                    for (Runner raceRunner : raceRunners) {
                        if (raceRunner.getNptips() >= criteria.getNptipsCountMin()) {
                            count++;
                        }
                    }
                    if (count < criteria.getNptipsPerRaceMin()) {
                        queryResult.remove(runner);
                    }
                } else if (criteria.getNptipsCountMin() == 0 && criteria.getNptipsCountMax() != 0) {
                    for (Runner raceRunner : raceRunners) {
                        if (raceRunner.getNptips() <= criteria.getNptipsCountMax()) {
                            count++;
                        }
                    }
                    if (count < criteria.getNptipsPerRaceMin()) {
                        queryResult.remove(runner);
                    }
                } else if (criteria.getNptipsCountMin() != 0 && criteria.getNptipsCountMax() != 0) {
                    for (Runner raceRunner : raceRunners) {
                        if (raceRunner.getNptips() >= criteria.getNptipsCountMin() &&
                                raceRunner.getNptips() <= criteria.getNptipsCountMax()) {
                            count++;
                        }
                    }
                    if (count < criteria.getNptipsPerRaceMin()) {
                        queryResult.remove(runner);
                    }
                }
            }
            if (criteria.getNptipsPerRaceMax() != 0) {
                int count = 0;
                if (criteria.getNptipsCountMin() != 0 && criteria.getNptipsCountMax() == 0) {
                    for (Runner raceRunner : raceRunners) {
                        if (raceRunner.getNptips() >= criteria.getNptipsCountMin()) {
                            count++;
                        }
                    }
                    if (count < criteria.getNptipsPerRaceMin()) {
                        queryResult.remove(runner);
                    }
                } else if (criteria.getNptipsCountMin() == 0 && criteria.getNptipsCountMax() != 0) {
                    for (Runner raceRunner : raceRunners) {
                        if (raceRunner.getNptips() <= criteria.getNptipsCountMax()) {
                            count++;
                        }
                    }
                    if (count < criteria.getNptipsPerRaceMin()) {
                        queryResult.remove(runner);
                    }
                } else if (criteria.getNptipsCountMin() != 0 && criteria.getNptipsCountMax() != 0) {
                    for (Runner raceRunner : raceRunners) {
                        if (raceRunner.getNptips() >= criteria.getNptipsCountMin() &&
                                raceRunner.getNptips() <= criteria.getNptipsCountMax()) {
                            count++;
                        }
                    }
                    if (count < criteria.getNptipsPerRaceMin()) {
                        queryResult.remove(runner);
                    }
                }
            }

            // remove runners from races which are not at demanded favourite place
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

            // remove runners from races which do not have specified count of star runners
            if (criteria.getRunnerStarsPerRaceMin() != 0 ||
                criteria.getRunnerStarsPerRaceMax() != 0) {
                int count = 0;
                for (Runner raceRunner : raceRunners) {
                    if (runner.getStars() == raceRunner.getStars()) {
                        count++;
                    }
                }
                if (criteria.getRunnerStarsPerRaceMin() != 0) {
                    if (count < criteria.getRunnerStarsPerRaceMin()) {
                        queryResult.remove(runner);
                    }
                }
                if (criteria.getRunnerStarsPerRaceMax() != 0) {
                    if (count > criteria.getRunnerStarsPerRaceMax()) {
                        queryResult.remove(runner);
                    }
                }
            }

            // remove runners which are not at specified favourite place among star runners
            if (criteria.getFavouritePlaceAmongStarsMin() != 0 ||
                criteria.getFavouritePlaceAmongStarsMax() != 0) {
                List<Runner> starRunners = new ArrayList<>();
                for (Runner raceRunner : raceRunners) {
                    if (raceRunner.getStars() == runner.getStars()) {
                        starRunners.add(raceRunner);
                    }
                }
                Collections.sort(starRunners);
                if (criteria.getFavouritePlaceAmongStarsMin() != 0) {
                    if (starRunners.indexOf(runner) + 1 < criteria.getFavouritePlaceAmongStarsMin() ) {
                        queryResult.remove(runner);
                    }
                }
                if (criteria.getFavouritePlaceAmongStarsMax() != 0) {
                    if (starRunners.indexOf(runner) + 1 > criteria.getFavouritePlaceAmongStarsMax()) {
                        queryResult.remove(runner);
                    }
                }
            }

            // remove runners which were not last favourite in the race
            if (criteria.isLastRunner()) {
                if (!raceRunners.get(raceRunners.size() - 1).equals(runner)) {
                    queryResult.remove(runner);
                }
            }

            // remove runners which are not meeting all day movements spec
            String allDayMovsColor = criteria.getAllDayMovsColor();
            if (!allDayMovsColor.isEmpty()) {
                if (this.countAllDayMovements(runner, criteria) != criteria.getAllDayMovsCount()) {
                    queryResult.remove(runner);
                }
            }

            String mov9to11Color = criteria.getMov9to11Color();
            int mov9to11CountPerRace = criteria.getMov9to11CountPerRace();
            if (!mov9to11Color.isEmpty()) {
                if (mov9to11CountPerRace != 0) {
                    List<Runner> movers = new ArrayList<>();
                    int count = 0;
                    double runnerMov9to11 = runner.getMov9to11();
                    for (Runner raceRunner : raceRunners) {
                        double raceRunnerMov9to11 = raceRunner.getMov9to11();
                        if (mov9to11Color.equalsIgnoreCase("blue")) {
                            double blueMovementMin = criteria.getMovementsColorsMap().get("blueMovementMin");
                            if (!(runnerMov9to11 >= blueMovementMin)) {
                                queryResult.remove(runner);
                            } else if (raceRunnerMov9to11 >= blueMovementMin) {
                                count++;
                                movers.add(raceRunner);
                            }
                        } else if (mov9to11Color.equalsIgnoreCase("green")) {
                            double greenMovementMin = criteria.getMovementsColorsMap().get("greenMovementMin");
                            double greenMovementMax = criteria.getMovementsColorsMap().get("greenMovementMax");
                            if (!(runnerMov9to11 <= greenMovementMax && runnerMov9to11 >= greenMovementMin)) {
                                queryResult.remove(runner);
                            } else if (raceRunnerMov9to11 <= greenMovementMax && raceRunnerMov9to11 >= greenMovementMin) {
                                count++;
                                movers.add(raceRunner);
                            }
                        } else if (mov9to11Color.equalsIgnoreCase("yellow")) {
                            double yellowMovementMin = criteria.getMovementsColorsMap().get("yellowMovementMax");
                            double yellowMovementMax = criteria.getMovementsColorsMap().get("yellowMovementMin");
                            if (!(runnerMov9to11 <= yellowMovementMax && runnerMov9to11 >= yellowMovementMin)) {
                                queryResult.remove(runner);
                            }  else if (raceRunnerMov9to11 <= yellowMovementMin && raceRunnerMov9to11 >= yellowMovementMax) {
                                count++;
                                movers.add(raceRunner);
                            }
                        } else if (mov9to11Color.equalsIgnoreCase("orange")) {
                            double orangeMovementMin = criteria.getMovementsColorsMap().get("orangeMovementMax");
                            double orangeMovementMax = criteria.getMovementsColorsMap().get("orangeMovementMin");
                            if (!(runnerMov9to11 <= orangeMovementMax && runnerMov9to11 >= orangeMovementMin)) {
                                queryResult.remove(runner);
                            } else if (raceRunnerMov9to11 <= orangeMovementMax && raceRunnerMov9to11 >= orangeMovementMin) {
                                count++;
                                movers.add(raceRunner);
                            }
                        } else if (mov9to11Color.equalsIgnoreCase("pink")) {
                            double pinkMovementMax = criteria.getMovementsColorsMap().get("pinkMovementMax");
                            if (!(runnerMov9to11 <= pinkMovementMax)) {
                                queryResult.remove(runner);
                            } else if (raceRunnerMov9to11 <= pinkMovementMax) {
                                count++;
                                movers.add(raceRunner);
                            }
                        }
                    }
                    if (count != mov9to11CountPerRace) {
                        queryResult.remove(runner);
                    } else {
                        Collections.sort(movers);
                        int mov9to11FavPos = criteria.getMov9to11FavPos();
                        if (mov9to11FavPos != 0) {
                            if (!movers.get(mov9to11FavPos).equals(runner)) {
                                queryResult.remove(runner);
                            }
                        }
                    }
                } else {
                    queryResult.remove(runner);
                }
            }

            String lastThreeMovsColor = criteria.getLastThreeMovsColor();
            if (!lastThreeMovsColor.isEmpty()) {
                int count = 0;
                if (countLastThreeMovs(runner, criteria) != criteria.getLastThreeMovsCount()) {
                    queryResult.remove(runner);
                } else {
                    if (criteria.getLastThreeMovsCountPerRace() != 0) {
                        for (Runner raceRunner : raceRunners) {
                            if (countLastThreeMovs(raceRunner, criteria) == criteria.getLastThreeMovsCount()) {
                                count++;
                            }
                        }
                        if (count != criteria.getLastThreeMovsCountPerRace()) {
                            queryResult.remove(runner);
                        }
                    }
                }
            }

        }

        return queryResult;
    }

    private int countLastThreeMovs(Runner runner, RunnerSearch criteria) {
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

    private int countAllDayMovements(Runner runner, RunnerSearch criteria) {
        int count = 0;
        String color = criteria.getAllDayMovsColor();

        if (color.equalsIgnoreCase("blue")) {
            double blueMovementMin = criteria.getMovementsColorsMap().get("blueMovementMin");
            if (runner.getMov60() >= blueMovementMin) {
                count++;
            }
            if (runner.getMov30() >= blueMovementMin) {
                count++;
            }
            if (runner.getMov15() >= blueMovementMin) {
                count++;
            }
            if (runner.getMov5() >= blueMovementMin) {
                count++;
            }
            if (runner.getMov3() >= blueMovementMin) {
                count++;
            }
            if (runner.getMov2() >= blueMovementMin) {
                count++;
            }
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
                if (runner.getMov60() >= greenMovementMin && runner.getMov60() <= greenMovementMax) {
                    count++;
                }
                if (runner.getMov30() >= greenMovementMin && runner.getMov30() <= greenMovementMax) {
                    count++;
                }
                if (runner.getMov15() >= greenMovementMin && runner.getMov15() <= greenMovementMax ) {
                    count++;
                }
                if (runner.getMov5() >= greenMovementMin && runner.getMov5() <= greenMovementMax) {
                    count++;
                }
                if (runner.getMov3() >= greenMovementMin && runner.getMov3() <= greenMovementMax) {
                    count++;
                }
                if (runner.getMov2() >= greenMovementMin && runner.getMov2() <= greenMovementMax) {
                    count++;
                }
                if (runner.getMov1() >= greenMovementMin && runner.getMov1() <= greenMovementMax) {
                    count++;
                }
                if (runner.getMean() >= greenMovementMin && runner.getMean() <= greenMovementMax) {
                    count++;
                }
                if (runner.getMov3to1() >= greenMovementMin && runner.getMov3to1() <= greenMovementMax) {
                    count++;
                }
            return count;
        } else if (color.equalsIgnoreCase("yellow")) {
            double yellowMovementMin = criteria.getMovementsColorsMap().get("yellowMovementMin");
            double yellowMovementMax = criteria.getMovementsColorsMap().get("yellowMovementMax");
                if (runner.getMov60() >= yellowMovementMin && runner.getMov60() <= yellowMovementMax) {
                    count++;
                }
                if (runner.getMov30() >= yellowMovementMin && runner.getMov30() <= yellowMovementMax) {
                    count++;
                }
                if (runner.getMov15() >= yellowMovementMin && runner.getMov15() <= yellowMovementMax ) {
                    count++;
                }
                if (runner.getMov5() >= yellowMovementMin && runner.getMov5() <= yellowMovementMax) {
                    count++;
                }
                if (runner.getMov3() >= yellowMovementMin && runner.getMov3() <= yellowMovementMax) {
                    count++;
                }
                if (runner.getMov2() >= yellowMovementMin && runner.getMov2() <= yellowMovementMax) {
                    count++;
                }
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
        } else if (color.equalsIgnoreCase("pink")) {
            double pinkMovementMax = criteria.getMovementsColorsMap().get("pinkMovementMax");
                if (runner.getMov60() <= pinkMovementMax) {
                    count++;
                }
                if (runner.getMov30() <= pinkMovementMax) {
                    count++;
                }
                if (runner.getMov15() <= pinkMovementMax) {
                    count++;
                }
                if (runner.getMov5() <= pinkMovementMax) {
                    count++;
                }
                if (runner.getMov3() <= pinkMovementMax) {
                    count++;
                }
                if (runner.getMov2() <= pinkMovementMax) {
                    count++;
                }
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

    private List<Runner> mergeSortRunnersByDate(List<Runner> list) {
        List<Runner> sortedList = new ArrayList<>();
        Runner[] runnersArray = new Runner[list.size()];
        for (int i = 0; i < runnersArray.length; i++) {
            runnersArray[i] = list.get(i);
        }

        sort(runnersArray, 0, runnersArray.length - 1);
        for (Runner runner : runnersArray) {
            sortedList.add(runner);
        }
        return sortedList;
    }

    private void sort(Runner arr[], int l, int r) {
        if (l < r)
        {
            // Find the middle point
            int m = (l+r)/2;

            // Sort first and second halves
            sort(arr, l, m);
            sort(arr , m+1, r);

            // Merge the sorted halves
            merge(arr, l, m, r);
        }
    }

    private void merge(Runner arr[], int l, int m, int r) {

        // Find sizes of two subarrays to be merged
        int n1 = m - l + 1;
        int n2 = r - m;

        /* Create temp arrays */
        Runner L[] = new Runner [n1];
        Runner R[] = new Runner [n2];

        /*Copy data to temp arrays*/
        for (int i=0; i<n1; ++i)
            L[i] = arr[l + i];
        for (int j=0; j<n2; ++j)
            R[j] = arr[m + 1+ j];


        /* Merge the temp arrays */

        // Initial indexes of first and second subarrays
        int i = 0, j = 0;

        // Initial index of merged subarray array
        int k = l;
        while (i < n1 && j < n2)
        {
            if (L[i].getRace().compareTo(R[j].getRace()) <= 0)
            {
                arr[k] = L[i];
                i++;
            }
            else
            {
                arr[k] = R[j];
                j++;
            }
            k++;
        }

        /* Copy remaining elements of L[] if any */
        while (i < n1)
        {
            arr[k] = L[i];
            i++;
            k++;
        }

        /* Copy remaining elements of R[] if any */
        while (j < n2)
        {
            arr[k] = R[j];
            j++;
            k++;
        }
    }
}
