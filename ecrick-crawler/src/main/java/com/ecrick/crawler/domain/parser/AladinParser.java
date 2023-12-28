package com.ecrick.crawler.domain.parser;

import com.ecrick.crawler.domain.webclient.JsoupResponse;
import com.ecrick.crawler.domain.webclient.response.AladinResponse;
import com.ecrick.crawler.domain.webclient.response.LibraryResponse;
import com.ecrick.entity.Library;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.StringReader;

@Component
public class AladinParser implements CrawlerParser {
    @Override
    public Boolean supports(Library library) {
        return library.isAladin();
    }

    @Override
    public LibraryResponse parse(JsoupResponse response) {
        try {
            return (LibraryResponse) JAXBContext.newInstance(AladinResponse.class)
                    .createUnmarshaller()
                    .unmarshal(new StringReader(response.body()));
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }
}
