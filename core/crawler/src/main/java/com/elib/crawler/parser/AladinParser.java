package com.elib.crawler.parser;

import com.elib.crawler.CrawlerParser;
import com.elib.crawler.dto.AladinDto;
import com.elib.crawler.dto.ResponseDto;
import com.elib.domain.Library;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.jsoup.Connection;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.StringReader;

public class AladinParser implements CrawlerParser {
    @Override
    public Boolean supports(Library library) {
        return library.isAladin();
    }

    @Override
    public ResponseDto parse(Connection.Response response) throws JAXBException, JsonProcessingException {
        return (ResponseDto) JAXBContext.newInstance(AladinDto.class)
                .createUnmarshaller()
                .unmarshal(new StringReader(response.body()));
    }
}
