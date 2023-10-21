package com.ecrick.crawler.parser;

import com.ecrick.crawler.dto.ResponseDto;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static com.ecrick.crawler.worker.CrawlerUtil.requestUrl;

class AladinParserTest {

    @Disabled
    @Test
    void parse() throws Exception {
        // Given
        String url = "http://elib.gyeongnam.go.kr:8050/data/xml/xml.php?sortType=4&";
        AladinParser parser = new AladinParser();

        // When
        ResponseDto dto = parser.parse(requestUrl(url));

        // Then
        Integer totalBooks = dto.getTotalBooks();
        System.out.println("totalBooks = " + totalBooks);
        dto.toCoreDtos(null).forEach(System.out::println);
    }

}