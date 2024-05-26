package com.ecrick.crawler;

import com.ecrick.crawler.domain.Crawler;
import com.ecrick.crawler.domain.CrawlerClient;
import com.ecrick.domain.entity.Library;
import com.ecrick.domain.repository.LibraryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CrawlerTest {
    @Autowired
    Crawler crawler;
    @Autowired
    LibraryRepository libraryRepository;
    @Autowired
    CrawlerClient crawlerClient;

    @Test
    void test() throws Exception {
//        Library library = libraryRepository.findById(5181L).orElseThrow();
//
//        crawlerClient.get(library)
//                .ifPresent(res -> {
//                    System.out.println("responseDto.getTotalBooks() = " + res.getTotalBooks());
//                });
        libraryRepository.findAll().forEach(library -> {
            crawlerClient.updateTotalBooks(library);
        });
    }

}
