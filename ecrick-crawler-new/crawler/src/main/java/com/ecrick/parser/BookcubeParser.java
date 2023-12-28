package com.ecrick.parser;

import com.ecrick.entity.Library;
import com.ecrick.model.BookcubeCrawlerModel;
import com.ecrick.model.CommonModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class BookcubeParser extends CrawlerParser {
    public BookcubeParser(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    public boolean supports(Library library) {
        return library.isBookcube();
    }

    @Override
    public CommonModel parse(String body) {
        body = preProcess(body);
        return parseJson(body, BookcubeCrawlerModel.class);
    }

    String preProcess(String body) {
        body = reduce(body);
        body = reFormat(body);
        return fixError(body);
    }

    private String reduce(String body) {
        body = body.replaceAll("[\t\n\r]", "");

        int start = body.indexOf('[');
        int end = body.lastIndexOf(']');
        return body.substring(start + 1, end);
    }

    private String reFormat(String body) {
        return body
                .replaceFirst("\\[", "\"contents\":[")
                .replaceFirst("}", "")
                .replaceFirst("}]$", "}]}");
    }

    private String fixError(String body) {
        // "\N"이 포함되어 있어서 파싱이 안되기 때문에 소문자로 변경해준다.
        return body
                .replaceAll("&gt;.{2}&lt;", "")
                .replaceAll("N", "n");
    }

}
