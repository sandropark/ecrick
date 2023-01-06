package com.elib.crawler.parser;

import com.elib.crawler.responsedto.ResponseDto;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static com.elib.crawler.CrawlerUtil.requestUrl;

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
        dto.toBookDtos(null).forEach(System.out::println);
    }

}