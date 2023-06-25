package com.elib.crawler.parser;

import com.elib.crawler.dto.LibraryCrawlerDto;
import com.elib.crawler.dto.BookcubeDto;
import com.elib.crawler.dto.ResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Connection;

import javax.xml.bind.JAXBException;

public class BookcubeParser extends BodyCleaner implements CrawlerParser {
    @Override
    public Boolean supports(LibraryCrawlerDto libraryDto) {
        return libraryDto.isBookcube();
    }

    @Override
    public ResponseDto parse(Connection.Response response) throws JAXBException, JsonProcessingException {
        return new ObjectMapper().readValue(cleanBody(response.body()), BookcubeDto.class);  // "\N"이 포함되어 있어서 파싱이 안되기 때문에 소문자로 변경해준다.
    }

    private String cleanBody(String body) {
        return clean(customize(fixError(body)));
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
}
