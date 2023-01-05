package com.elib.crawler.parser;

import com.elib.crawler.CrawlerParser;
import com.elib.crawler.dto.OPMSDto;
import com.elib.crawler.dto.ResponseDto;
import com.elib.domain.Library;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Connection;

import javax.xml.bind.JAXBException;

public class OpmsParser implements CrawlerParser {
    @Override
    public Boolean supports(Library library) {
        return library.isOPMS();
    }

    @Override
    public ResponseDto parse(Connection.Response response) throws JAXBException, JsonProcessingException {
        return new ObjectMapper().readValue(response.body(), OPMSDto.class);
    }
}
