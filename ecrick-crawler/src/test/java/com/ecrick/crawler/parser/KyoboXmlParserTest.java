//package com.ecrick.crawler.parser;
//
//import com.ecrick.crawler.domain.webrequest.response.KyoboXmlResponse;
//import com.ecrick.crawler.domain.parser.KyoboXmlParser;
//import com.ecrick.dto.RowBookDto;
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.Test;
//
//import java.util.List;
//
//import static com.ecrick.crawler.domain.CrawlerUtil.requestUrl;
//
//class KyoboXmlParserTest {
//
//    @Disabled
//    @Test
//    void parse() throws Exception {
//        // Given
//        String url = "http://gumilib.yes24library.com:8085/kyobo_t3_mobile/Tablet/Main/Ebook_MoreView.asp?paging=2";
//        KyoboXmlParser parser = new KyoboXmlParser();
//
//        // When
//        KyoboXmlResponse responseDto = (KyoboXmlResponse) parser.parse(requestUrl(url));
//
//        // Then
//        System.out.println("totalBooks = " + responseDto.getTotalBooks());
//
//        List<RowBookDto> rowBooks = responseDto.toRowBooks(null);
////        bookDtos.forEach(System.out::println);
//
//        rowBooks.forEach(bookDto -> {
//            System.out.println(bookDto.toEntity());
//        });
//    }
//
//}