//package com.ecrick.crawler.parser;
//
//import com.ecrick.controller.response.LibraryResponse;
//import com.ecrick.crawler.domain.parser.SeoulLibParser;
//import com.ecrick.entity.Library;
//import com.ecrick.dto.RowBookDto;
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.Test;
//
//import java.util.List;
//
//import static com.ecrick.crawler.domain.CrawlerUtil.requestUrl;
//
//class SeoulLibParserTest {
//
//    @Disabled
//    @Test
//    void parse() throws Exception {
//        // Given
////        String url = "https://elib.seoul.go.kr/api/contents/catesearch?orderOption=3&majorCategory=001&";
//        String url = "https://elib.seoul.go.kr/api/contents/catesearch?orderOption=3&majorCategory=011&currentCount=1&pageCount=500";
//        SeoulLibParser parser = new SeoulLibParser();
//
//        // When
//        LibraryResponse dto = parser.parse(requestUrl(url));
//
//        // Then
//        System.out.println("totalBooks = " + dto.getTotalBooks());
//        List<RowBookDto> rowBooks = dto.toCoreDtos(Library.builder().name("서울").build());
////        bookDtos.forEach(System.out::println);
//
//        rowBooks.forEach(bookDto ->
//            System.out.println(bookDto.toEntity())
//        );
//
//    }
//
//}