package uk.co.punishell.insideview.model.services.JSHDataScrapper;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;
import uk.co.punishell.insideview.model.exceptions.VendorsConnectionException;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Map;

@Slf4j
@Service
public class WebpageScrapper {

    private int connectionTimeout = 30*1000;
    private boolean followRedirects = true;

    public Document getWebpage(String url) throws IOException {

        return Jsoup.connect(url).get();
    }

    public Document loginAndGetWebpage(String loginUrl, Map<String, String> loginFormData, String targetUrl) {

        try {
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
        }  catch (IOException e) {
            if (e instanceof SocketTimeoutException) {
                throw new VendorsConnectionException("Unfortunately the the attempt to communicate with data vendor has reach the timeout of 30 seconds.");
            } else {
                throw new VendorsConnectionException("Data vendor is not responding.");
            }
        }


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
