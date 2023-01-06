package com.elib.crawler.parser;

import com.elib.crawler.CrawlerParser;
import com.elib.crawler.LibraryCrawlerDto;
import com.elib.crawler.responsedto.ResponseDto;
import com.elib.crawler.responsedto.SeoulLibDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Connection;

import javax.xml.bind.JAXBException;

public class SeoulLibCleaner extends BodyCleaner implements CrawlerParser {
    @Override
    public Boolean supports(LibraryCrawlerDto libraryDto) {
        return libraryDto.isSeoulLib();
    }

    @Override
    public ResponseDto parse(Connection.Response response) throws JAXBException, JsonProcessingException {
        return new ObjectMapper().readValue(clean(response.body()), SeoulLibDto.class);
    }

}
