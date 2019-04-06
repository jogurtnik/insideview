package uk.co.punishell.insideview.controllers;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import uk.co.punishell.insideview.model.services.JSHDataScrapper.JSHRaceAssembler;
import uk.co.punishell.insideview.model.services.JSHDataScrapper.WebpageScrapper;
import uk.co.punishell.insideview.view.commands.entityCommands.JSHRaceCommand;
import uk.co.punishell.insideview.view.commands.guiCommands.TraidingAidCommand;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
public class TradingAidController {

    @Autowired
    private WebpageScrapper scrapper;

    @Autowired
    private JSHRaceAssembler jshRaceAssembler;

    private final String JSHLoginUrl = "https://juststarthere.co.uk/cgi-bin/just.pl";
    private final String JSHDataTargetUrl = "https://juststarthere.co.uk/allwinback.html";

    @GetMapping("tradingAid")
    public String getTradingAid(Model model) throws IOException {

        Map<String, String> data = new HashMap<>();
        data.put("op", "login");
        data.put("usr_login", "gavinb");
        data.put("usr_password", "passw0rd");

        Document doc = scrapper.loginAndGetWebpage(JSHLoginUrl, data, JSHDataTargetUrl);

        Elements raceInfo = doc.getElementsByClass("race_infoback");
        Elements racebody = doc.getElementsByClass("racebody");

        TraidingAidCommand traidingAidCommand = new TraidingAidCommand();

        if (raceInfo != null &&
            raceInfo.size() > 0 &&
            raceInfo.size() == racebody.size()) {

            List<JSHRaceCommand> races = traidingAidCommand.getRaces();

            for (int i = 0; i < raceInfo.size(); i++) {

                Element raceInfoElement = raceInfo.get(i);
                Element racebodyElement = racebody.get(i);

                JSHRaceCommand race = jshRaceAssembler.getJshRace(raceInfoElement, racebodyElement);

                races.add(race);

            }

            traidingAidCommand.setRaces(races);

        } else {
            // TODO
            // implement "No races available" message
        }

        model.addAttribute("jshData", traidingAidCommand);

        return "tradingAid";
    }
}
