package com.ecrick.crawler.domain.parser;

import com.ecrick.crawler.domain.webclient.JsoupResponse;
import com.ecrick.crawler.domain.webclient.response.BookcubeResponse;
import com.ecrick.crawler.domain.webclient.response.LibraryResponse;
import com.ecrick.entity.Library;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BookcubeParser implements CrawlerParser {
    private final ObjectMapper objectMapper;

    @Override
    public Boolean supports(Library library) {
        return library.isBookcube();
    }

    @Override
    public LibraryResponse parse(JsoupResponse response) {
        try {
            String body = response.body();
            body = preProcess(body);
            return objectMapper.readValue(body, BookcubeResponse.class);  // "\N"이 포함되어 있어서 파싱이 안되기 때문에 소문자로 변경해준다.
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private String preProcess(String body) {
        body = fixError(body);
        body = reduce(body);
        return customize(body);
    }

    private String fixError(String body) {
        return body.replaceAll("N", "n");
    }

    private String reduce(String body) {
        body = body.replaceAll("[\r\n\t]", "");

        int start = body.indexOf('[');
        int end = body.lastIndexOf(']');
        return body.substring(start + 1, end);
    }

    private String customize(String body) {
        return body
                .replaceFirst("\\[", "\"contents\":[")
                .replaceFirst("}", "")
                .replaceFirst("}]$", "}]}");
    }
}
