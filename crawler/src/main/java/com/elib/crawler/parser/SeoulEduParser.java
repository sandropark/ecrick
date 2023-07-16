package com.elib.crawler.parser;

import com.elib.crawler.dto.LibraryCrawlerDto;
import com.elib.crawler.dto.ResponseDto;
import com.elib.crawler.dto.SeoulEduDto;
import org.jsoup.Connection;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public class SeoulEduParser implements CrawlerParser {
    @Override
    public Boolean supports(LibraryCrawlerDto libraryDto) {
        return libraryDto.isSeoulEdu();
    }

    @Override
    public ResponseDto parse(Connection.Response response) throws JAXBException, IOException {
        return new SeoulEduDto().init(response.parse());
    }
}