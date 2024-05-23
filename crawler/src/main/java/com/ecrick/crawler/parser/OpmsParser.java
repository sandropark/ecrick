package com.ecrick.crawler.parser;

import com.ecrick.crawler.dto.LibraryCrawlerDto;
import com.ecrick.crawler.dto.OPMSDto;
import com.ecrick.crawler.dto.ResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Connection;

import jakarta.xml.bind.JAXBException;

public class OpmsParser extends BodyCleaner implements CrawlerParser {
    @Override
    public Boolean supports(LibraryCrawlerDto libraryDto) {
        return libraryDto.isOPMS();
    }

    @Override
    public ResponseDto parse(Connection.Response response) throws JAXBException, JsonProcessingException {
        return new ObjectMapper().readValue(clean(response.body()), OPMSDto.class);
    }
}
