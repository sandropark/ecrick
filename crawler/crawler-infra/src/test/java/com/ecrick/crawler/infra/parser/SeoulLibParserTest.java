package com.ecrick.crawler.infra.parser;

import com.ecrick.crawler.domain.dto.ResponseDto;
import com.ecrick.domain.dto.CoreDto;
import com.ecrick.domain.entity.Library;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.ecrick.crawler.domain.worker.CrawlerUtil.requestUrl;

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
        List<CoreDto> coreDtos = dto.toCoreDtos(Library.builder().name("서울").build());
//        bookDtos.forEach(System.out::println);

        coreDtos.forEach(bookDto ->
                System.out.println(bookDto.toEntity())
        );

    }

}