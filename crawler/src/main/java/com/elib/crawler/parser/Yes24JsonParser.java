package com.elib.crawler.parser;

import com.elib.crawler.dto.LibraryCrawlerDto;
import com.elib.crawler.dto.ResponseDto;
import com.elib.crawler.dto.Yes24JsonDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.xml.bind.JAXBException;

import static org.jsoup.Connection.Response;

public class Yes24JsonParser extends BodyCleaner implements CrawlerParser {
    @Override
    public Boolean supports(LibraryCrawlerDto libraryDto) {
        return libraryDto.isYes24Json();
    }

    @Override
    public ResponseDto parse(Response response) throws JAXBException, JsonProcessingException {
        return new ObjectMapper().readValue(clean(response.body()), Yes24JsonDto.class);
    }
}
