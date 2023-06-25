package com.elib.crawler.parser;

import com.elib.crawler.dto.Yes24JsonDto;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static com.elib.crawler.worker.CrawlerUtil.requestUrl;

class Yes24JsonParserTest {

    @Disabled
    @Test
    void parse() throws Exception {
        // Given
//        String url = "https://gumilib.yes24library.com/app/appMain.asp?";
        String url = "http://www.yclib.go.kr:8080/app/appMain.asp?currentPage=1&viewResultCount=100";
        Yes24JsonParser parser = new Yes24JsonParser();

        // When
        Yes24JsonDto dto = (Yes24JsonDto) parser.parse(requestUrl(url));

        // Then
        System.out.println("count = " + dto.getTotalBooks());
        dto.toCoreDtos(null).forEach(System.out::println);
    }

}