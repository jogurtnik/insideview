package uk.co.punishell.insideview.model.services.JSHDataScrapper;

import org.springframework.stereotype.Service;
import uk.co.punishell.insideview.view.commands.entityCommands.JSHRaceCommand;
import uk.co.punishell.insideview.view.commands.entityCommands.JSHRunnerCommand;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JSHTradingAidAssembler {

    public List<JSHRaceCommand> assembleRaceData(List<JSHRaceCommand> races) {

        Map<String, Integer> jockeyWinsMap = new HashMap<>();
        Map<String, Integer> jockeyRidesMap = new HashMap<>();
        Map<String, Integer> trainerWinsMap = new HashMap<>();
        Map<String, Integer> trainerRunnersMap = new HashMap<>();

        for (int i = 0; i < races.size(); i++) {
            for (int j = 0; j < races.get(i).getRunners().size(); j++) {

                JSHRunnerCommand runner = races.get(i).getRunners().get(j);

                // exclude non-runners form the count
                if (!runner.getHorseName().contains("NR")) {

                    String jockey = runner.getJockey();
                    String trainer = runner.getTrainer();

                    if (!jockeyRidesMap.containsKey(jockey)) {

                        int rides = 1;
                        int jockeyWins = 0;

                        jockeyRidesMap.put(jockey, rides);
                        jockeyWinsMap.put(jockey, jockeyWins);
                        runner.setJockeyRideNo(rides);

                        if (runner.getResult().equalsIgnoreCase("won")) {
                            jockeyWinsMap.put(jockey, ++jockeyWins);
                            runner.setJockeyWins(jockeyWins);
                        }

                    } else {

                        int rides = jockeyRidesMap.get(jockey);
                        int jockeyWins = jockeyWinsMap.get(jockey);

                        jockeyRidesMap.put(jockey, ++rides);
                        runner.setJockeyRideNo(rides);

                        if (runner.getResult().equalsIgnoreCase("won")) {
                            jockeyWinsMap.put(jockey, ++jockeyWins);
                            runner.setJockeyWins(jockeyWins);
                        } else {
                            runner.setJockeyWins(jockeyWins);
                        }
                    }

                    if (!trainerRunnersMap.containsKey(trainer)) {

                        int runners = 1;
                        int trainerWins = 0;

                        trainerRunnersMap.put(trainer, runners);
                        trainerWinsMap.put(trainer, trainerWins);
                        runner.setTrainerRunnerNo(runners);

                        if (runner.getResult().equalsIgnoreCase("won")) {
                            trainerWinsMap.put(trainer, ++trainerWins);
                            runner.setTrainerWins(trainerWins);
                        }

                    } else {

                        int runners = trainerRunnersMap.get(trainer);
                        int trainerWins = trainerWinsMap.get(trainer);

                        trainerRunnersMap.put(trainer, ++runners);
                        runner.setTrainerRunnerNo(runners);

                        if (runner.getResult().equalsIgnoreCase("won")) {
                            trainerWinsMap.put(trainer, ++trainerWins);
                            runner.setTrainerWins(trainerWins);
                        } else {
                            runner.setTrainerWins(trainerWins);
                        }
                    }
                }


            }
        }

        for (JSHRaceCommand race : races) {
            for (JSHRunnerCommand runner : race.getRunners()) {

                if (!runner.getHorseName().contains("NR")) {

                    String jockey = runner.getJockey();
                    String trainer = runner.getTrainer();

                    runner.setJockeyRides(jockeyRidesMap.get(jockey));
                    runner.setTrainerRunners(trainerRunnersMap.get(trainer));
                }
            }
        }

        return races;
    }
}
