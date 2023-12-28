package com.ecrick.crawler.domain.webclient;

import com.ecrick.crawler.domain.parser.CrawlerParser;
import com.ecrick.crawler.domain.webclient.response.LibraryResponse;
import com.ecrick.entity.Library;
import com.ecrick.entity.RowBook;
import com.ecrick.repository.LibraryRepository;
import org.jsoup.Connection;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;

@SpringBootTest
class WebClientTest {

    @Autowired
    LibraryRepository libraryRepository;
    @Autowired
    WebClient webClient;
    @Autowired
    List<CrawlerParser> parsers;

    @Test
    void test() throws Exception {
        Library library = libraryRepository.findById(Integer.toUnsignedLong(673)).orElseThrow();

        CrawlResult crawlResult = webClient.execute(library, 2);

        crawlResult.printResult();
    }

    @Test
    void exchange() throws Exception {
        Library library = libraryRepository.findById(Integer.toUnsignedLong(5186)).orElseThrow();

        List<String> detailUrls = library.getDetailUrls();
        String url = detailUrls.get(0);

        CrawlerParser parser = parsers.stream()
                .filter(crawlerParser -> crawlerParser.supports(library))
                .findAny()
                .orElseThrow();

        JsoupResponse response = webClient.exchange(url);
        LibraryResponse libraryResponse = parser.parse(response);
        List<RowBook> rowBooks = libraryResponse.toRowBooks(library);
        RowBook rowBook = rowBooks.get(rowBooks.size()-1);

        System.out.println("response = " + response.body());
        System.out.println();
        System.out.println("totalBooks = " + libraryResponse.getTotalBooks());
        System.out.println("rowBook = " + rowBook);
    }

    @Test
    void healthCheck() throws Exception {
        List<Library> libraries = libraryRepository.findAll();

        WebClient.HealthCheckResult healthCheckResult = webClient.healthCheck(libraries);

        healthCheckResult.printResult();
    }

    @EnableJpaRepositories(basePackages = "com.ecrick")
    @EntityScan(basePackages = "com.ecrick")
    @SpringBootApplication(scanBasePackages = "com.ecrick")
    static class TextContext {
    }

}