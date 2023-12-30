package com.ecrick.crawler.parser;

import com.ecrick.crawler.dto.LibraryCrawlerDto;
import com.ecrick.crawler.dto.KyoboXmlDto;
import com.ecrick.crawler.dto.ResponseDto;
import org.jsoup.Connection;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.StringReader;

public class KyoboXmlParser extends BodyCleaner implements CrawlerParser {

    @Override
    public Boolean supports(LibraryCrawlerDto libraryDto) {
        return libraryDto.isKyoboXml();
    }

    @Override
    public ResponseDto parse(Connection.Response response) throws JAXBException {
        return (ResponseDto) JAXBContext
                .newInstance(KyoboXmlDto.class)
                .createUnmarshaller()
                .unmarshal(new StringReader(cleanBody(response.body())));
    }

    private String cleanBody(String body) {
        return clean(body)
                .replaceAll("&apos;", "'");
    }

}