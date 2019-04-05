package uk.co.punishell.insideview.controllers;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Controller
public class TradingAidController {

    @GetMapping("tradingAid")
    public String getTradingAid() throws IOException {

        Document doc = Jsoup.connect("https://juststarthere.co.uk/user/login.html").get();

        Element loginForm = doc.select("form").first();
        String absLoginURL = loginForm.attr("abs:action");

        Connection.Response res = Jsoup.connect(absLoginURL)
                    .timeout(10 * 1000)
                    .method(Connection.Method.POST)
                    .data("op", "login")
                    .data("usr_login", "gavinb")
                    .data("usr_password", "passw0rd")
                    .followRedirects(true)
                    .execute();

        Map<String, String> loginCookies = res.cookies();

        res = Jsoup.connect("https://juststarthere.co.uk/allwinback.html")
                .timeout(10*1000)
                .cookies(loginCookies)
                .followRedirects(true)
                .execute();

        doc = res.parse();

        Element raceDataTable = doc.getElementById("racedata");
        Elements raceDataTBodies = raceDataTable.getElementsByTag("tbody");

        return "tradingAid";
    }
}
