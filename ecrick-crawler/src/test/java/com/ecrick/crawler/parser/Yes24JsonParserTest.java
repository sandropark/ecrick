//package com.ecrick.crawler.parser;
//
//import com.ecrick.crawler.domain.webrequest.response.Yes24JsonResponse;
//import com.ecrick.crawler.domain.parser.Yes24JsonParser;
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.Test;
//
//import static com.ecrick.crawler.domain.CrawlerUtil.requestUrl;
//
//class Yes24JsonParserTest {
//
//    @Disabled
//    @Test
//    void parse() throws Exception {
//        // Given
////        String url = "https://gumilib.yes24library.com/app/appMain.asp?";
//        String url = "http://www.yclib.go.kr:8080/app/appMain.asp?currentPage=1&viewResultCount=100";
//        Yes24JsonParser parser = new Yes24JsonParser();
//
//        // When
//        Yes24JsonResponse dto = (Yes24JsonResponse) parser.parse(requestUrl(url));
//
//        // Then
//        System.out.println("count = " + dto.getTotalBooks());
//        dto.toRowBooks(null).forEach(System.out::println);
//    }
//
//}