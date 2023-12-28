package com.ecrick;

import com.ecrick.entity.Library;
import com.ecrick.entity.RowBook;
import com.ecrick.repository.LibraryRepository;
import com.ecrick.service.Crawler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class CrawlerTest {

    @Autowired
    LibraryRepository libraryRepository;
    @Autowired
    Crawler crawler;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    void crawl() throws Exception {
        Library library = libraryRepository.findById(5233L).orElseThrow();
        List<RowBook> rowBooks = crawler.crawl(library);

        System.out.println("rowBooks.size() = " + rowBooks.size());
    }

}