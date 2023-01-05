package com.elib.crawler.parser;

import com.elib.crawler.dto.BookcubeDto;
import org.junit.jupiter.api.Test;

import static com.elib.crawler.CrawlerUtil.requestUrl;

class BookcubeParserTest {

    @Test
    void parse() throws Exception {
        // Given
        String url = "https://lib.seogwipo.go.kr/FxLibrary/m/product/productList/?category=total&cateopt=&sort=2&";
//        String url = "http://210.96.40.66:9080/FxLibrary/m/product/productList/?category=total&cateopt=&sort=2&";

        // When
        BookcubeDto dto = (BookcubeDto) new BookcubeParser().parse(requestUrl(url));

        // Then
        System.out.println("totalBooks = " + dto.getTotalBooks());
        dto.toBookDtos(null).forEach(System.out::println);
    }

}