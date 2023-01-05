package com.elib.crawler;

import com.elib.crawler.dto.ResponseDto;
import com.elib.domain.Library;

import javax.xml.bind.JAXBException;
import java.io.IOException;

import static org.jsoup.Connection.Response;

public interface CrawlerParser {
    Boolean supports(Library library);
    ResponseDto parse(Response response) throws JAXBException, IOException;
}
