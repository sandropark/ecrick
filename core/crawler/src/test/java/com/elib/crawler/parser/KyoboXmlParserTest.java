package com.elib.crawler.parser;

import com.elib.crawler.responsedto.KyoboXmlDto;
import com.elib.dto.BookDto;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.elib.crawler.CrawlerUtil.requestUrl;

class KyoboXmlParserTest {

    @Disabled
    @Test
    void parse() throws Exception {
        // Given
        String url = "http://gumilib.yes24library.com:8085/kyobo_t3_mobile/Tablet/Main/Ebook_MoreView.asp?paging=2";
        KyoboXmlParser parser = new KyoboXmlParser();

        // When
        KyoboXmlDto responseDto = (KyoboXmlDto) parser.parse(requestUrl(url));

        // Then
        System.out.println("totalBooks = " + responseDto.getTotalBooks());

        List<BookDto> bookDtos = responseDto.toBookDtos(null);
//        bookDtos.forEach(System.out::println);

        bookDtos.forEach(bookDto -> {
            System.out.println(bookDto.toEntity());
        });
    }

}