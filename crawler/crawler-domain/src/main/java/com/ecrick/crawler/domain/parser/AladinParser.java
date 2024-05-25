package com.ecrick.crawler.domain.parser;

import com.ecrick.crawler.domain.dto.LibraryCrawlerDto;
import com.ecrick.crawler.domain.dto.AladinDto;
import com.ecrick.crawler.domain.dto.ResponseDto;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import org.jsoup.Connection;

import java.io.StringReader;

public class AladinParser extends BodyCleaner implements CrawlerParser {
    @Override
    public Boolean supports(LibraryCrawlerDto libraryDto) {
        return libraryDto.isAladin();
    }

    @Override
    public ResponseDto parse(Connection.Response response) throws JAXBException {
        return (ResponseDto) JAXBContext.newInstance(AladinDto.class)
                .createUnmarshaller()
                .unmarshal(new StringReader(clean(response.body())));
    }
}
