package com.ecrick.parser;

import com.ecrick.dto.BookcubeDto;
import org.jsoup.Connection;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static com.ecrick.worker.CrawlerUtil.requestUrl;

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

        BookcubeDto dto = (BookcubeDto) new BookcubeParser().parse(null);

        // Then
        System.out.println("totalBooks = " + dto.getTotalBooks());
        dto.toCoreDtos(null).forEach(System.out::println);
    }

}