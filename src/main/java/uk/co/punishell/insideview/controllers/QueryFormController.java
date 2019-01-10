package uk.co.punishell.insideview.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import uk.co.punishell.insideview.model.services.web.commands.guiCommands.QueryFormResultCommand;
import uk.co.punishell.insideview.model.services.web.commands.guiCommands.RaceSearch;

import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@SessionAttributes("raceSearch")
public class QueryFormController {

    private QueryFormResultCommand queryFormResult;

    @GetMapping({"query", "/query", "query.html"})
    public String getQueryPage(HttpSession session) {

        return "query";
    }

    @PostMapping("performQuery")
    public String performQuery(@ModelAttribute("raceSearch") RaceSearch raceSearch,
                               HttpSession session) {



        session.setAttribute("queryFormResult", queryFormResult);

        return "redirect:/query";
    }

    @ModelAttribute("raceSearch")
    public RaceSearch getRaceSearchFormData() {
        return new RaceSearch();
    }
}
