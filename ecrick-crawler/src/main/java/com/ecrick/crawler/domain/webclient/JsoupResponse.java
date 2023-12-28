package com.ecrick.crawler.domain.webclient;

import lombok.AllArgsConstructor;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;

import java.io.IOException;

@AllArgsConstructor
public class JsoupResponse {
    private String body;
    private Document document;


    public static JsoupResponse of(Connection.Response response) {
        try {
            return new JsoupResponse(response.body(), response.parse());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String body() {
        return preProcessBody();
    }

    public Document document() {
        return document;
    }

    private String preProcessBody() {
        return body
                .replaceAll("&gt;.{2}&lt;", "")
                .replaceAll("&[a-zA-Z]{1,5};", "");
    }

}
