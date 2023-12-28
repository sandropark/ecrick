//package com.ecrick.crawler.parser;
//
//import com.ecrick.crawler.domain.parser.AladinParser;
//import com.ecrick.crawler.domain.webrequest.response.LibraryResponse;
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.Test;
//
//import static com.ecrick.crawler.domain.CrawlerUtil.requestUrl;
//
//class AladinParserTest {
//
//    @Disabled
//    @Test
//    void parse() throws Exception {
//        // Given
//        String url = "http://elib.gyeongnam.go.kr:8050/data/xml/xml.php?sortType=4&";
//        AladinParser parser = new AladinParser();
//
//        // When
//        LibraryResponse dto = parser.parse(requestUrl(url));
//
//        // Then
//        Integer totalBooks = dto.getTotalBooks();
//        System.out.println("totalBooks = " + totalBooks);
//        dto.toRowBooks(null).forEach(System.out::println);
//    }
//
//}