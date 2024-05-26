package com.ecrick.crawler.infra;

import com.ecrick.crawler.domain.CrawlerClient;
import com.ecrick.crawler.domain.ResponseDto;
import com.ecrick.crawler.infra.parser.CrawlerParser;
import com.ecrick.domain.entity.Library;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.jsoup.Connection.Response;

@Slf4j
@RequiredArgsConstructor
@Component
public class JsoupCrawlerClient implements CrawlerClient {
    private static final Map<String, String> headers = Map.of(
            "accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
            "Accept-Encoding", "gzip, deflate",
            "Accept-Language", "ko,en-US;q=0.9,en;q=0.8,ko-KR;q=0.7",
            "Cache-Control", "max-age=0",
            "Upgrade-Insecure-Requests", "1",
            "User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/106.0.5249.207 Whale/3.17.145.18 Safari/537.36");

    private final List<CrawlerParser> parsers;

    @Override
    public ResponseDto get(Library library) {
        Response jsoupResponse = execute(library.getUrl());
        CrawlerParser parser = getParser(library);
        return parser.parse(jsoupResponse);
    }

    @Override
    public void updateTotalBooks(Library library) {
        Response jsoupResponse = execute(library.getUrl());
        CrawlerParser parser = getParser(library);
        ResponseDto responseDto = parser.parse(jsoupResponse);
        library.updateTotalBooks(responseDto.getTotalBooks());
    }

    private static Response execute(String url) {
        try {
            return Jsoup.connect(url)
                    .headers(headers)
                    .timeout(300000)
                    .ignoreContentType(true)
                    .execute();
        } catch (IOException e) {
            log.error("접속 실패 {}", url, e);
            throw new RuntimeException(e);
        }
    }

    private CrawlerParser getParser(Library library) {
        return parsers.stream()
                .filter(parser -> parser.supports(library))
                .findFirst()
                .orElseThrow();
    }

//    public static Optional<ResponseDto> getResponseDto(LibraryCrawlerDto libraryDto) {
//        return getResponseDto(libraryDto.getUrl(), libraryDto);
//    }

//    public Optional<ResponseDto> getResponseDto(String url, LibraryCrawlerDto libraryDto) {
//        try {
//            return Optional.of(getParser(libraryDto).parse(execute(url)));
//        } catch (IllegalArgumentException e) {
//            log.error("parser를 찾을 수 없습니다. {}", libraryDto.getName());
//        } catch (JAXBException | JsonProcessingException e) {
//            log.error("파싱 오류 {}, {}", libraryDto.getName(), url, e);
//        } catch (IOException e) {
//            log.error("접속 실패 {}, {}", libraryDto.getName(), url, e);
//        }
//        return Optional.empty();
//    }

}
