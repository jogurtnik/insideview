package uk.co.punishell.insideview.model.services.JSHDataScrapper;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Service
public class WebpageScrapper {

    private int connectionTimeout = 10*1000;
    private boolean followRedirects = true;

    public Document getWebpage(String url) throws IOException {

        return Jsoup.connect(url).get();
    }

    public Document loginAndGetWebpage(String loginUrl, Map<String, String> loginFormData, String targetUrl)
        throws IOException{

        Connection.Response res = Jsoup.connect(loginUrl)
                .timeout(connectionTimeout)
                .method(Connection.Method.POST)
                .data(loginFormData)
                .followRedirects(followRedirects)
                .execute();

        Map<String, String> loginCookies = res.cookies();

        res = Jsoup.connect(targetUrl)
                .timeout(connectionTimeout)
                .cookies(loginCookies)
                .followRedirects(followRedirects)
                .execute();

        return res.parse();
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public boolean isFollowRedirects() {
        return followRedirects;
    }

    public void setFollowRedirects(boolean followRedirects) {
        this.followRedirects = followRedirects;
    }
}
