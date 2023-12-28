//package com.ecrick.crawler.parser;
//
//import com.ecrick.crawler.domain.webrequest.response.Yes24XmlResponse;
//import com.ecrick.crawler.domain.parser.Yes24XmlParser;
//import com.ecrick.dto.RowBookDto;
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.Test;
//
//import java.util.List;
//
//import static com.ecrick.crawler.domain.CrawlerUtil.requestUrl;
//
//class Yes24XmlParserTest {
//
//    @Disabled
//    @Test
//    void parse() throws Exception {
//        // Given
//        String url = "http://ebook.gunpolib.go.kr:8082/xml.asp?viewResultCount=50";
//        Yes24XmlParser parser = new Yes24XmlParser();
//
//        // When
//        Yes24XmlResponse responseDto = (Yes24XmlResponse) parser.parse(requestUrl(url));
//
//        // Then
//        System.out.println("totalBooks = " + responseDto.getTotalBooks());
//
//        List<RowBookDto> rowBooks = responseDto.toRowBooks(null);
//        rowBooks.forEach(System.out::println);
//    }
//
//    @Disabled
//    @Test
//    void parse2() throws Exception {
//        // Given
//        String url = "http://elib.gbgs.go.kr/yes24/xml.asp?currentPage=11&viewResultCount=500";
//        Yes24XmlParser parser = new Yes24XmlParser();
//
//        // When
//        Yes24XmlResponse responseDto = (Yes24XmlResponse) parser.parse(requestUrl(url));
//
//        // Then
//        System.out.println("totalBooks = " + responseDto.getTotalBooks());
//
//        List<RowBookDto> rowBooks = responseDto.toRowBooks(null);
//        rowBooks.forEach(System.out::println);
//    }
//
//}