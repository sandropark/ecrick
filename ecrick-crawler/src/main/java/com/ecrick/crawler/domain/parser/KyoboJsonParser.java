package com.ecrick.crawler.domain.parser;

import com.ecrick.crawler.domain.webclient.JsoupResponse;
import com.ecrick.crawler.domain.webclient.response.KyoboJsonResponse;
import com.ecrick.crawler.domain.webclient.response.LibraryResponse;
import com.ecrick.entity.Library;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class KyoboJsonParser implements CrawlerParser {
    private final ObjectMapper objectMapper;

    @Override
    public Boolean supports(Library library) {
        return library.isKyoboJson();
    }

    @Override
    public LibraryResponse parse(JsoupResponse response) {
        try {
            return objectMapper.readValue(response.body(), KyoboJsonResponse.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private String cleanBody(String body) {
        return body.replaceAll("&amp;|&ldquo;|&rdquo;|&lsquo;|&rsquo;|&AElig;", "");
    }
}
