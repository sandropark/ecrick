//package com.ecrick.crawler.parser;
//
//import com.ecrick.controller.response.LibraryResponse;
//import com.ecrick.crawler.domain.parser.OpmsParser;
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.Test;
//
//import static com.ecrick.crawler.domain.CrawlerUtil.requestUrl;
//
//class OpmsParserTest {
//
//    @Disabled
//    @Test
//    void parse() throws Exception {
//        // Given
//        String url = "http://ebookcontents.incheon.go.kr:8093/api/v2/books.asp?";
//        OpmsParser parser = new OpmsParser();
//
//        // When
//        LibraryResponse dto = parser.parse(requestUrl(url));
//
//        // Then
//        System.out.println(dto.getTotalBooks());
//        dto.toCoreDtos(null).forEach(System.out::println);
//    }
//
//}