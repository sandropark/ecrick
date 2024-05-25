package com.ecrick.crawler.domain.parser;

import com.ecrick.crawler.domain.dto.LibraryCrawlerDto;
import com.ecrick.crawler.domain.dto.ResponseDto;
import com.ecrick.crawler.domain.dto.SeoulEduDto;
import org.jsoup.Connection;

import jakarta.xml.bind.JAXBException;
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
