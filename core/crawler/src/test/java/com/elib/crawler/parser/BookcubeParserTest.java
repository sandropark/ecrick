package com.elib.crawler.parser;

import com.elib.crawler.responsedto.BookcubeDto;
import org.jsoup.Connection;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static com.elib.crawler.CrawlerUtil.requestUrl;

class BookcubeParserTest {

    @Disabled
    @Test
    void parse() throws Exception {
        // Given
//        String url = "https://lib.seogwipo.go.kr/FxLibrary/m/product/productList/?category=total&cateopt=&sort=2&";
        String url = " https://lib.seogwipo.go.kr/FxLibrary/m/product/productList/?category=total&cateopt=&sort=2&page=44";

        // When
        Connection.Response response = requestUrl(url);

        System.out.println("response.body() = " + response.body());

        BookcubeDto dto = (BookcubeDto) new BookcubeParser().parse(response);

        // Then
        System.out.println("totalBooks = " + dto.getTotalBooks());
        dto.toBookDtos(null).forEach(System.out::println);
    }

}