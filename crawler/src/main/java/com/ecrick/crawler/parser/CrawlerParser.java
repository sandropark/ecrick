package com.ecrick.crawler.parser;

import com.ecrick.crawler.dto.LibraryCrawlerDto;
import com.ecrick.crawler.dto.ResponseDto;

import javax.xml.bind.JAXBException;
import java.io.IOException;

import static org.jsoup.Connection.Response;

public interface CrawlerParser {
    Boolean supports(LibraryCrawlerDto libraryDto);
    ResponseDto parse(Response response) throws JAXBException, IOException;
}
