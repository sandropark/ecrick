package com.ecrick.crawler.domain.parser;

import com.ecrick.crawler.domain.webclient.JsoupResponse;
import com.ecrick.crawler.domain.webclient.response.LibraryResponse;
import com.ecrick.crawler.domain.webclient.response.OPMSResponse;
import com.ecrick.entity.Library;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class OpmsParser implements CrawlerParser {
    private final ObjectMapper objectMapper;

    @Override
    public Boolean supports(Library library) {
        return library.isOPMS();
    }

    @Override
    public LibraryResponse parse(JsoupResponse response) {
        try {
            return objectMapper.readValue(response.body(), OPMSResponse.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
