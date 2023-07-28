package com.ecrick.client;

import com.ecrick.dto.LibraryCrawlerDto;
import com.ecrick.dto.ResponseBodyDto;
import com.ecrick.dto.ResponseDto;
import com.ecrick.parser.*;
import com.ecrick.domain.ContentType;
import com.ecrick.domain.VendorName;
import com.ecrick.dto.CoreDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
//@Component
public class RequestCrawler {

//    private final CrawlerCoreService crawlerCoreService;

    private static final Map<String, CrawlerParser> PARSERS = Map.of(
            VendorName.KYOBO.getValue() + ContentType.APPLICATION_JSON, new KyoboJsonParser(),
            VendorName.KYOBO.getValue() + ContentType.TEXT_XML, new KyoboXmlParser(),
            VendorName.YES24.getValue() + ContentType.APPLICATION_JSON, new Yes24JsonParser(),
            VendorName.YES24.getValue() + ContentType.TEXT_XML, new Yes24XmlParser(),
            VendorName.OPMS.getValue() + ContentType.APPLICATION_JSON, new OpmsParser(),
            VendorName.BOOKCUBE.getValue() + ContentType.APPLICATION_JSON, new BookcubeParser(),
            VendorName.ALADIN.getValue() + ContentType.TEXT_XML, new AladinParser(),
            VendorName.SEOUL_LIB.getValue() + ContentType.APPLICATION_JSON, new SeoulLibParser(),
            VendorName.SEOUL_EDU.getValue() + ContentType.HTML, new SeoulEduParser()
    );
    private static final Map<String, String> headers =
            Map.of("User-Agent",
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 " +
                            "(KHTML, like Gecko) Chrome/106.0.5249.207 Whale/3.17.145.18 Safari/537.36");

    public void crawl(String url, LibraryCrawlerDto library) {
        // 1. 데이터 요청
        // 2. 응답 데이터 파싱. 도서 데이터 리스트를 얻는다.
        // 3. DB에 저장
        log.info("{}, url={} 데이터 요청", library.getName(), url);
        Connection.Response response;
        try {
            response = Jsoup.connect(url)
                    .headers(headers)
                    .timeout(180000)
                    .ignoreContentType(true)
                    .execute();
        } catch (IOException e) {
            log.error("데이터 요청 실패 - {}, url={}", library.getName(), url);
            throw new RuntimeException(e);
        }
        ResponseBodyDto bodyDto = ResponseBodyDto.from(response);
        CrawlerParser parser = PARSERS.get(library.getKey());
        ResponseDto responseDto = parser.parse(bodyDto);

        List<CoreDto> coreDtos = responseDto.toCoreDtos(library.toEntity());

//        crawlerCoreService.saveAll(coreDtos);
    }

}
