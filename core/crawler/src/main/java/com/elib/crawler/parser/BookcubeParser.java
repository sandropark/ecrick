package com.elib.crawler.parser;

import com.elib.crawler.CrawlerParser;
import com.elib.crawler.dto.BookcubeDto;
import com.elib.crawler.dto.ResponseDto;
import com.elib.domain.Library;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.xml.bind.JAXBException;

import static org.jsoup.Connection.Response;

public class BookcubeParser implements CrawlerParser {
    @Override
    public Boolean supports(Library library) {
        return library.isBookcube();
    }

    @Override
    public ResponseDto parse(Response response) throws JAXBException, JsonProcessingException {
        return new ObjectMapper().readValue(getBody(response), BookcubeDto.class);  // "\N"이 포함되어 있어서 파싱이 안되기 때문에 소문자로 변경해준다.
    }

    private String getBody(Response response) {
        return clean(customize(fixError(response.body())));
    }

    private String fixError(String body) {
        return body
                .replaceAll("N", "n")
                .replaceAll("&gt;.,&lt;", "");
    }

    private String customize(String body) {
        return body
                .replaceAll("(\\r\\n|\\r|\\n|\\n\\r|\\t)", "")
                .replaceFirst("^\\{.+\"result\":\\[", "")
                .replaceFirst("},", ",\"contents\":")
                .replaceFirst("]]}$", "]}");
    }

    private String clean(String body) {
        return body
                .replaceAll("&gt;|&lt;", "")
                .replaceAll("&quot;", "\"");
    }
}
