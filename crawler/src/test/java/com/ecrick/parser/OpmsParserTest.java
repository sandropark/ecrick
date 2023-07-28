package com.ecrick.parser;

import com.ecrick.dto.ResponseDto;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class OpmsParserTest {

    @Disabled
    @Test
    void parse() throws Exception {
        // Given
        String url = "http://ebookcontents.incheon.go.kr:8093/api/v2/books.asp?";
        OpmsParser parser = new OpmsParser();

        // When
        ResponseDto dto = parser.parse(null);

        // Then
        System.out.println(dto.getTotalBooks());
        dto.toCoreDtos(null).forEach(System.out::println);
    }

}