package com.elib.crawler.parser;

import com.elib.crawler.dto.ResponseDto;
import org.junit.jupiter.api.Test;

import static com.elib.crawler.CrawlerUtil.requestUrl;
import static org.jsoup.Connection.Response;

class KyoboJsonParserTest {

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

    @Test
    void 에러_찾기() throws Exception {
        int totalBooks = 68298;
        int size = 500;
        int totalPages = totalBooks / size;

        for (int i = 1; i < totalPages; i++) {
            String url = "http://yclib.dkyobobook.co.kr/content/contentListMobile.json?cttsDvsnCode=001&pageIndex=" + i + "&recordCount=" + size;

            System.out.println("url = " + url);

            Response response = requestUrl(url);

//            System.out.println("response = " + response.body()
//                    .replaceAll("&amp;|&lt;|&gt;|&ldquo;|&rdquo;|&lsquo;|&rsquo;|&AElig;", "")
//            );

            ResponseDto dto = new KyoboJsonParser().parse(response);
//
//            dto.toBookDtos(null).forEach(System.out::println);
        }

    }

}