package com.ecrick.crawler.domain.parser;

import com.ecrick.crawler.domain.webclient.JsoupResponse;
import com.ecrick.crawler.domain.webclient.response.KyoboXmlResponse;
import com.ecrick.crawler.domain.webclient.response.LibraryResponse;
import com.ecrick.entity.Library;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.StringReader;

@Component
public class KyoboXmlParser implements CrawlerParser {

    @Override
    public Boolean supports(Library library) {
        return library.isKyoboXml();
    }

    @Override
    public LibraryResponse parse(JsoupResponse response) {
        try {
            return (LibraryResponse) JAXBContext
                    .newInstance(KyoboXmlResponse.class)
                    .createUnmarshaller()
                    .unmarshal(new StringReader(response.body()));
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

//    private String cleanBody(String body) {
//        return clean(body)
//                .replaceAll("&apos;", "'");
//    }

}
