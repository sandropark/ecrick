package com.elib.crawler.parser;

import com.elib.crawler.CrawlerParser;
import com.elib.crawler.dto.ResponseDto;
import com.elib.crawler.dto.SeoulLibDto;
import com.elib.domain.Library;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Connection;

import javax.xml.bind.JAXBException;

public class SeoulLibParser implements CrawlerParser {
    @Override
    public Boolean supports(Library library) {
        return library.isSeoulLib();
    }

    @Override
    public ResponseDto parse(Connection.Response response) throws JAXBException, JsonProcessingException {
        return new ObjectMapper().readValue(response.body(), SeoulLibDto.class);
    }
}
