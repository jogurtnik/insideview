package uk.co.punishell.insideview.model.services.JSHDataScrapper;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.co.punishell.insideview.view.commands.entityCommands.JSHRaceCommand;
import uk.co.punishell.insideview.view.commands.entityCommands.JSHRunnerCommand;

import java.util.ArrayList;
import java.util.List;

@Service
public class JSHRaceAssembler {

    @Autowired
    private JSHRunnerAssembler jshRunnerAssembler;

    public JSHRaceCommand getJshRace(Element raceInfo, Element raceBody) {

        JSHRaceCommand race = new JSHRaceCommand();

        race.setGeneralInfo(this.getGeneralRaceInfo(raceInfo));
        race.setRunners(this.getJshRunners(raceBody));

        return race;
    }

    private String getGeneralRaceInfo(Element raceInfo) {
        return raceInfo.getElementsByClass("race_infoback").get(0).text();
    }

    private List<JSHRunnerCommand> getJshRunners(Element raceBody) {

        List<JSHRunnerCommand> runners = new ArrayList<>();

        Elements tableRows = raceBody.getElementsByTag("tr");

        for (Element row : tableRows) {
            JSHRunnerCommand runner = jshRunnerAssembler.getJshRunner(row);
            runners.add(runner);
        }

        return runners;
    }
}
