package com.ecrick.service;

import com.ecrick.dto.LibraryCrawlerDto;
import com.ecrick.dto.ResponseDto;
import com.ecrick.parser.*;
import com.ecrick.domain.Library;
import com.ecrick.dto.CoreDto;
import com.ecrick.repository.LibraryRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static java.lang.Thread.sleep;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@ActiveProfiles("prd")
@SpringBootTest(properties = {
        "OCI_DB_HOST=jdbc:mariadb://localhost:3306/elib",
        "OCI_DB_USERNAME=root",
        "OCI_DB_PASSWORD=8989"
})
class LibraryServiceTest {

    private static final String EX_FORMAT = "{}, url={}";

    private static final RestTemplate template = getRestTemplate();

    @Autowired
    LibraryRepository libraryRepository;

    @Autowired
    TestContext.AsyncTemplate asyncTemplate;

    @EnableAsync
    @SpringBootApplication(scanBasePackages = "com.ecrick")
    static class TestContext {
        @Component
        static class AsyncTemplate {
            @Async
            public void exchange(Library library) {
                String url = makeUrl(library);
                try {
                    LibraryCrawlerDto libraryCrawlerDto = LibraryCrawlerDto.from(library);
                    ResponseEntity<String> response =
                            template.exchange(url, HttpMethod.GET, HttpEntity.EMPTY, String.class);
                    ResponseDto responseDto = findParser(libraryCrawlerDto).parse(null);
                    CoreDto coreDto = responseDto.toCoreDtos(library).get(0);

                    String title = coreDto.getTitle();
                    String author = coreDto.getAuthor();
                    String publisher = coreDto.getPublisher();

                    log.info("{}, url={}, vendor={}, contentType={}, totalBooks={}, title={}, author={}, publisher={}",
                            library.getName(), url, library.getVendor().getName(), library.getContentType(),
                            responseDto.getTotalBooks(), title, author, publisher);

                    assertThat(responseDto.getTotalBooks()).isNotNull();
                    assertThat(title).isNotNull();
                    assertThat(author).isNotNull();
                    assertThat(publisher).isNotNull();

                } catch (Exception e) {
                    log.error(EX_FORMAT, library.getName(), url, e);
                }
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
        }
    }

    @Test
    void parseAll() throws Exception {
        // 1. 도서관 모두 조회
        List<Library> libraries = libraryRepository.findAll();

        // 2. 파서 조회
        // 3. 요청 후 파싱
        for (Library library : libraries)
            asyncTemplate.exchange(library);

        sleep(300000);
    }

    @Test
    void parse() throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add((request, body, execution) -> {
            ClientHttpResponse response = execution.execute(request, body);
            HttpHeaders headers = response.getHeaders();
            List<String> contentTypes = headers.get("Content-Type");

            if (isJustText(contentTypes) || isBookcube(request))
                headers.setContentType(MediaType.APPLICATION_JSON);

            log.info("{}", response.getHeaders().get("Content-Type"));
            return response;
        });
        Library library = libraryRepository.findById(5168L).orElseThrow();
        ResponseEntity<String> response = restTemplate.exchange(makeUrl(library), HttpMethod.GET, HttpEntity.EMPTY, String.class);
        String body = response.getBody();
        log.info("{}, body={}", library.getName(), body);

        ResponseDto responseDto = new BookcubeParser().parse(null);
        log.info("{}", responseDto.getTotalBooks());
        CoreDto coreDto = responseDto.toCoreDtos(Library.builder().build()).get(0);
        System.out.println("coreDto = " + coreDto);
    }

    private static String makeUrl(Library library) {
        return makeUrl(library.getUrl(), library.getParam());
    }

    static String makeUrl(String url, String param) {
        String[] params = param.split("&");
        String tempUrl = url + params[0] + "2";
        if (params.length > 1)
            return tempUrl + "&" + params[1] + "1";
        return tempUrl;
    }

    private static RestTemplate getRestTemplate() {
        RestTemplate template = new RestTemplate();
        template.getInterceptors().add((request, body, execution) -> {
            ClientHttpResponse response = execution.execute(request, body);
            List<String> contentTypes = response.getHeaders().get("Content-Type");

            if (isJustText(contentTypes) || isBookcube(request))
                response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
            return response;
        });
        return template;
    }

    private static boolean isBookcube(HttpRequest request) {
        return request.getURI().toString().contains("FxLibrary/m/product/productList");
    }

    private static boolean isJustText(List<String> contentTypes) {
        return contentTypes.get(0).contains("text;");
    }

}
