package uk.co.punishell.insideview.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import uk.co.punishell.insideview.model.database.services.RaceSearchEngine;
import uk.co.punishell.insideview.view.commands.guiCommands.RaceSearch;
import uk.co.punishell.insideview.view.commands.guiCommands.RaceSearchResult;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Slf4j
@Controller
@SessionAttributes("raceSearch")
public class QueryFormController {

    private RaceSearchEngine raceSearchEngine;

    @Autowired
    public QueryFormController(RaceSearchEngine raceSearchEngine) {
        this.raceSearchEngine = raceSearchEngine;
    }

    @GetMapping({"query", "/query", "query.html"})
    public String getQueryPage(HttpSession session) {



        return "query";
    }

    @PostMapping("performQuery")
    public String performQuery(@ModelAttribute("raceSearch") @Valid RaceSearch raceSearch,
                               BindingResult bindingResult,
                               HttpSession session) throws NumberFormatException {

        if (bindingResult.hasErrors()) {
            throw new NumberFormatException("At least of the number fields in the query form was blank instead of zero.");
        }

        RaceSearchResult raceSearchResult = raceSearchEngine.search(raceSearch);

        session.setAttribute("queryFormResult", raceSearchResult);

        return "redirect:/query";
    }

    @ModelAttribute("raceSearch")
    public RaceSearch getRaceSearchFormData() {
        return new RaceSearch();
    }

    @ExceptionHandler(NumberFormatException.class)
    public ModelAndView handleBindingError(Exception exception) {

        log.error("QUERY FORM OBJECT BINDING ERROR!");
        log.error(exception.getMessage());

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("QueryFormError");
        modelAndView.addObject("exception", exception);

        return modelAndView;
    }
}
