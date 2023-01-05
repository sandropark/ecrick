package com.elib.crawler.parser;

import com.elib.crawler.dto.ResponseDto;
import org.junit.jupiter.api.Test;

import static com.elib.crawler.CrawlerUtil.requestUrl;

class SeoulEduParserTest {

    @Test
    void parse() throws Exception {
        // Given
        String url = "http://e-lib.sen.go.kr/0_ebook/list.php?code=2&start_code=2&flag=ebk&sort1=cont_makedt&mode=total&";
        SeoulEduParser parser = new SeoulEduParser();

        // When
        ResponseDto dto = parser.parse(requestUrl(url));

        // Then
        System.out.println("totalBooks = " + dto.getTotalBooks());
        dto.toBookDtos(null).forEach(System.out::println);
    }

}