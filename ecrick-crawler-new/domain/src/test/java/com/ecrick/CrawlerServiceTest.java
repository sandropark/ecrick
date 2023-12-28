package com.ecrick;

import com.ecrick.service.CrawlerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CrawlerServiceTest {

    @Autowired
    CrawlerService crawlerService;

    @Test
    void test() throws Exception {
        crawlerService.crawl();
    }

}