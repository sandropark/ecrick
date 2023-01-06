package com.elib.crawler;

import com.elib.crawler.responsedto.ResponseDto;

import javax.xml.bind.JAXBException;
import java.io.IOException;

import static org.jsoup.Connection.Response;

public interface CrawlerParser {
    Boolean supports(LibraryCrawlerDto libraryDto);
    ResponseDto parse(Response response) throws JAXBException, IOException;
}
