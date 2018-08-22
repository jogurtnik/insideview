package uk.co.punishell.insideview.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class OverviewController {

    public OverviewController(){}

    @RequestMapping("/")
    public String getOverviewWebsite(){

        return "query";
    }
}
