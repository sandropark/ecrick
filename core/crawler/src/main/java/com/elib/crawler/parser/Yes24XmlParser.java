package com.elib.crawler.parser;

import com.elib.crawler.CrawlerParser;
import com.elib.crawler.dto.ResponseDto;
import com.elib.crawler.dto.Yes24XmlDto;
import com.elib.domain.Library;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.jsoup.Connection;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.StringReader;

public class Yes24XmlParser implements CrawlerParser {
    @Override
    public Boolean supports(Library library) {
        return library.isYes24Xml();
    }

    @Override
    public ResponseDto parse(Connection.Response response) throws JAXBException, JsonProcessingException {
        return (ResponseDto) JAXBContext
                .newInstance(Yes24XmlDto.class)
                .createUnmarshaller()
                .unmarshal(new StringReader(response.body()
                        .replaceAll("[^\\u0009\\u000A\\u000D\\u0020-\\uD7FF\\uE000-\\uFFFD\\u10000-\\u10FFF]+", ""))); // xml
    }
}
