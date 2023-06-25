package com.elib.crawler.parser;

import com.elib.crawler.dto.LibraryCrawlerDto;
import com.elib.crawler.dto.ResponseDto;
import com.elib.crawler.dto.Yes24XmlDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.jsoup.Connection;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.StringReader;

public class Yes24XmlParser extends BodyCleaner implements CrawlerParser {
    @Override
    public Boolean supports(LibraryCrawlerDto libraryDto) {
        return libraryDto.isYes24Xml();
    }

    @Override
    public ResponseDto parse(Connection.Response response) throws JAXBException, JsonProcessingException {
        return (ResponseDto) JAXBContext
                .newInstance(Yes24XmlDto.class)
                .createUnmarshaller()
                .unmarshal(new StringReader(cleanBody(response.body()))); // xml
    }

    private String cleanBody(String body) {
        return clean(body)
                .replaceAll("[^\\u0009\\u000A\\u000D\\u0020-\\uD7FF\\uE000-\\uFFFD\\u10000-\\u10FFF]+", "");

    }
}
