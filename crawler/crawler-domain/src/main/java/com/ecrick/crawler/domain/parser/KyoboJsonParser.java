package com.ecrick.crawler.domain.parser;

import com.ecrick.crawler.domain.dto.LibraryCrawlerDto;
import com.ecrick.crawler.domain.dto.KyoboJsonDto;
import com.ecrick.crawler.domain.dto.ResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.xml.bind.JAXBException;

import static org.jsoup.Connection.Response;

public class KyoboJsonParser extends BodyCleaner implements CrawlerParser {
    @Override
    public Boolean supports(LibraryCrawlerDto libraryDto) {
        return libraryDto.isKyoboJson();
    }

    @Override
    public ResponseDto parse(Response response) throws JAXBException, JsonProcessingException {
        return new ObjectMapper()
                .readValue(cleanBody(response.body()), KyoboJsonDto.class);
    }

    private String cleanBody(String body) {
        return clean(body)
                .replaceAll("&amp;|&ldquo;|&rdquo;|&lsquo;|&rsquo;|&AElig;", "");
    }
}
