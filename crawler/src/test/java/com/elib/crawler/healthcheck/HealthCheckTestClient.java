package com.elib.crawler.healthcheck;

import com.elib.crawler.dto.LibraryCrawlerDto;
import com.elib.crawler.dto.ResponseDto;
import com.elib.crawler.parser.*;
import com.elib.domain.Library;
import com.elib.dto.CoreDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RequiredArgsConstructor
@Component
public class HealthCheckTestClient {

    private final RestTemplate template;

    public Boolean exchange(Library library) {
        String url = makeUrl(library);
        try {
            ResponseEntity<String> response = template.exchange(url, HttpMethod.GET, HttpEntity.EMPTY, String.class);

            ResponseDto responseDto = findParser(LibraryCrawlerDto.from(library)).parse(response.getBody());
            CoreDto coreDto = responseDto.toCoreDtos(library).get(0);

            log(url, responseDto, coreDto);

            assert responseDto.getTotalBooks() != null : "totalBooks must not be null";
            assert coreDto.getTitle() != null : "title must not be null";
            assert coreDto.getAuthor() != null : "author must not be null";
            assert coreDto.getPublisher() != null : "publisher must not be null";

            return true;
        } catch (Exception e) {
            log.error("{}, url={}", library.getName(), url, e);
            return false;
        }
    }

    private void log(String url, ResponseDto responseDto, CoreDto coreDto) {
        Library library = coreDto.getLibrary();
        log.info("{}, url={}, vendor={}, contentType={}, totalBooks={}, title={}, author={}, publisher={}",
                library.getName(), url, library.getVendor().getName(), library.getContentType(),
                responseDto.getTotalBooks(), coreDto.getTitle(), coreDto.getAuthor(), coreDto.getPublisher());
    }

    private CrawlerParser findParser(LibraryCrawlerDto library) {
        if (library.isAladin()) return new AladinParser();
        if (library.isBookcube()) return new BookcubeParser();
        if (library.isKyoboJson()) return new KyoboJsonParser();
        if (library.isKyoboXml()) return new KyoboXmlParser();
        if (library.isOPMS()) return new OpmsParser();
        if (library.isSeoulEdu()) return new SeoulEduParser();
        if (library.isSeoulLib()) return new SeoulLibParser();
        if (library.isYes24Json()) return new Yes24JsonParser();
        if (library.isYes24Xml()) return new Yes24XmlParser();

        throw new IllegalArgumentException();
    }

    private String makeUrl(Library library) {
        String[] params = library.getParam().split("&");
        String tempUrl = library.getUrl() + params[0] + "2";
        if (params.length > 1)
            return tempUrl + "&" + params[1] + "1";
        return tempUrl;
    }

}