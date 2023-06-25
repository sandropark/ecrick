package com.elib.crawler.parser;

import com.elib.crawler.dto.LibraryCrawlerDto;
import com.elib.crawler.dto.KyoboJsonDto;
import com.elib.crawler.dto.ResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.xml.bind.JAXBException;

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
