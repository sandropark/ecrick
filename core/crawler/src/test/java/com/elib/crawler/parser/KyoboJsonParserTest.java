package com.elib.crawler.parser;

import com.elib.crawler.responsedto.ResponseDto;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.elib.crawler.CrawlerUtil.requestUrl;
import static org.jsoup.Connection.Response;

class KyoboJsonParserTest {

    @Disabled
    @Test
    void parse() throws Exception {
        // Given
        String url = "http://ebook.splib.or.kr:8090/elibrary-front/content/contentListMobile.json?cttsDvsnCode=001&";
        KyoboJsonParser parser = new KyoboJsonParser();

        // When
        Response response = requestUrl(url);
        ResponseDto dto = parser.parse(response);

        // Then
        System.out.println("totalBooks = " + dto.getTotalBooks());
        dto.toBookDtos(null).forEach(System.out::println);
    }

}