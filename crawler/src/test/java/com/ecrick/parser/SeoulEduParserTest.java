package com.ecrick.parser;

import com.ecrick.dto.ResponseDto;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class SeoulEduParserTest {

    @Disabled
    @Test
    void parse() throws Exception {
        // Given
        String url = "http://e-lib.sen.go.kr/0_ebook/list.php?code=2&start_code=2&flag=ebk&sort1=cont_makedt&mode=total&";
        SeoulEduParser parser = new SeoulEduParser();

        // When
        ResponseDto dto = parser.parse(null);

        // Then
        System.out.println("totalBooks = " + dto.getTotalBooks());
        dto.toCoreDtos(null).forEach(System.out::println);
    }

}