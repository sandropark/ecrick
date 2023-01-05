package com.elib.crawler;

import com.elib.crawler.dto.ResponseDto;
import com.elib.crawler.parser.*;
import com.elib.domain.Library;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.jsoup.Connection.Response;

@Slf4j
public class CrawlerUtil {

    private static final HashMap<String, String> headers = new HashMap<>() {{
        put("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        put("Accept-Encoding", "gzip, deflate");
        put("Accept-Language", "ko,en-US;q=0.9,en;q=0.8,ko-KR;q=0.7");
        put("Cache-Control", "max-age=0");
        put("Upgrade-Insecure-Requests", "1");
        put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/106.0.5249.207 Whale/3.17.145.18 Safari/537.36");
    }};

    private static final List<CrawlerParser> parsers = List.of(
            new KyoboXmlParser(), // TODO : 마포구전자도서관 확인
            new KyoboJsonParser(),
            new Yes24XmlParser(),
            new Yes24JsonParser(),
            new OpmsParser(),
            new BookcubeParser(),
            new AladinParser(),
            new SeoulLibParser(),
            new SeoulEduParser()
    );

    public static Response requestUrl(String url) throws IOException {
        return Jsoup.connect(url)
                .headers(headers)
                .timeout(300000)
                .ignoreContentType(true)
                .execute();
    }

    public static Optional<ResponseDto> getResponseDto(String url, Library library) {
        try {
            return Optional.of(getParser(library).parse(requestUrl(url)));
        } catch (IllegalArgumentException e) {
            log.error("parser를 찾을 수 없습니다. {}", library.getName());
        } catch (JAXBException | JsonProcessingException e) {
            log.error("파싱 오류 {}, {}", library.getName(), url, e);
        } catch (IOException e) {
            log.error("접속 실패 {}, {}", library.getName(), url, e);
        }
        return Optional.empty();
    }

    protected static CrawlerParser getParser(Library library) {
        for (CrawlerParser parser : parsers) {
            if (parser.supports(library)) {
                return parser;
            }
        }
        throw new IllegalArgumentException();
    }

}
