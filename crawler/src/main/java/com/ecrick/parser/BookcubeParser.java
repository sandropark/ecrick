package com.ecrick.parser;

import com.ecrick.dto.BookcubeDto;
import com.ecrick.dto.LibraryCrawlerDto;
import com.ecrick.dto.ResponseBodyDto;
import com.ecrick.dto.ResponseDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BookcubeParser extends CrawlerParser {

    @Override
    public Boolean supports(LibraryCrawlerDto libraryDto) {
        return libraryDto.isBookcube();
    }

    @Override
    public ResponseDto parse(ResponseBodyDto bodyDto) {
        return super.parseJson(BookcubeDto.class, processBody(bodyDto.getBody()));
    }

    private String processBody(String body) {
        String processedBody = fixError(body);
        processedBody = reduce(processedBody);
        processedBody = customize(processedBody);
        return processedBody;
    }

    private String fixError(String body) {
        return body
                .replaceAll("N", "n")
                .replaceAll("&[a-z]+;", "");
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
