package com.ecrick.crawler.domain.parser;

import com.ecrick.crawler.domain.dto.LibraryCrawlerDto;
import com.ecrick.crawler.domain.dto.ResponseDto;

import jakarta.xml.bind.JAXBException;
import java.io.IOException;

import static org.jsoup.Connection.Response;

public interface CrawlerParser {
    Boolean supports(LibraryCrawlerDto libraryDto);
    ResponseDto parse(Response response) throws JAXBException, IOException;
}
