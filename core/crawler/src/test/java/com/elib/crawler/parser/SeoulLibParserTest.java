package com.elib.crawler.parser;

import com.elib.crawler.responsedto.ResponseDto;
import com.elib.domain.Library;
import com.elib.dto.BookDto;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.elib.crawler.CrawlerUtil.requestUrl;

class SeoulLibParserTest {

    @Disabled
    @Test
    void parse() throws Exception {
        // Given
//        String url = "https://elib.seoul.go.kr/api/contents/catesearch?orderOption=3&majorCategory=001&";
        String url = "https://elib.seoul.go.kr/api/contents/catesearch?orderOption=3&majorCategory=011&currentCount=1&pageCount=500";
        SeoulLibCleaner parser = new SeoulLibCleaner();

        // When
        ResponseDto dto = parser.parse(requestUrl(url));

        // Then
        System.out.println("totalBooks = " + dto.getTotalBooks());
        List<BookDto> bookDtos = dto.toBookDtos(Library.builder().name("서울").build());
//        bookDtos.forEach(System.out::println);

        bookDtos.forEach(bookDto ->
            System.out.println(bookDto.toEntity())
        );

    }

}