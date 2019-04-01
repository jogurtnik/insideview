package uk.co.punishell.insideview.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class TradingAidController {

    @GetMapping("tradingAid")
    public String getTradingAid() {
        return "tradingAid";
    }
}
