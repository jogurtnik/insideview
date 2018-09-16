package uk.co.punishell.insideview.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class QueryFormController {

    public QueryFormController(){}

    @RequestMapping("/")
    public String getQueryFormWebsite(){

        return "query_form";
    }

}
