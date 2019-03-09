package uk.co.punishell.insideview.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping({"", "/"})
    public String getDefaultPage() {

        return "redirect:/query";
    }
}
