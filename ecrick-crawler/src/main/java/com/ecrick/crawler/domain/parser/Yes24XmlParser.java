package com.ecrick.crawler.domain.parser;

import com.ecrick.crawler.domain.webclient.JsoupResponse;
import com.ecrick.crawler.domain.webclient.response.LibraryResponse;
import com.ecrick.crawler.domain.webclient.response.Yes24XmlResponse;
import com.ecrick.entity.Library;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.StringReader;

@Component
public class Yes24XmlParser implements CrawlerParser {
    @Override
    public Boolean supports(Library library) {
        return library.isYes24Xml();
    }

    @Override
    public LibraryResponse parse(JsoupResponse response) {
        try {
            return (LibraryResponse) JAXBContext
                    .newInstance(Yes24XmlResponse.class)
                    .createUnmarshaller()
                    .unmarshal(new StringReader(response.body()));
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

//    private String cleanBody(String body) {
//        return clean(body)
//                .replaceAll("[^\\u0009\\u000A\\u000D\\u0020-\\uD7FF\\uE000-\\uFFFD\\u10000-\\u10FFF]+", "");
//    }
}
