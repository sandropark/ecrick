package com.elib.crawler.parser;

import com.elib.crawler.dto.LibraryCrawlerDto;
import com.elib.crawler.dto.AladinDto;
import com.elib.crawler.dto.ResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.jsoup.Connection;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.StringReader;

public class AladinParser extends BodyCleaner implements CrawlerParser {
    @Override
    public Boolean supports(LibraryCrawlerDto libraryDto) {
        return libraryDto.isAladin();
    }

    @Override
    public ResponseDto parse(Connection.Response response) throws JAXBException, JsonProcessingException {
        return (ResponseDto) JAXBContext.newInstance(AladinDto.class)
                .createUnmarshaller()
                .unmarshal(new StringReader(clean(response.body())));
    }
}
