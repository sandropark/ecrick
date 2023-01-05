package com.elib.crawler.parser;

import com.elib.crawler.CrawlerParser;
import com.elib.crawler.dto.KyoboJsonDto;
import com.elib.crawler.dto.ResponseDto;
import com.elib.domain.Library;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.xml.bind.JAXBException;

import static org.jsoup.Connection.Response;

public class KyoboJsonParser implements CrawlerParser {
    @Override
    public Boolean supports(Library library) {
        return library.isKyoboJson();
    }

    @Override
    public ResponseDto parse(Response response) throws JAXBException, JsonProcessingException {
        return new ObjectMapper()
                .readValue(getBody(response), KyoboJsonDto.class);
    }

    private String getBody(Response response) {
        return response.body()
                .replaceAll("&amp;|&lt;|&gt;|&ldquo;|&rdquo;|&lsquo;|&rsquo;|&AElig;", "");
    }
}
