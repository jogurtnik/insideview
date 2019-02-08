package uk.co.punishell.insideview.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import uk.co.punishell.insideview.model.database.services.RaceSearchEngine;
import uk.co.punishell.insideview.model.database.services.RunnerSearchEngine;
import uk.co.punishell.insideview.view.commands.guiCommands.RaceSearch;
import uk.co.punishell.insideview.view.commands.guiCommands.RaceSearchResult;
import uk.co.punishell.insideview.view.commands.guiCommands.RunnerSeachResult;
import uk.co.punishell.insideview.view.commands.guiCommands.RunnerSearch;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Slf4j
@Controller
@SessionAttributes({"raceSearch", "runnerSearch"})
public class QueryFormController {

    private RaceSearchEngine raceSearchEngine;
    private RunnerSearchEngine runnerSearchEngine;

    @Autowired
    public QueryFormController(RaceSearchEngine raceSearchEngine, RunnerSearchEngine runnerSearchEngine) {
        this.raceSearchEngine = raceSearchEngine;
        this.runnerSearchEngine = runnerSearchEngine;
    }

    @GetMapping({"query", "/query", "query.html"})
    public String getQueryPage(HttpSession session) {

        return "query";
    }

    @PostMapping("performRaceQuery")
    public String performRaceQuery(@ModelAttribute("raceSearch") @Valid RaceSearch raceSearch,
                               BindingResult bindingResult,
                               HttpSession session) throws Exception {

        if (bindingResult.hasErrors()) {
            throw new Exception("At least one of the number fields in the query form was blank instead of zero.");
        }

        RaceSearchResult raceSearchResult = raceSearchEngine.search(raceSearch);

        session.setAttribute("raceSearchResult", raceSearchResult);

        return "redirect:/query";
    }

    @PostMapping("performRunnerQuery")
    public String performRunnerQuery(@ModelAttribute("runnerSearch") @Valid RunnerSearch runnerSearch,
                                     BindingResult bindingResult,
                                     HttpSession session) throws Exception {
        if (bindingResult.hasErrors()) {
            throw new Exception("At least one of the number fields in the query form was blank instead of zero.");
        }

        RunnerSeachResult runnerSeachResult = runnerSearchEngine.search(runnerSearch);

        session.setAttribute("runnerSearchResult", runnerSeachResult);

        return "redirect:/query";
    }


    @ModelAttribute("raceSearch")
    public RaceSearch getRaceSearchFormData() {

        return new RaceSearch();
    }

    @ModelAttribute("runnerSearch")
    public RunnerSearch gerRunnerSearchFormData() {
        return new RunnerSearch();
    }
}
