package com.ecrick.log;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@SpringBootTest
class CrawlLogRepositoryTest {

    @Autowired
    CrawlLogRepository crawlLogRepository;

    @Transactional
    @Test
    void test() throws Exception {
        CrawlLog crawlLog = crawlLogRepository.findById(24576L).orElseThrow();
        System.out.println("crawlLog = " + crawlLog);
//        List<CrawlLog> logs = crawlLogRepository.findAllByTransactionId(UUID.fromString("79583da6-966b-444a-a533-b05b0ceeea4a"));
//        System.out.println("logs.size() = " + logs.size());
    }

}