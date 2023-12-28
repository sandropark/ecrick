//package com.ecrick.crawler.parser;
//
//import com.ecrick.controller.response.LibraryResponse;
//import com.ecrick.crawler.domain.parser.SeoulEduParser;
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.Test;
//
//import static com.ecrick.crawler.domain.CrawlerUtil.requestUrl;
//
//class SeoulEduParserTest {
//
//    @Disabled
//    @Test
//    void parse() throws Exception {
//        // Given
//        String url = "http://e-lib.sen.go.kr/0_ebook/list.php?code=2&start_code=2&flag=ebk&sort1=cont_makedt&mode=total&";
//        SeoulEduParser parser = new SeoulEduParser();
//
//        // When
//        LibraryResponse dto = parser.parse(requestUrl(url));
//
//        // Then
//        System.out.println("totalBooks = " + dto.getTotalBooks());
//        dto.toCoreDtos(null).forEach(System.out::println);
//    }
//
//}