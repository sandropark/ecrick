package com.ecrick.crawler.healthcheck.code;

import com.ecrick.dto.LibraryCrawlerDto;
import com.ecrick.dto.ResponseBodyDto;
import com.ecrick.dto.ResponseDto;
import com.ecrick.domain.Library;
import com.ecrick.dto.CoreDto;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.util.Map;

@Slf4j
public class JsoupHealthCheckClient extends HealthCheckClient {

    private static final Map<String, String> headers =
            Map.of("User-Agent",
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 " +
                            "(KHTML, like Gecko) Chrome/106.0.5249.207 Whale/3.17.145.18 Safari/537.36");

    @Override
    public Boolean exchange(Library library) {
        String url = makeUrl(library);
        Connection.Response response = null;
        try {
            response = Jsoup.connect(url)
                    .headers(headers)
                    .timeout(300000)
                    .ignoreContentType(true)
                    .execute();

            ResponseDto responseDto = findParser(LibraryCrawlerDto.from(library)).parse(ResponseBodyDto.from(response));
            CoreDto coreDto = responseDto.toCoreDtos(library).get(0);

            log(url, responseDto, coreDto);

            assert responseDto.getTotalBooks() != null : "totalBooks must not be null";
            assert coreDto.getTitle() != null : "title must not be null";
            assert coreDto.getAuthor() != null : "author must not be null";
            assert coreDto.getPublisher() != null : "publisher must not be null";

            return true;
        } catch (Exception e) {
            if (response != null)
                log.error("{}, url={} type={} \n response={}", library.getName(), url, library.getContentType(), response.body(), e);
            else
                log.error("{}, url={} type={}", library.getName(), url, library.getContentType(), e);
            return false;
        }
    }
}
