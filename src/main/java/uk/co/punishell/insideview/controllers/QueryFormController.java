package uk.co.punishell.insideview.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import uk.co.punishell.insideview.model.database.services.RaceSearchEngine;
import uk.co.punishell.insideview.model.database.services.RunnerSearchEngine;
import uk.co.punishell.insideview.model.exceptions.BindingFormException;
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

    @GetMapping({"query", "query.html"})
    public String getQueryPage() {

        return "query";
    }

    @PostMapping("performRaceQuery")
    public String performRaceQuery(@ModelAttribute("raceSearch") @Valid RaceSearch raceSearch,
                               BindingResult bindingResult,
                               HttpSession session) throws BindingFormException {

        if (bindingResult.hasErrors()) {
            throw new BindingFormException("At least one of the number fields in the query form was blank instead of zero.");
        }

        RaceSearchResult raceSearchResult = raceSearchEngine.search(raceSearch);

        session.setAttribute("raceSearchResult", raceSearchResult);

        return "redirect:/query";
    }

    @PostMapping("performRunnerQuery")
    public String performRunnerQuery(@ModelAttribute("runnerSearch") @Valid RunnerSearch runnerSearch,
                                     BindingResult bindingResult,
                                     HttpSession session) throws BindingFormException {
        if (bindingResult.hasErrors()) {
            throw new BindingFormException("There has been unidentified issue while processing the query form.");
        }

        RunnerSeachResult runnerSeachResult = runnerSearchEngine.search(runnerSearch);

        session.setAttribute("runnerSearchResult", runnerSeachResult);

        return "redirect:/query";
    }

    @RequestMapping({"clearRaceAndRunnerForms", "/clearRaceAndRunnerForms"})
    public String clearRaceAndRunnerForms(HttpSession session) {

        session.setAttribute("raceSearch", new RaceSearch());
        session.setAttribute("runnerSearch", new RunnerSearch());

        return "redirect:/query";
    }

    @RequestMapping({"clearRaceForm", "/clearRaceForm"})
    public String clearRaceForm(@ModelAttribute("raceSearch") RaceSearch raceSearch,
                                Model model) {
        model.addAttribute("raceSearch", new RaceSearch());
        return "redirect:/query";
    }

    @RequestMapping({"clearRaceResults", "/clearRaceResults"})
    public String clearRaceResults(HttpSession session) {
        session.setAttribute("raceSearchResult", null);
        return "redirect:/query";
    }

    @RequestMapping({"clearRunnerForm", "/clearRunnerForm"})
    public String clearRunnerForm(@ModelAttribute("runnerSearch") RunnerSearch runnerSearch,
                                  Model model) {
        model.addAttribute("runnerSearch", new RunnerSearch());
        return "redirect:/query";
    }

    @RequestMapping({"clearRunnerResults", "/clearRunnerResults"})
    public String clearRunnerResults(@ModelAttribute("runnerSearchResult") RunnerSeachResult runnerSeachResult,
                                     HttpSession session) {
        session.setAttribute("runnerSearchResult", null);
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
