package com.ecrick.crawler.infra.parser;

import com.ecrick.crawler.domain.dto.ResponseDto;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static com.ecrick.crawler.domain.worker.CrawlerUtil.requestUrl;

class OpmsParserTest {

    @Disabled
    @Test
    void parse() throws Exception {
        // Given
        String url = "http://ebookcontents.incheon.go.kr:8093/api/v2/books.asp?";
        OpmsParser parser = new OpmsParser();

        // When
        ResponseDto dto = parser.parse(requestUrl(url));

        // Then
        System.out.println(dto.getTotalBooks());
        dto.toCoreDtos(null).forEach(System.out::println);
    }

}