//package com.ecrick.crawler.parser;
//
//import com.ecrick.controller.response.LibraryResponse;
//import com.ecrick.crawler.domain.parser.KyoboJsonParser;
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.Test;
//
//import static com.ecrick.crawler.domain.CrawlerUtil.requestUrl;
//import static org.jsoup.Connection.Response;
//
//class KyoboJsonParserTest {
//
//    @Disabled
//    @Test
//    void parse() throws Exception {
//        // Given
//        String url = "http://ebook.splib.or.kr:8090/elibrary-front/content/contentListMobile.json?cttsDvsnCode=001&";
//        KyoboJsonParser parser = new KyoboJsonParser();
//
//        // When
//        Response response = requestUrl(url);
//        LibraryResponse dto = parser.parse(response);
//
//        // Then
//        System.out.println("totalBooks = " + dto.getTotalBooks());
//        dto.toCoreDtos(null).forEach(System.out::println);
//    }
//
//}