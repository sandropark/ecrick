package com.elib.crawler.parser;

import com.elib.crawler.dto.ResponseDto;
import org.junit.jupiter.api.Test;

import static com.elib.crawler.CrawlerUtil.requestUrl;

class SeoulLibParserTest {

    @Test
    void parse() throws Exception {
        // Given
        String url = "https://elib.seoul.go.kr/api/contents/catesearch?orderOption=3&majorCategory=001&";
        SeoulLibParser parser = new SeoulLibParser();

        // When
        ResponseDto dto = parser.parse(requestUrl(url));

        // Then
        System.out.println("totalBooks = " + dto.getTotalBooks());
        dto.toBookDtos(null).forEach(System.out::println);
    }

}