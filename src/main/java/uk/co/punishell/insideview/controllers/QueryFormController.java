package uk.co.punishell.insideview.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import uk.co.punishell.insideview.model.database.services.QueryFormConversionService;
import uk.co.punishell.insideview.model.services.web.forms.QueryFormData;
import uk.co.punishell.insideview.model.services.web.forms.QueryFormResult;

import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@SessionAttributes("queryFormData")
public class QueryFormController {

    private QueryFormConversionService queryFormConversionService;

    private QueryFormResult queryFormResult;

    @Autowired
    public QueryFormController(QueryFormConversionService queryFormConversionService) {
        this.queryFormConversionService = queryFormConversionService;
    }

    @GetMapping({"query", "/query", "query.html"})
    public String getQueryPage() {

        return "query";
    }

    @PostMapping("performQuery")
    public String performQuery(@ModelAttribute("queryFormData") QueryFormData queryFormData,
                               HttpSession session) {

        queryFormResult = queryFormConversionService.convert(queryFormData);

        session.setAttribute("queryFormResult", queryFormResult);

        return "redirect:/query";
    }

    @ModelAttribute("queryFormData")
    public QueryFormData getQueryFormData() {
        return new QueryFormData();
    }
}
