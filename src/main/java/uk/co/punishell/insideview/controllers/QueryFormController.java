package uk.co.punishell.insideview.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import uk.co.punishell.insideview.model.commands.QueryFormDataCommand;
import uk.co.punishell.insideview.model.commands.RaceCommand;
import uk.co.punishell.insideview.model.converters.RaceToRaceCommand;
import uk.co.punishell.insideview.model.database.services.QueryConversionService;
import uk.co.punishell.insideview.model.database.services.RaceService;

import javax.servlet.http.HttpSession;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@Controller
public class QueryFormController {

    QueryConversionService queryConversionService;
    RaceService raceService;
    RaceToRaceCommand raceToRaceCommand;


    public QueryFormController(QueryConversionService queryConversionService, RaceToRaceCommand raceToRaceCommand, RaceService raceService) {
        this.queryConversionService = queryConversionService;
        this.raceToRaceCommand = raceToRaceCommand;
        this.raceService = raceService;
    }

    @GetMapping({"query", "/query", "query.html"})
    public String getQueryForm(Model model) {


        model.addAttribute("queryFormData", new QueryFormDataCommand());



        return "query";
    }

    @PostMapping("performQuery")
    public String performQuery(@ModelAttribute QueryFormDataCommand queryFormDataCommand, HttpSession session) {

        List<RaceCommand> races = new LinkedList<>();

        raceService.getRaces().iterator().forEachRemaining(race -> races.add(raceToRaceCommand.convert(race)));

        log.debug("RaceCommand list size: " + races.size());
        log.debug("RunnerCommand list size of first RaceCommand: " + ((LinkedList<RaceCommand>) races).getFirst().getRunners().size());

        session.setAttribute("races", races);
        session.setAttribute("queryFormData", queryFormDataCommand);

        return "redirect:/query";
    }
}
