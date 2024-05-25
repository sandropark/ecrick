package com.ecrick.crawler.domain.parser;

import com.ecrick.crawler.domain.dto.LibraryCrawlerDto;
import com.ecrick.crawler.domain.dto.KyoboXmlDto;
import com.ecrick.crawler.domain.dto.ResponseDto;
import org.jsoup.Connection;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
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
