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

                if (!jockeyRidesMap.containsKey(runner.getJockey())) {

                    jockeyRidesMap.put(runner.getJockey(), 1);
                    jockeyWinsMap.put(runner.getJockey(), 0);
                    runner.setJockeyRideNo(jockeyRidesMap.get(runner.getJockey()));

                    if (runner.getResult().equalsIgnoreCase("won")) {
                        jockeyWinsMap.put(runner.getJockey(), jockeyWinsMap.get(runner.getJockey()) + 1);
                    }

                } else {

                    jockeyRidesMap.put(runner.getJockey(), jockeyRidesMap.get(runner.getJockey()) + 1);
                    runner.setJockeyRideNo(jockeyRidesMap.get(runner.getJockey()));

                    if (runner.getResult().equalsIgnoreCase("won")) {
                        jockeyWinsMap.put(runner.getJockey(), jockeyWinsMap.get(runner.getJockey()) + 1);
                    }
                }

                if (!trainerRunnersMap.containsKey(runner.getTrainer())) {

                    trainerRunnersMap.put(runner.getTrainer(), 1);
                    trainerWinsMap.put(runner.getTrainer(), 0);
                    runner.setTrainerRunnerNo(trainerRunnersMap.get(runner.getTrainer()));

                    if (runner.getResult().equalsIgnoreCase("won")) {
                        trainerWinsMap.put(runner.getTrainer(), trainerWinsMap.get(runner.getTrainer()) + 1);
                    }

                } else {

                    trainerRunnersMap.put(runner.getTrainer(), trainerRunnersMap.get(runner.getTrainer()) + 1);
                    runner.setTrainerRunnerNo(trainerRunnersMap.get(runner.getTrainer()));

                    if (runner.getResult().equalsIgnoreCase("won")) {
                        trainerWinsMap.put(runner.getTrainer(), trainerWinsMap.get(runner.getTrainer()) + 1);
                    }
                }
            }
        }

        for (JSHRaceCommand race : races) {
            for (JSHRunnerCommand runner : race.getRunners()) {

                String jockey = runner.getJockey();
                String trainer = runner.getTrainer();

                runner.setJockeyRides(jockeyRidesMap.get(jockey));
                runner.setJockeyWins(jockeyWinsMap.get(jockey));
                runner.setTrainerRunners(trainerRunnersMap.get(trainer));
                runner.setTrainerWins(trainerWinsMap.get(trainer));
            }
        }

        return races;
    }
}
