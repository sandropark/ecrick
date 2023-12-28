package com.ecrick.webclient;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;

import java.io.IOException;

@Slf4j
public class WebClient {
    private static final String USER_AGENT =
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/106.0.5249.207 Whale/3.17.145.18 Safari/537.36";

    public static String execute(String url) {
        try {
            log.info("url: {}", url);
            return Jsoup.connect(url)
                    .userAgent(USER_AGENT)
                    .ignoreContentType(true)
                    .timeout(60000)
                    .execute()
                    .body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
