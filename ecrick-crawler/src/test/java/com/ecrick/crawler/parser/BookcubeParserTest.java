//package com.ecrick.crawler.parser;
//
//import com.ecrick.crawler.domain.webrequest.response.BookcubeResponse;
//import com.ecrick.crawler.domain.parser.BookcubeParser;
//import org.jsoup.Connection;
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.Test;
//
//import static com.ecrick.crawler.domain.CrawlerUtil.requestUrl;
//
//class BookcubeParserTest {
//
//    @Disabled
//    @Test
//    void parse() throws Exception {
//        // Given
////        String url = "https://lib.seogwipo.go.kr/FxLibrary/m/product/productList/?category=total&cateopt=&sort=2&";
//        String url = " https://lib.seogwipo.go.kr/FxLibrary/m/product/productList/?category=total&cateopt=&sort=2&page=44";
//
//        // When
//        Connection.Response response = requestUrl(url);
//
//        System.out.println("response.body() = " + response.body());
//
//        BookcubeResponse dto = (BookcubeResponse) new BookcubeParser().parse(response);
//
//        // Then
//        System.out.println("totalBooks = " + dto.getTotalBooks());
//        dto.toRowBooks(null).forEach(System.out::println);
//    }
//
//}