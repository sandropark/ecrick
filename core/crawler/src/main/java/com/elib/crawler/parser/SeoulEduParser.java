package com.elib.crawler.parser;

import com.elib.crawler.CrawlerParser;
import com.elib.crawler.dto.ResponseDto;
import com.elib.crawler.dto.SeoulEduDto;
import com.elib.domain.Library;

import javax.xml.bind.JAXBException;
import java.io.IOException;

import static org.jsoup.Connection.Response;

public class SeoulEduParser implements CrawlerParser {
    @Override
    public Boolean supports(Library library) {
        return library.isSeoulEdu();
    }

    @Override
    public ResponseDto parse(Response response) throws JAXBException, IOException {
        return new SeoulEduDto().init(response.parse());
    }
}
