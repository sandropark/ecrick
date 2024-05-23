package com.ecrick.crawler.parser;

import com.ecrick.core.dto.CoreDto;
import com.ecrick.crawler.dto.KyoboXmlDto;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.ecrick.crawler.worker.CrawlerUtil.requestUrl;

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

        List<CoreDto> coreDtos = responseDto.toCoreDtos(null);
//        bookDtos.forEach(System.out::println);

        coreDtos.forEach(bookDto -> {
            System.out.println(bookDto.toEntity());
        });
    }

}