package com.ecrick.parser;

import com.ecrick.dto.ResponseDto;
import com.ecrick.domain.Library;
import com.ecrick.dto.CoreDto;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;

class SeoulLibParserTest {

    @Disabled
    @Test
    void parse() throws Exception {
        // Given
//        String url = "https://elib.seoul.go.kr/api/contents/catesearch?orderOption=3&majorCategory=001&";
        String url = "https://elib.seoul.go.kr/api/contents/catesearch?orderOption=3&majorCategory=011&currentCount=1&pageCount=500";
        SeoulLibParser parser = new SeoulLibParser();

        // When
        ResponseDto dto = parser.parse(null);

        // Then
        System.out.println("totalBooks = " + dto.getTotalBooks());
        List<CoreDto> coreDtos = dto.toCoreDtos(Library.builder().name("서울").build());
//        bookDtos.forEach(System.out::println);

        coreDtos.forEach(bookDto ->
            System.out.println(bookDto.toEntity())
        );

    }

}